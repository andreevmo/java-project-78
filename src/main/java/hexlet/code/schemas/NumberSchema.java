package hexlet.code.schemas;

import java.util.function.Predicate;

public final class NumberSchema extends BaseSchema<Integer> {

    public NumberSchema() {
        super(value -> value instanceof Integer);
    }

    public NumberSchema required() {
        setIsCheckRequired(true);
        return this;
    }

    public NumberSchema positive() {
        Predicate<Integer> positivePredicate = num -> num > 0;
        addValidation(positivePredicate);
        return this;
    }

    public NumberSchema range(int begin, int end) {
        Predicate<Integer> rangePredicate = num -> num >= begin && num <= end;
        addValidation(rangePredicate);
        return this;
    }
}
