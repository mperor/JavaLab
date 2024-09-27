package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.Java1.OuterClass.InnerClass;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static pl.mperor.lab.java.Java1.OuterClass.StaticNestedClass;

/**
 * Java 1.1 (February 1997)
 */
public class Java1 {

    @Test
    public void testInnerAndNestedStaticClasses() {
        StaticNestedClass nested = new StaticNestedClass();
        Assertions.assertNotNull(nested);

        OuterClass outer = new OuterClass();
        InnerClass inner = outer.new InnerClass();

        Assertions.assertEquals("outer secret", outer.getSecret());
        Assertions.assertEquals(outer.getSecret(), inner.getOuterClassSecret());
        Assertions.assertEquals("inner secret", inner.getSecret());
    }

    class OuterClass {

        private String secret = "outer secret";

        class InnerClass {

            private String secret = "inner secret";

            public String getOuterClassSecret() {
                return OuterClass.this.secret;
            }

            String getSecret() {
                // same as this.secret
                return secret;
            }
        }

        String getSecret() {
            return secret;
        }

        public static class StaticNestedClass {
        }
    }

    @Test
    public void testJavaBean() throws IOException, ClassNotFoundException {
        var bean = new JavaBean();
        bean.setStringField("Hello");
        bean.setPrimitiveIntField(-1);

        Assertions.assertEquals("Hello", bean.getStringField());
        Assertions.assertEquals(-1, bean.getPrimitiveIntField());
        Assertions.assertInstanceOf(Serializable.class, bean);

        assertJavaBeanSerializationAndDeserialization(bean);
    }

    private void assertJavaBeanSerializationAndDeserialization(JavaBean bean) throws IOException, ClassNotFoundException {
        var file = new File("src/test/resources/bean");
        try (FileOutputStream fileOut = new FileOutputStream(file);
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(bean);
        }

        try (FileInputStream fileIn = new FileInputStream(file);
             ObjectInputStream in = new ObjectInputStream(fileIn)) {
            var beanFromFile = (JavaBean) in.readObject();
            Assertions.assertEquals(bean.getStringField(), beanFromFile.getStringField());
            Assertions.assertEquals(bean.getPrimitiveIntField(), beanFromFile.getPrimitiveIntField());
        }
    }

    @Test
    public void testReflectionAPI() throws NoSuchMethodException, InstantiationException, IllegalAccessException, InvocationTargetException {
        Class<JavaBean> clazz = JavaBean.class;
        Constructor<JavaBean> constructor = clazz.getDeclaredConstructor(String.class, int.class);
        JavaBean bean = constructor.newInstance("init by reflection", -1);

        Method getter = clazz.getDeclaredMethod("getStringField");
        Assertions.assertEquals("init by reflection", getter.invoke(bean));

        Method setter = clazz.getDeclaredMethod("setStringField", String.class);
        setter.invoke(bean, "set by reflection");

        Assertions.assertEquals("set by reflection", getter.invoke(bean));
    }

    @Test
    public void testRemoteMethodInvocationAkaRMI() throws RemoteException, NotBoundException, InterruptedException {
        CountDownLatch serverReadyLatch = new CountDownLatch(1);

        // Run the RMI server asynchronously
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                HelloService helloService = new HelloServiceImpl();
                Registry registry = LocateRegistry.createRegistry(1099);
                registry.rebind("HelloService", helloService);
                serverReadyLatch.countDown();
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        });

        // Give the server time to start
        serverReadyLatch.await();

        // Connect to the RMI registry and lookup the HelloService
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        HelloService stub = (HelloService) registry.lookup("HelloService");

        // Call the remote method and verify the result
        Assertions.assertEquals("Hello World!", stub.sayHello());

        executor.shutdown();
    }

    interface HelloService extends Remote {
        String sayHello() throws RemoteException;
    }

    static class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

        protected HelloServiceImpl() throws RemoteException {
        }

        @Override
        public String sayHello() throws RemoteException {
            return "Hello World!";
        }
    }


}