package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.schemas.StringSchema;
import  org.junit.jupiter.api.Test;


public class TestValidator {

    @Test
    void testStringSchema() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        schema.required();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();
        assertThat(schema.isValid("Hi")).isTrue();

        schema.minLength(3);
        assertThat(schema.isValid("Hi")).isFalse();
        assertThat(schema.isValid("Hi, Max")).isTrue();
        assertThat(schema.isValid("Max")).isTrue();

        assertThat(schema.contains("Max").isValid("Hi, Max")).isTrue();
        assertThat(schema.contains("Hi").isValid("Hi, Max")).isTrue();
        assertThat(schema.contains("Some").isValid("Hi, Max")).isFalse();
        assertThat(schema.contains("Max").isValid("Hi, Max")).isFalse();

        schema.required();
        assertThat(schema.contains("Max").isValid("Hi, Max")).isTrue();

        schema.required().minLength(8);
        assertThat(schema.contains("Max").isValid("Hi, Max")).isFalse();

        schema.required().minLength(4).contains("Some");
        assertThat(schema.contains("Max").isValid("Hi, Max")).isFalse();

        schema.required().minLength(4);
        assertThat(schema.contains("Max").isValid("Hi, Max")).isTrue();

        schema.required().minLength(4).contains("Some");
        assertThat(schema.isValid("Hi, Max")).isFalse();
    }
}
