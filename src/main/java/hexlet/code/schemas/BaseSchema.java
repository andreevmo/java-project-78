package hexlet.code.schemas;

import java.util.function.Predicate;

public class BaseSchema {

    private Predicate<Object> valid = s -> true;

    public final Predicate<Object> getValid() {
        return valid;
    }

    public final void setValid(Predicate<Object> newValid) {
        this.valid = newValid;
    }

    public final boolean isValid(Object value) {
        return valid.test(value);
    }
}
