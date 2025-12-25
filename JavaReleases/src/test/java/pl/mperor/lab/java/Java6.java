package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.common.TestUtils;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.Files;

/**
 * Java 1.6 (December 2006) aka Mustang
 */
public class Java6 {

    @Test
    public void testScriptingLanguageSupport() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        // Execute a simple JavaScript expression
        Assertions.assertEquals(2, engine.eval("const a = 1, b = 1; a + b;"));

        // Call a JavaScript function from Java
        engine.eval("function add(a, b) { return a + b; }");
        Assertions.assertEquals(2, engine.eval("add(1,1)"));
    }

    @Test
    public void testJavaDynamicCompilation() throws IOException, ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        File sourceFile = new File("HelloWorld.java");
        String javaSourceCode = """
                public class HelloWorld {
                    public void sayHello() {
                        System.out.print("Hello World!");
                    }
                }
                """;
        Files.write(sourceFile.toPath(), javaSourceCode.getBytes());

        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        Assertions.assertNotNull(compiler, "JavaCompiler should not be null");
        int compilationResult = compiler.run(null, null, null, sourceFile.getAbsolutePath());
        Assertions.assertEquals(0, compilationResult, "Compilation should succeed with result 0!");
        assertHelloWorldClassAccessible();

        Assertions.assertTrue(sourceFile.delete(), "Source file should be deleted after compilation");
        Assertions.assertTrue(new File("HelloWorld.class").delete(), "Now compilation result can be deleted!");
    }

    private void assertHelloWorldClassAccessible() throws MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        URL[] urls = {new File("").toURI().toURL()};
        URLClassLoader loader = new URLClassLoader(urls);
        Class<?> clazz = loader.loadClass("HelloWorld");
        Object instance = clazz.getDeclaredConstructor().newInstance();
        var readableOut = TestUtils.setTempSystemOut();
        clazz.getDeclaredMethod("sayHello").invoke(instance);
        Assertions.assertEquals(readableOut.all(), "Hello World!");
        TestUtils.resetSystemOut();
    }

    @Test
    void testSystemTrayIcon() throws AWTException {
        if (SystemTray.isSupported()) {
            SystemTray tray = SystemTray.getSystemTray();
            Image trayIconImage = Toolkit.getDefaultToolkit().createImage("icon.png");
            TrayIcon trayIcon = new TrayIcon(trayIconImage, "Test Tray Icon");

            tray.add(trayIcon);
            Assertions.assertTrue(tray.getTrayIcons().length > 0, "Tray icon should be added");

            tray.remove(trayIcon);
            Assertions.assertEquals(0, tray.getTrayIcons().length, "Tray icon should be removed");
        }
    }

}
