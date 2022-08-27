package hexlet.code.schemas;

import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema {

    private final Predicate<Object> schemaPredicate = value -> value instanceof Integer;

    public NumberSchema required() {
        setValid(getValid().and(schemaPredicate));
        return this;
    }

    public NumberSchema positive() {
        Predicate<Object> positivePredicate = num -> (Integer) num > 0;
        setValid(getValid().and(num -> check(num, positivePredicate, schemaPredicate)));
        return this;
    }

    public NumberSchema range(int begin, int end) {
        Predicate<Object> rangePredicate = num -> (Integer) num >= begin && (Integer) num <= end;
        setValid(getValid().and(num -> check(num, rangePredicate, schemaPredicate)));
        return this;
    }
}
