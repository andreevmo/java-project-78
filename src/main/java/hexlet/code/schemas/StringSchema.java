package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema<String> {

    public StringSchema() {
        super(value -> value instanceof String && !value.toString().trim().equals(""));
    }

    public StringSchema required() {
        setIsCheckRequired(true);
        return this;
    }

    public StringSchema minLength(int minLength) {
        Predicate<String> minLengthPredicate = s -> s.length() >= minLength;
        addValidation(minLengthPredicate);
        return this;
    }

    public StringSchema contains(String subs) {
        Predicate<String> containsPredicate = s -> s.contains(subs);
        addValidation(containsPredicate);
        return this;
    }
}
