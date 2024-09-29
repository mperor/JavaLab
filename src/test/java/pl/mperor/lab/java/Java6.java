package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

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

}
