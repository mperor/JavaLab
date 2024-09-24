package pl.mperor.lab.java;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.mperor.lab.java.Java1.OuterClass.InnerClass;

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

}