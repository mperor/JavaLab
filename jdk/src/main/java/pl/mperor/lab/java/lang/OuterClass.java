package pl.mperor.lab.java.lang;

public class OuterClass {

    private String secret = "outer secret";

    public class InnerClass {

        private String secret = "inner secret";

        public String getOuterClassSecret() {
            return OuterClass.this.secret;
        }

        public String getSecret() {
            // same as this.secret
            return secret;
        }
    }

    public String getSecret() {
        return secret;
    }

    public static class StaticNestedClass {
    }
}
