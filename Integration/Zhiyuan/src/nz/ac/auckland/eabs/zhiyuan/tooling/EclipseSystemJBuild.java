package nz.ac.auckland.eabs.zhiyuan.tooling;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import javax.tools.*;

/** Eclipse Java Application entry point. No PowerShell, shell scripts or PATH tools. */
public final class EclipseSystemJBuild {
    private static final String COMPILER = "nz.ac.auckland.eabs.eric.tooling.SystemJCompilerLauncher";
    private static final String TEST = "nz.ac.auckland.eabs.zhiyuan.coordinator.IntegratedCoordinatorTest";
    private static final String RUNNER = "com.systemj.SystemJRunner";
    private static final Pattern ERRORS = Pattern.compile(
        "error:|Internal errors were detected|OutOfMemoryError|Exception in thread", Pattern.CASE_INSENSITIVE);
    private static volatile Process activeChild;
    private final Path root, eric, run, classes, generated, java;
    private final String classpath;

    private EclipseSystemJBuild(Path project) throws IOException {
        root = project.toRealPath();
        eric = root.resolve("../Eric").normalize().toRealPath();
        if (!Files.isRegularFile(root.resolve("sysj/coordinator.sysj"))) {
            throw new IOException("Working directory must be Integration/Zhiyuan: " + root);
        }
        if (ToolProvider.getSystemJavaCompiler() == null) {
            throw new IllegalStateException("BuildAll needs a JDK, not a JRE. In Run Configurations > JRE, select a full JDK.");
        }
        Path javaHome = Paths.get(System.getProperty("java.home"));
        java = javaHome.resolve("bin").resolve(System.getProperty("os.name").startsWith("Windows") ? "java.exe" : "java");
        if (!Files.isRegularFile(java)) throw new IOException("Java executable missing: " + java);
        Path build = root.resolve("build");
        Files.createDirectories(build);
        if (!build.toRealPath().startsWith(root)) throw new IOException("Build directory escapes project: " + build);
        run = Files.createTempDirectory(build, "eclipse-build-");
        classes = Files.createDirectory(run.resolve("classes"));
        generated = Files.createDirectory(run.resolve("generated"));
        List<String> cp = new ArrayList<String>();
        cp.add(classes.toString());
        for (Path jar : files(root.resolve("lib"), ".jar", false)) {
            if (!jar.getFileName().toString().startsWith("kotlin-")) cp.add(jar.toString());
        }
        if (!Files.isRegularFile(root.resolve("lib/sjc-2.2-13-g8ab684c-SNAPSHOT.jar"))) {
            throw new IOException("Course SystemJ compiler jar missing in " + root.resolve("lib"));
        }
        classpath = String.join(File.pathSeparator, cp);
    }

    public static void main(String[] args) throws Exception {
        boolean tests = false;
        Path project = Paths.get(System.getProperty("user.dir"));
        for (int i=0; i<args.length; i++) {
            if (args[i].equals("--test")) tests = true;
            else if (args[i].equals("--project") && i+1 < args.length) project = Paths.get(args[++i]);
            else throw new IllegalArgumentException("Usage: EclipseSystemJBuild [--test] [--project directory]");
        }
        Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
            public void run() { Process child = activeChild; if (child != null && child.isAlive()) child.destroyForcibly(); }
        }, "systemj-build-child-cleanup"));
        EclipseSystemJBuild build = new EclipseSystemJBuild(project);
        build.compile();
        if (tests) build.test();
        build.publishSources();
        System.out.println(tests ? "ECLIPSE SYSTEMJ BUILD AND TEST PASSED" : "ECLIPSE SYSTEMJ BUILD PASSED");
        System.out.println("Evidence/output: " + build.run);
        System.out.println("Next: F5 on both projects; Project > Build Project (or Build Automatically); RunCoordinator.");
    }

    private void compile() throws Exception {
        // Compile source helpers independently of Eclipse's bytecode settings or stale bin/.
        List<Path> helpers = files(eric.resolve("src/main/java"), ".java", true);
        helpers.addAll(files(root.resolve("src"), ".java", true));
        helpers.addAll(files(root.resolve("tests"), ".java", true));
        System.out.println("[1/3] Compile Java helpers with Java 8 bytecode (" + helpers.size() + " files)");
        compileJava(helpers);
        List<Path> sources = new ArrayList<Path>();
        for (String name : new String[]{"coordinator.sysj", "coordinator_harness.sysj", "BottleLoaderController.sysj",
                "BottleLoaderPlant.sysj", "ConveyorController.sysj", "ConveyorPlant.sysj", "RoteryTableController.sysj",
                "RoteryTablePlant.sysj", "TwoLiquidFillerController.sysj", "TwoLiquidFillerPlant.sysj"}) {
            sources.add(root.resolve("sysj").resolve(name));
        }
        sources.add(eric.resolve("systemj/finishing_devices.sysj"));
        sources.add(eric.resolve("systemj/finishing_shims.sysj"));
        System.out.println("[2/3] Translate SystemJ to Java; the course compiler may take several minutes.");
        for (Path source : sources) {
            System.out.println("SystemJ: " + source.getFileName());
            child("compile-" + source.getFileName(), 90, "-Xmx384m", "-cp", classpath, COMPILER,
                "-d", generated.toString(), "--nojavac", "--silence", "--", source.toString());
        }
        List<Path> generatedSources = files(generated, ".java", false);
        if (generatedSources.size() != 23) throw new IOException("Expected 23 fresh CD sources, got " + generatedSources.size());
        System.out.println("[3/3] Compile and check all 23 generated CD classes");
        compileJava(generatedSources);
        for (Path source : generatedSources) {
            String name = source.getFileName().toString().replaceFirst("\\.java$", ".class");
            if (!Files.isRegularFile(classes.resolve(name))) throw new IOException("Missing fresh CD class " + name);
        }
    }

    private void compileJava(List<Path> sources) throws IOException {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        DiagnosticCollector<JavaFileObject> diagnostics = new DiagnosticCollector<JavaFileObject>();
        try (StandardJavaFileManager manager = compiler.getStandardFileManager(diagnostics, Locale.ROOT, StandardCharsets.UTF_8)) {
            List<File> inputs = new ArrayList<File>();
            for (Path p : sources) inputs.add(p.toFile());
            List<String> options = Arrays.asList("-source", "8", "-target", "8", "-Xlint:-options", "-encoding", "UTF-8",
                "-classpath", classpath, "-d", classes.toString());
            boolean ok = compiler.getTask(null, manager, diagnostics, options, null, manager.getJavaFileObjectsFromFiles(inputs)).call();
            for (Diagnostic<?> d : diagnostics.getDiagnostics()) System.out.println(d);
            if (!ok) throw new IOException("Java compilation failed; no generated sources published. Output: " + run);
        }
    }

    private String child(String label, int timeoutSeconds, String... args) throws Exception {
        List<String> command = new ArrayList<String>();
        command.add(java.toString()); Collections.addAll(command, args);
        Path log = run.resolve(label + ".log");
        Process child = new ProcessBuilder(command).directory(root.toFile()).redirectErrorStream(true).redirectOutput(log.toFile()).start();
        activeChild = child;
        try {
            if (!child.waitFor(timeoutSeconds, TimeUnit.SECONDS)) {
                child.destroyForcibly(); child.waitFor();
                throw new IOException(label + " timed out; see " + log);
            }
            String output = new String(Files.readAllBytes(log), StandardCharsets.UTF_8);
            if (!output.trim().isEmpty()) System.out.print(output);
            if (child.exitValue() != 0 || ERRORS.matcher(output).find()) {
                throw new IOException(label + " failed (exit " + child.exitValue() + "). See " + log);
            }
            return output;
        } finally {
            if (child.isAlive()) child.destroyForcibly();
            activeChild = null;
        }
    }

    private void test() throws Exception {
        System.out.println("[Tests] Model, real-device integration, archive and fault hold");
        require(child("model", 30, "-Djava.awt.headless=true", "-cp", classpath, TEST), "COORDINATOR MODEL TESTS PASSED");
        Path archive = run.resolve("workpieces.properties");
        require(child("normal", 120, "-Xmx256m", "-Djava.awt.headless=true", "-Deric.finishing.flatSimulation=true",
            "-Dcoordinator.archive=" + archive, "-cp", classpath, RUNNER, "sysj/coordinator.xml"), "COORDINATOR REAL DEVICE INTEGRATION PASSED");
        require(child("archive", 30, "-Djava.awt.headless=true", "-cp", classpath, TEST, archive.toString()), "COORDINATOR ARCHIVE CHECK PASSED");
        Path faultArchive = run.resolve("fault-workpieces.properties");
        require(child("fault", 45, "-Xmx256m", "-Djava.awt.headless=true", "-Deric.finishing.flatSimulation=true",
            "-Deric.finishing.testMode=true", "-Dcoordinator.testLidFault=true", "-Dcoordinator.archive=" + faultArchive,
            "-cp", classpath, RUNNER, "sysj/coordinator_fault.xml"), "COORDINATOR REAL DEVICE FAULT HOLD PASSED");
        if (Files.exists(faultArchive)) throw new IOException("Faulted bottle incorrectly archived as completed");
    }

    private void publishSources() throws IOException {
        Path target = root.resolve("generated-src");
        Files.createDirectories(target);
        if (!target.toRealPath().startsWith(root)) throw new IOException("Generated directory escapes project: " + target);
        List<Path> sources = files(generated, ".java", false);
        Set<String> names = new HashSet<String>();
        for (Path source : sources) names.add(source.getFileName().toString());
        for (Path old : files(target, ".java", false)) {
            if (!names.contains(old.getFileName().toString())) throw new IOException("Unexpected old generated source; inspect/remove it before rebuilding: " + old);
        }
        // Only known generated Java is replaced. Authored src/ and tests/ are never modified.
        for (Path source : sources) Files.copy(source, target.resolve(source.getFileName()), StandardCopyOption.REPLACE_EXISTING);
    }

    private static List<Path> files(Path dir, String suffix, boolean recursive) throws IOException {
        if (!Files.isDirectory(dir)) throw new IOException("Required source/library directory missing: " + dir);
        List<Path> result = new ArrayList<Path>();
        try (Stream<Path> stream = Files.walk(dir, recursive ? Integer.MAX_VALUE : 1)) {
            Iterator<Path> it = stream.iterator();
            while (it.hasNext()) { Path p = it.next(); if (Files.isRegularFile(p) && p.getFileName().toString().endsWith(suffix)) result.add(p); }
        }
        Collections.sort(result); return result;
    }

    private static void require(String output, String marker) throws IOException {
        if (!output.contains(marker)) throw new IOException("Missing test completion marker: " + marker);
    }
}
