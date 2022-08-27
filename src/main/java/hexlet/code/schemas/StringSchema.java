package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema {

    private final Predicate<Object> schemaPredicate = value -> value instanceof String;

    public StringSchema required() {
        Predicate<Object> requiredPredicate = s -> !s.toString().trim().equals("");
        setValid(getValid().and(value -> schemaPredicate.test(value) && requiredPredicate.test(value)));
        return this;
    }

    public StringSchema minLength(int minLength) {
        Predicate<Object> minLengthPredicate = s -> s.toString().length() >= minLength;
        setValid(getValid().and(s -> check(s, minLengthPredicate, schemaPredicate)));
        return this;
    }

    public StringSchema contains(String subs) {
        Predicate<Object> containsPredicate = s -> s.toString().contains(subs);
        setValid(getValid().and(s -> check(s, containsPredicate, schemaPredicate)));
        return this;
    }
}
