package nz.ac.auckland.eabs.eric.tooling;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Compatibility launcher for the course SystemJ compiler on modern JDKs.
 *
 * The 2023 compiler reads legacy properties removed after Java 8. Supplying
 * empty search paths preserves its intended classpath fallback behaviour.
 */
public final class SystemJCompilerLauncher {
    private SystemJCompilerLauncher() { }

    public static void main(String[] args) throws Exception {
        setIfMissing("sun.boot.class.path", "");
        setIfMissing("java.ext.dirs", "");

        Class<?> compiler =
                Class.forName("com.systemj.compiler.JavaPrettyPrinter");
        Method main = compiler.getMethod("main", String[].class);
        try {
            main.invoke(null, new Object[] {args});
        } catch (InvocationTargetException failure) {
            Throwable cause = failure.getCause();
            if (cause instanceof Exception) {
                throw (Exception) cause;
            }
            if (cause instanceof Error) {
                throw (Error) cause;
            }
            throw failure;
        }
    }

    private static void setIfMissing(String name, String value) {
        if (System.getProperty(name) == null) {
            System.setProperty(name, value);
        }
    }
}
