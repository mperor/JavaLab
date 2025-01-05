package pl.mperor.lab.java.jndi;

import javax.naming.Context;
import javax.naming.spi.InitialContextFactory;
import java.util.Hashtable;

public class CustomInitialContextFactory implements InitialContextFactory {
    @Override
    public Context getInitialContext(Hashtable<?, ?> environment) {
        return new CustomContext();
    }
}
