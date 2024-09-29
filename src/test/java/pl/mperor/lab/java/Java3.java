package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.jndi.CustomInitialContextFactory;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * Java 1.3 (May 2000)
 */
public class Java3 {

    @Test
    public void testJavaNamingAndDirectoryInterfaceAkaJNDILookup() throws NamingException {
        System.setProperty(InitialContext.INITIAL_CONTEXT_FACTORY, CustomInitialContextFactory.class.getName());
        Context context = new InitialContext();
        String bindingName = "hello";
        String bindingResult = "Hello World!";
        context.bind(bindingName, bindingResult);
        String lookupResult = (String) context.lookup(bindingName);
        Assertions.assertEquals(bindingResult, lookupResult);

        context.unbind(bindingName);
        Assertions.assertThrows(NamingException.class, () -> {
            context.lookup(bindingName);
        });
    }

}