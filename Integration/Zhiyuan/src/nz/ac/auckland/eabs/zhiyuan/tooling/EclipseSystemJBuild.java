package nz.ac.auckland.eabs.zhiyuan.tooling;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.tools.*;

public final class EclipseSystemJBuild {

    private static final String COMPILER = "nz.ac.auckland.eabs.eric.tooling.SystemJCompilerLauncher";
    private static final String RUNNER   = "com.systemj.SystemJRunner";
    private static final String MODEL    = "nz.ac.auckland.eabs.zhiyuan.coordinator.IntegratedCoordinatorTest";
    private static final Pattern ERR = Pattern.compile("error:|Exception in thread|OutOfMemoryError|Internal errors were detected", Pattern.CASE_INSENSITIVE);
    private static final Pattern PORT = Pattern.compile("\\bPort\\s*=\\s*\"([0-9]+)\"");
    private static volatile Process activeChild;

    private static final class Variant {
        final String name, folder, xml;
        final String[] sysj;
        Variant(String name, String folder, String xml, String... sysj) {
            this.name = name; this.folder = folder; this.xml = xml; this.sysj = sysj;
        }
    }

    private static final String[] SHARED = {
        "coordinator_harness.sysj", "batch_manager.sysj", "safety_monitor.sysj",
        "BottleLoaderController.sysj", "BottleLoaderPlant.sysj",
        "ConveyorController.sysj", "ConveyorPlant.sysj",
        "RoteryTablePlant.sysj", "TwoLiquidFillerController.sysj", "TwoLiquidFillerPlant.sysj"
    };

    private static final Variant[] VARIANTS = {
        new Variant("BASELINE", "01_Baseline_1CD", "coordinator.xml",
            "BaselineCoordinatorCD.sysj", "BaselineRotaryTableController.sysj"),
        new Variant("IMPROVED", "02_Improved_3CD", "IntegratedCoordinatorAcceptance_3CD.xml",
            "BatchCoordinatorCD.sysj", "ProductionCoordinatorCD.sysj", "SafetyCoordinatorCD.sysj", "DecomposedRotaryTableController.sysj"),
        new Variant("HARDENED", "03_Hardened_1CD", "HardenedCoordinatorCD.xml",
            "coordinator.sysj", "HardenedRotaryTableController.sysj")
    };

    private static final String[] REQUIRED = {
        "BaselineCoordinatorCD.java", "BaselineRotaryTableController.java",
        "BatchCoordinatorCD.java", "ProductionCoordinatorCD.java", "SafetyCoordinatorCD.java", "DecomposedRotaryTableController.java",
        "HardenedCoordinatorCD.java", "HardenedRotaryTableController.java"
    };

    private final Path root, eric, run, classes, generated, java;
    private final String cp;

    public static void main(String[] args) throws Exception {
        boolean test = false;
        Path project = Paths.get(System.getProperty("user.dir"));
        for (int i = 0; i < args.length; i++) {
            if ("--test".equals(args[i])) test = true;
            else if ("--project".equals(args[i]) && i + 1 < args.length) project = Paths.get(args[++i]);
            else throw new IllegalArgumentException("Usage: EclipseSystemJBuild [--test] [--project DIR]");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            Process p = activeChild;
            if (p != null && p.isAlive()) p.destroyForcibly();
        }));
        EclipseSystemJBuild b = new EclipseSystemJBuild(project);
        if (test) b.checkPorts();
        b.compile();
        if (test) b.smokeAll();
        b.publishGenerated();
        b.publishManifest();
        System.out.println();
        System.out.println(test ? "ECLIPSE SYSTEMJ BUILD AND TEST PASSED" : "ECLIPSE SYSTEMJ BUILD PASSED");
        System.out.println("Classes:  " + b.classes);
        System.out.println("Build dir: " + b.run);
        System.out.println("XML choices:");
        for (Variant v : VARIANTS) System.out.println("  " + v.name + " -> " + b.root.resolve("sysj").resolve(v.folder).resolve(v.xml));
        System.out.println("Switch architecture by selecting a different XML; rebuild is not needed.");
    }

    private EclipseSystemJBuild(Path project) throws Exception {
        root = project.toRealPath();
        eric = root.resolve("../Eric").normalize().toRealPath();
        Path sysj = root.resolve("sysj");
        needDir(sysj); needDir(root.resolve("src")); needDir(root.resolve("tests")); needDir(eric.resolve("src/main/java"));
        for (String s : SHARED) needFile(sysj.resolve(s));
        for (Variant v : VARIANTS) {
            Path d = sysj.resolve(v.folder); needDir(d); needFile(d.resolve(v.xml));
            for (String s : v.sysj) needFile(d.resolve(s));
        }
        needFile(eric.resolve("systemj/finishing_devices.sysj"));
        needFile(eric.resolve("systemj/finishing_shims.sysj"));
        if (ToolProvider.getSystemJavaCompiler() == null) throw new IllegalStateException("Run with a JDK, not a JRE.");
        java = Paths.get(System.getProperty("java.home"), "bin", osExe("java"));
        needFile(java);
        Path build = root.resolve("build"); Files.createDirectories(build);
        run = Files.createTempDirectory(build, "eclipse-build-all-");
        classes = Files.createDirectory(run.resolve("classes"));
        generated = Files.createDirectory(run.resolve("generated"));
        List<String> c = new ArrayList<>();
        c.add(classes.toString());
        for (Path j : listFiles(root.resolve("lib"), ".jar", false)) if (!j.getFileName().toString().startsWith("kotlin-")) c.add(j.toString());
        needFile(root.resolve("lib/sjc-2.2-13-g8ab684c-SNAPSHOT.jar"));
        cp = String.join(File.pathSeparator, c);
    }

    private void compile() throws Exception {
        validateSourceLayout(eric.resolve("src/main/java"));
        validateSourceLayout(root.resolve("src"));
        validateSourceLayout(root.resolve("tests"));

        List<Path> helpers = new ArrayList<>();
        helpers.addAll(listFiles(eric.resolve("src/main/java"), ".java", true));
        helpers.addAll(listFiles(root.resolve("src"), ".java", true));
        helpers.addAll(listFiles(root.resolve("tests"), ".java", true));
        System.out.println("[1/3] Compile Java helpers with Java 8 bytecode (" + helpers.size() + " files)");
        compileJava(helpers);

        List<Path> sysj = new ArrayList<>();
        Path rootSysj = root.resolve("sysj");
        for (String s : SHARED) sysj.add(rootSysj.resolve(s));
        for (Variant v : VARIANTS) for (String s : v.sysj) sysj.add(rootSysj.resolve(v.folder).resolve(s));
        sysj.add(eric.resolve("systemj/finishing_devices.sysj"));
        sysj.add(eric.resolve("systemj/finishing_shims.sysj"));

        System.out.println("[2/3] Translate all SystemJ variants to Java");
        for (Path s : sysj) {
            System.out.println("SystemJ: " + root.relativize(s.toAbsolutePath().normalize()));
            child("compile-" + safe(s.getFileName().toString()), 90,
                "-Xmx384m", "-cp", cp, COMPILER,
                "-d", generated.toString(), "--nojavac", "--silence", "--", s.toString());
        }

        for (String name : REQUIRED) needFile(generated.resolve(name));
        List<Path> out = listFiles(generated, ".java", false);
        System.out.println("[3/3] Compile generated clock-domain classes (" + out.size() + " files)");
        compileJava(out);
        for (Path s : out) needFile(classes.resolve(s.getFileName().toString().replaceFirst("\\.java$", ".class")));
    }

    private void smokeAll() throws Exception {
        System.out.println("[Tests] Model test");
        mustContain(child("model", 30, "-Djava.awt.headless=true", "-cp", cp, MODEL), "COORDINATOR MODEL TESTS PASSED");
        for (Variant v : VARIANTS) {
            Path xml = root.resolve("sysj").resolve(v.folder).resolve(v.xml);
            System.out.println("[Smoke] " + v.name + " -> " + xml);
            String out = child("smoke-" + v.name.toLowerCase(Locale.ROOT), 120,
                "-Xmx256m", "-Djava.awt.headless=true", "-Davailability.fastHarness=true", "-Deric.finishing.flatSimulation=true",
                "-cp", cp, RUNNER, xml.toString());
            if (!(out.contains("DRAINED") || out.contains("COORDINATOR REAL DEVICE INTEGRATION PASSED")))
                throw new IOException("Smoke test did not finish cleanly for " + v.name);
        }
    }

    private void compileJava(List<Path> src) throws IOException {
        JavaCompiler jc = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diags = new DiagnosticCollector<>();
        try (StandardJavaFileManager fm = jc.getStandardFileManager(diags, Locale.ROOT, StandardCharsets.UTF_8)) {
            List<File> in = new ArrayList<>(); for (Path p : src) in.add(p.toFile());
            List<String> opt = Arrays.asList("-source", "8", "-target", "8", "-Xlint:-options", "-encoding", "UTF-8", "-classpath", cp, "-d", classes.toString());
            boolean ok = jc.getTask(null, fm, diags, opt, null, fm.getJavaFileObjectsFromFiles(in)).call();
            for (Diagnostic<?> d : diags.getDiagnostics()) System.out.println(d);
            if (!ok) throw new IOException("Java compilation failed. Output: " + run);
        }
    }

    private String child(String label, int timeout, String... args) throws Exception {
        List<String> cmd = new ArrayList<>(); cmd.add(java.toString()); Collections.addAll(cmd, args);
        Path log = run.resolve(label + ".log");
        Process p = new ProcessBuilder(cmd).directory(root.toFile()).redirectErrorStream(true).redirectOutput(log.toFile()).start();
        activeChild = p;
        try {
            if (!p.waitFor(timeout, TimeUnit.SECONDS)) {
                p.destroyForcibly(); p.waitFor(); throw new IOException(label + " timed out; see " + log);
            }
            String out = new String(Files.readAllBytes(log), StandardCharsets.UTF_8);
            if (!out.trim().isEmpty()) System.out.print(out);
            if (p.exitValue() != 0 || ERR.matcher(out).find()) throw new IOException(label + " failed; see " + log);
            return out;
        } finally {
            if (p.isAlive()) p.destroyForcibly();
            activeChild = null;
        }
    }

    private void publishGenerated() throws IOException {
        Path dst = root.resolve("generated-src"); Files.createDirectories(dst);
        Set<String> fresh = new HashSet<>();
        for (Path p : listFiles(generated, ".java", false)) {
            fresh.add(p.getFileName().toString());
            Files.copy(p, dst.resolve(p.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        }
        for (Path p : listFiles(dst, ".java", false)) if (!fresh.contains(p.getFileName().toString())) Files.deleteIfExists(p);
    }

    private void publishManifest() throws IOException {
        List<String> m = new ArrayList<>();
        m.add("mode=ALL_CONFIGURATIONS");
        m.add("classes=" + classes);
        m.add("build=" + run);
        for (Variant v : VARIANTS) m.add(v.name.toLowerCase(Locale.ROOT) + "Xml=" + root.resolve("sysj").resolve(v.folder).resolve(v.xml));
        Files.write(run.resolve("BUILD_MANIFEST.txt"), m, StandardCharsets.UTF_8);
        Files.write(root.resolve("build/ACTIVE_BUILD.txt"), m, StandardCharsets.UTF_8);
    }

    private void checkPorts() throws IOException {
        Set<Integer> ports = new TreeSet<>();
        for (Variant v : VARIANTS) {
            String xml = new String(Files.readAllBytes(root.resolve("sysj").resolve(v.folder).resolve(v.xml)), StandardCharsets.UTF_8);
            Matcher m = PORT.matcher(xml); while (m.find()) ports.add(Integer.parseInt(m.group(1)));
        }
        List<ServerSocket> held = new ArrayList<>();
        try {
            for (int port : ports) {
                ServerSocket s = new ServerSocket(); held.add(s); s.setReuseAddress(false);
                s.bind(new InetSocketAddress("127.0.0.1", port));
            }
            System.out.println("[Ports] Available: " + ports);
        } finally { for (ServerSocket s : held) s.close(); }
    }

    public static void validateSourceLayout(Path root) throws IOException {
        Pattern pkg = Pattern.compile("(?m)^\\s*package\\s+([A-Za-z_$][\\w.$]*)\\s*;");
        for (Path f : listFiles(root, ".java", true)) {
            String expected = root.relativize(f.getParent()).toString().replace(File.separatorChar, '.');
            Matcher m = pkg.matcher(new String(Files.readAllBytes(f), StandardCharsets.UTF_8));
            String actual = m.find() ? m.group(1) : "";
            if (!expected.equals(actual)) throw new IOException("Package/source-folder mismatch: " + f + " declares '" + actual + "' but should be '" + expected + "'.");
        }
    }

    private static List<Path> listFiles(Path dir, String suffix, boolean recursive) throws IOException {
        needDir(dir);
        List<Path> out = new ArrayList<>();
        try (Stream<Path> st = Files.walk(dir, recursive ? Integer.MAX_VALUE : 1)) {
            for (Iterator<Path> it = st.iterator(); it.hasNext();) {
                Path p = it.next();
                if (Files.isRegularFile(p) && p.getFileName().toString().endsWith(suffix)) out.add(p);
            }
        }
        Collections.sort(out); return out;
    }

    private static void needFile(Path p) throws IOException { if (!Files.isRegularFile(p)) throw new IOException("Missing file: " + p); }
    private static void needDir(Path p) throws IOException { if (!Files.isDirectory(p)) throw new IOException("Missing directory: " + p); }
    private static void mustContain(String s, String marker) throws IOException { if (!s.contains(marker)) throw new IOException("Missing marker: " + marker); }
    private static String osExe(String base) { return System.getProperty("os.name").startsWith("Windows") ? base + ".exe" : base; }
    private static String safe(String s) { return s.replaceAll("[^A-Za-z0-9._-]", "_"); }
}

