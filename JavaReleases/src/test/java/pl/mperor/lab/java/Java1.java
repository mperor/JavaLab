package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.lang.JavaBean;
import pl.mperor.lab.java.lang.OuterClass;
import pl.mperor.lab.java.lang.OuterClass.InnerClass;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
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
import java.sql.*;
import java.util.Arrays;
import java.util.List;

import static pl.mperor.lab.java.lang.OuterClass.StaticNestedClass;

/**
 * Java 1.1 (February 1997)
 *
 * - LANGUAGE FEATURES:
 *     - Inner classes (member, local & anonymous)
 *     - Instance initializer blocks
 *     - Class literals (`String.class`)
 *     - Blank finals
 *
 * - LIBRARIES & APIs:
 *     - JavaBeans
 *     - JDBC (Java Database Connectivity)
 *     - RMI (Remote Method Invocation)
 *     - Reflection API
 *     - Object Serialization
 *     - Character Streams (`Reader` / `Writer`) & Unicode 2.0
 *     - Internationalization (`java.text`, `Locale`, `ResourceBundle`)
 *     - Arbitrary-Precision Arithmetic (`java.math`)
 *     - AWT Event Delegation Model
 *     - JNI (Java Native Interface)
 *
 * - TOOLS:
 *     - JAR files (`jar`)
 *     - Signed JARs (`javakey`)
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

    @Test
    public void testJavaBean() throws IntrospectionException {
        var bean = new JavaBean(); // public no-arg constructor
        bean.setStringField("Hello");
        bean.setPrimitiveIntField(-1);

        Assertions.assertEquals("Hello", bean.getStringField());
        Assertions.assertEquals(-1, bean.getPrimitiveIntField());
        Assertions.assertInstanceOf(Serializable.class, bean);

        // getX/setX naming convention lets tools discover bean properties
        List<String> properties = Arrays.stream(Introspector.getBeanInfo(JavaBean.class, Object.class).getPropertyDescriptors())
                .map(PropertyDescriptor::getName)
                .toList();
        Assertions.assertEquals(List.of("primitiveIntField", "stringField"), properties);
    }

    @Test
    public void testObjectSerialization() throws IOException, ClassNotFoundException {
        var bean = new JavaBean("Hello", -1);

        var file = new File("src/test/resources/bean.bin");
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(bean);
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
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
    public void testRemoteMethodInvocationAkaRMI() throws RemoteException, NotBoundException {
        // Server side: start the registry, export the remote object and bind it under a name
        Registry serverRegistry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
        HelloService helloService = new HelloServiceImpl();
        try {
            serverRegistry.rebind("HelloService", helloService);

            // Client side: look up the stub in the registry and call the remote method
            Registry clientRegistry = LocateRegistry.getRegistry("localhost", Registry.REGISTRY_PORT);
            HelloService stub = (HelloService) clientRegistry.lookup("HelloService");

            Assertions.assertEquals("Hello World!", stub.getMessage());
        } finally {
            // Release the port and RMI threads, so the test can be run again in the same JVM
            UnicastRemoteObject.unexportObject(helloService, true);
            UnicastRemoteObject.unexportObject(serverRegistry, true);
        }
    }

    interface HelloService extends Remote {
        String getMessage() throws RemoteException;
    }

    static class HelloServiceImpl extends UnicastRemoteObject implements HelloService {

        protected HelloServiceImpl() throws RemoteException {
        }

        @Override
        public String getMessage() throws RemoteException {
            return "Hello World!";
        }
    }

    @Test
    public void testJavaDatabaseConnectivityAkaJDBC() throws SQLException, ClassNotFoundException {
        String createTableUsersSqlCommand = "CREATE TABLE IF NOT EXISTS users (id INT AUTO_INCREMENT PRIMARY KEY, name TEXT)";
        String insertUserSqlCommand = "INSERT INTO users (name) VALUES ('Mark Pi')";
        String queryAllUsersSqlCommand = "SELECT * FROM users";

        // Load the H2 JDBC driver: its static initializer registers it in DriverManager.
        // Required in Java 1.1; since JDBC 4.0 (Java 6) drivers are discovered automatically via ServiceLoader.
        Class.forName("org.h2.Driver");

        // Establish a connection to an in-memory H2 database & create a statement object for executing SQL queries
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:test", "sa", "");
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(createTableUsersSqlCommand);
            statement.executeUpdate(insertUserSqlCommand);
            try (ResultSet resultSet = statement.executeQuery(queryAllUsersSqlCommand)) {
                Assertions.assertTrue(resultSet.next());
                Assertions.assertEquals(1, resultSet.getInt("id"));
                Assertions.assertEquals("Mark Pi", resultSet.getString("name"));
            }
        }
    }

    @Test
    public void testCharacterStreams() throws IOException {
        // Java 1.1 added character streams (Reader/Writer) converting chars <-> bytes with a given encoding
        String text = "Zażółć gęślą jaźń"; // 17 chars, 9 of them Polish diacritics
        Assertions.assertEquals(26, text.getBytes("UTF-8").length);  // diacritics take 2 bytes each
        Assertions.assertEquals(17, text.getBytes("Cp1250").length); // single-byte Windows code page

        var bytes = new ByteArrayOutputStream();
        try (Writer writer = new OutputStreamWriter(bytes, "UTF-8")) {
            writer.write(text);
        }
        try (Reader reader = new InputStreamReader(new ByteArrayInputStream(bytes.toByteArray()), "UTF-8")) {
            var buffer = new char[text.length()];
            Assertions.assertEquals(text.length(), reader.read(buffer));
            Assertions.assertEquals(text, new String(buffer));
        }
    }

}