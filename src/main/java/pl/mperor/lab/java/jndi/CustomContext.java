package pl.mperor.lab.java.jndi;

import javax.naming.*;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class CustomContext implements Context {
    private Map<String, Object> resources;

    public CustomContext() {
        resources = new HashMap<>();
    }

    @Override
    public Object lookup(String name) throws NamingException {
        Object resource = resources.get(name);
        if (resource == null) {
            throw new NamingException("Resource not found: %s".formatted(name));
        }
        return resource;
    }

    @Override
    public void bind(String name, Object obj) throws NamingException {
        resources.put(name, obj);
    }

    @Override
    public void unbind(String name) throws NamingException {
        resources.remove(name);
    }

    @Override
    public void close() throws NamingException {
        // Clean up resources if necessary
    }

    // For simplicity, not implementing the other required methods here.
    @Override
    public NamingEnumeration<NameClassPair> list(String name) throws NamingException {
        return null;
    }

    @Override
    public void rebind(String name, Object obj) throws NamingException {
    }

    @Override
    public NameParser getNameParser(String name) throws NamingException {
        return null;
    }

    @Override
    public String composeName(String name, String prefix) throws NamingException {
        return null;
    }

    @Override
    public Context createSubcontext(String name) throws NamingException {
        return null;
    }

    @Override
    public Hashtable<?, ?> getEnvironment() throws NamingException {
        return null;
    }

    @Override
    public Object lookup(Name name) throws NamingException {
        return null;
    }

    @Override
    public void bind(Name name, Object obj) throws NamingException {

    }

    @Override
    public void rebind(Name name, Object obj) throws NamingException {

    }

    @Override
    public void unbind(Name name) throws NamingException {

    }

    @Override
    public void rename(Name oldName, Name newName) throws NamingException {

    }

    @Override
    public void rename(String oldName, String newName) throws NamingException {

    }

    @Override
    public NamingEnumeration<NameClassPair> list(Name name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<Binding> listBindings(Name name) throws NamingException {
        return null;
    }

    @Override
    public NamingEnumeration<Binding> listBindings(String name) throws NamingException {
        return null;
    }

    @Override
    public void destroySubcontext(Name name) throws NamingException {

    }

    @Override
    public void destroySubcontext(String name) throws NamingException {

    }

    @Override
    public Context createSubcontext(Name name) throws NamingException {
        return null;
    }

    @Override
    public Object lookupLink(Name name) throws NamingException {
        return null;
    }

    @Override
    public Object lookupLink(String name) throws NamingException {
        return null;
    }

    @Override
    public NameParser getNameParser(Name name) throws NamingException {
        return null;
    }

    @Override
    public Name composeName(Name name, Name prefix) throws NamingException {
        return null;
    }

    @Override
    public Object addToEnvironment(String propName, Object propVal) throws NamingException {
        return null;
    }

    @Override
    public Object removeFromEnvironment(String propName) throws NamingException {
        return null;
    }

    @Override
    public String getNameInNamespace() throws NamingException {
        return "";
    }
}
