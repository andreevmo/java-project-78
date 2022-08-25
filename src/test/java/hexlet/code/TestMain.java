package hexlet.code;

import static org.assertj.core.api.Assertions.assertThat;
import  org.junit.jupiter.api.Test;
import static hexlet.code.Main.add;

public class TestMain {

    @Test
    void testAdd() {
        assertThat(add(2, 5)).isEqualTo(7);
    }
}
