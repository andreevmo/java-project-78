package hexlet.code.schemas;

import java.util.function.Predicate;

public final class StringSchema {

    private Predicate<String> valid = s -> true;

    public boolean isValid(String s) {
        return valid.test(s);
    }

    public StringSchema required() {
        valid = s -> s != null && !s.trim().equals("");
        return this;
    }

    public StringSchema minLength(int minLength) {
        valid =  valid.and(s -> s.length() >= minLength);
        return this;
    }

    public StringSchema contains(String subs) {
        valid = valid.and(s -> s.contains(subs));
        return this;
    }
}
