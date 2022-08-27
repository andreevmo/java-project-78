package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;

import hexlet.code.schemas.BaseSchema;
import hexlet.code.schemas.MapSchema;
import hexlet.code.schemas.NumberSchema;
import hexlet.code.schemas.StringSchema;
import  org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;


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

        Validator v1 = new Validator();
        StringSchema schema1 = v1.string();
        schema1.required();
        assertThat(schema1.contains("Max").isValid("Hi, Max")).isTrue();

        Validator v2 = new Validator();
        StringSchema schema2 = v2.string();
        schema2.required().minLength(8);
        assertThat(schema2.contains("Max").isValid("Hi, Max")).isFalse();

        Validator v3 = new Validator();
        StringSchema schema3 = v3.string();
        schema.minLength(3);
        assertThat(schema.isValid(123)).isFalse();
    }

    @Test
    void testNumberSchema() {
        Validator v = new Validator();
        NumberSchema schema = v.number();

        assertThat(schema.isValid(null)).isTrue();
        assertThat(schema.positive().isValid(null)).isTrue();
        assertThat(schema.isValid("5")).isFalse();

        Validator v1 = new Validator();
        NumberSchema schema1 = v1.number();

        schema1.required();
        assertThat(schema1.isValid(null)).isFalse();
        assertThat(schema1.positive().isValid(null)).isFalse();

        assertThat(schema1.isValid(null)).isFalse();
        assertThat(schema1.isValid(10)).isTrue();
        assertThat(schema1.isValid("5")).isFalse();

        assertThat(schema1.positive().isValid(10)).isTrue();
        assertThat(schema1.isValid(-10)).isFalse();

        schema1.range(5, 10);

        assertThat(schema1.isValid(5)).isTrue();
        assertThat(schema1.isValid(10)).isTrue();
        assertThat(schema1.isValid(4)).isFalse();
        assertThat(schema1.isValid(11)).isFalse();
    }

    @Test
    void testMapSchema() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        assertThat(schema.isValid(null)).isTrue();

        schema.required();

        assertThat(schema.isValid(null)).isFalse();
        assertThat(schema.isValid(new HashMap())).isTrue();
        Map<String, String> data = new HashMap<>();
        data.put("key1", "value1");
        assertThat(schema.isValid(data)).isTrue();

        schema.sizeof(2);

        assertThat(schema.isValid(data)).isFalse();
        data.put("key2", "value2");
        assertThat(schema.isValid(data)).isTrue();
        data.put("key3", "value3");
        assertThat(schema.isValid(data)).isFalse();

        Map<String, String> data1 = new LinkedHashMap<>();
        data1.put("key1", "value1");
        assertThat(schema.isValid(data1)).isFalse();
        data1.put("key2", "value2");
        assertThat(schema.isValid(data1)).isTrue();
        data1.put("key3", "value3");
        assertThat(schema.isValid(data1)).isFalse();

        Validator v1 = new Validator();
        MapSchema schema1 = v1.map();

        schema1.sizeof(2);

        boolean res1 = schema1.isValid("1");
    }

    @Test
    void testShape() {
        Validator v = new Validator();
        MapSchema schema = v.map();

        Map<String, BaseSchema> schemas = new HashMap<>();
        schemas.put("name", v.string().required());
        schemas.put("age", v.number().positive());
        schema.shape(schemas);

        Map<String, Object> human = new HashMap<>();
        human.put("name", "Kolya");
        human.put("age", 100);
        assertThat(schema.isValid(human)).isTrue();

        human.put("name", "Maya");
        human.put("age", null);
        assertThat(schema.isValid(human)).isTrue();

        human.put("name", "");
        human.put("age", null);
        assertThat(schema.isValid(human)).isFalse();

        human.put("name", "Valya");
        human.put("age", -5);
        assertThat(schema.isValid(human)).isFalse();

        Validator v1 = new Validator();
        MapSchema schema1 = v1.map();

        Map<String, BaseSchema> schemas1 = new HashMap<>();
        schemas1.put("name", v.string().required().minLength(4));
        schemas1.put("age", v.number().positive().range(1, 5));
        schema1.shape(schemas1);

        human.put("name", "Max");
        human.put("age", 5);
        assertThat(schema1.isValid(human)).isFalse();

        human.put("name", "Maxim");
        human.put("age", 5);
        assertThat(schema1.isValid(human)).isTrue();

        human.put("name", "Maxim");
        human.put("age", 7);
        assertThat(schema1.isValid(human)).isFalse();

        human.put("name", null);
        human.put("age", 4);
        assertThat(schema1.isValid(human)).isFalse();

        Validator v2 = new Validator();
        MapSchema schema2 = v2.map();

        Map<String, BaseSchema> schemas2 = new HashMap<>();
        schemas2.put("name", v.string().minLength(4));
        schemas2.put("age", v.number().range(-10, 5));
        schema2.shape(schemas2);

        human.put("name", null);
        human.put("age", 4);
        assertThat(schema2.isValid(human)).isTrue();

        human.put("name", "Maxim");
        human.put("age", -3);
        assertThat(schema2.isValid(human)).isTrue();
    }
}
