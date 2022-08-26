package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import  org.junit.jupiter.api.Test;


public class TestValidator {

    @Test
    void testStringSchema() {
        Validator v = new Validator();
        StringSchema schema = v.string();

        assertThat(schema.isValid(null)).isTrue();

        schema.required();
        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid("")).isFalse();
        boolean res = schema.isValid("Hi");
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

    @Test
    void testNumberSchema() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertThat(schema.isValid(null)).isTrue();

        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid("5")).isFalse();

        assertThat(schema.positive().isValid(10)).isTrue();
        assertThat(schema.isValid(-10)).isFalse();

        schema.range(5, 10);

        assertThat(schema.isValid(5)).isTrue();
        assertThat(schema.isValid(10)).isTrue();
        assertThat(schema.isValid(4)).isFalse();
        assertThat(schema.isValid(11)).isFalse();

        schema.required();
        assertThat(schema.range(5, 10).isValid(7)).isTrue();

        schema.required().range(-1, 10);
        assertThat(schema.isValid(-1)).isTrue();

        assertThat(schema.required().positive().isValid(-1)).isFalse();
    }
}
