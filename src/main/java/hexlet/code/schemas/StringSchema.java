package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema extends BaseSchema {

    public StringSchema required() {
        setValid(getValid().and(s -> s instanceof String && !s.toString().trim().equals("")));
        return this;
    }

    public StringSchema minLength(int minLength) {
        setValid(getValid().and(s -> check(s, s1 -> s1.toString().length() >= minLength)));
        return this;
    }

    public StringSchema contains(String subs) {
        setValid(getValid().and(s -> check(s, s1 -> s1.toString().contains(subs))));
        return this;
    }

    public boolean check(Object o, Predicate<Object> predicate) {
        if (o == null) {
            return true;
        } else if (o instanceof String) {
            return predicate.test(o);
        }
        return false;
    }
}
