package hexlet.code.schemas;

import java.util.Map;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {

    public MapSchema required() {
        setValid(getValid().and(m -> m instanceof Map<?, ?>));
        return this;
    }

    public MapSchema sizeof(int count) {
        setValid(getValid().and(m -> check(m, m1 -> ((Map<?, ?>) m1).size() == count)));
        return this;
    }

    public MapSchema shape(Map<?, BaseSchema> schemas) {
        Predicate<Object> p = m1 -> {
            for (Map.Entry<?, ?> el : ((Map<?, ?>) m1).entrySet()) {
                if (schemas.containsKey(el.getKey())) {
                    if (!schemas.get(el.getKey()).isValid(el.getValue())) {
                        return false;
                    }
                }
            }
            return true;
        };
        setValid(getValid().and(m -> check(m, p)));
        return this;
    }

    public boolean check(Object o, Predicate<Object> predicate) {
        if (o == null) {
            return true;
        } else if (o instanceof Map<?, ?>) {
            return predicate.test(o);
        }
        return false;
    }
}
