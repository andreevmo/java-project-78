package hexlet.code.schemas;

public final class StringSchema extends BaseSchema {

    public StringSchema required() {
        setValid(s -> s instanceof String && !s.toString().trim().equals(""));
        return this;
    }

    public StringSchema minLength(int minLength) {
        setValid(getValid().and(s -> s.toString().length() >= minLength));
        return this;
    }

    public StringSchema contains(String subs) {
        setValid(getValid().and(s -> s.toString().contains(subs)));
        return this;
    }
}
