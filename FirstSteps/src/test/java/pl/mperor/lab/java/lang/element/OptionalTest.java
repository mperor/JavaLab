package pl.mperor.lab.java.lang.element;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

public class OptionalTest {

    @Test
    public void testOptionalImperatively() {
        Assertions.assertFalse(SomeAPI.getNameById(0).isPresent());
        // Please for(get), do not use get, instead use orElseThrow() / orElse("default")
        Assertions.assertEquals("default", SomeAPI.getNameById(0).orElse("default"));
        Assertions.assertEquals("some name", SomeAPI.getNameById(1).orElseThrow());
    }

    @Test
    public void testOptionalFunctionally() {
        SomeAPI.getNameById(1).ifPresentOrElse(
                name -> Assertions.assertEquals("some name", name),
                () -> Assertions.fail()
        );
    }

    static class SomeAPI {

        // 1. Return a reference if the value will always exist
        // 2. Return an Optional<T> if the value may or may not exist
        public static Optional<String> getNameById(long id) {
            if (id == 0) {
                return Optional.empty();
            }

            return Optional.of("some name");
        }

        // 3. Do not use Optional<T> for fields - overhead with little benefit
        private String config;

        // 4. Do not user Optional<T> for method parameters - user overloading instead
        public void setConfig(String config) {
            this.config = config;
        }

        public void setConfig() {
            setConfig("default");
        }
    }

}
