package hexlet.code.schemas;

import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema {

    public NumberSchema required() {
        setValid(getValid().and(value -> value instanceof Integer));
        return this;
    }

    public NumberSchema positive() {
        setValid(getValid().and(num -> check(num, num1 -> (Integer) num1 > 0)));
        return this;
    }

    public NumberSchema range(int begin, int end) {
        setValid(getValid().and(num -> check(num, num1 -> (Integer) num1 >= begin && (Integer) num1 <= end)));
        return this;
    }

    public boolean check(Object o, Predicate<Object> predicate) {
        if (o == null) {
            return true;
        } else if (o instanceof Integer) {
            return predicate.test(o);
        }
        return false;
    }
}
