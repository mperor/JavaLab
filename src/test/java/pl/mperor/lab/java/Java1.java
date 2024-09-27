package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.Java1.OuterClass.InnerClass;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

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

        assertReadWriteJavaBean(bean);
    }

    private void assertReadWriteJavaBean(JavaBean bean) throws IOException, ClassNotFoundException {
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

}