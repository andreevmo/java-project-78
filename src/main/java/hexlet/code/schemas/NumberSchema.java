package hexlet.code.schemas;

public final class NumberSchema extends BaseSchema {

    public NumberSchema required() {
        setValid(value -> value instanceof Integer);
        return this;
    }

    public NumberSchema positive() {
        setValid(getValid().and(num -> (Integer) num > 0));
        return this;
    }

    public NumberSchema range(int begin, int end) {
        setValid(getValid().and(num -> (Integer) num >= begin && (Integer) num <= end));
        return this;
    }
}
