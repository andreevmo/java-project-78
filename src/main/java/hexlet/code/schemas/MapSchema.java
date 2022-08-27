package hexlet.code.schemas;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema {

    private final Predicate<Object> schemaPredicate = value -> value instanceof Map<?, ?>;

    public MapSchema required() {
        setValid(getValid().and(schemaPredicate));
        return this;
    }

    public MapSchema sizeof(int count) {
        Predicate<Object> sizeofPredicate = m1 -> ((Map<?, ?>) m1).size() == count;
        setValid(getValid().and(m -> check(m, sizeofPredicate, schemaPredicate)));
        return this;
    }

    public MapSchema shape(Map<?, BaseSchema> schemas) {
        Predicate<Object> shapePredicate = m1 -> {
            Set<?> keys = ((Map<?, ?>) m1).keySet();
            keys.retainAll(schemas.keySet());
            for (Object el : keys) {
                if (!schemas.get(el).isValid(((Map<?, ?>) m1).get(el))) {
                    return false;
                }
            }
            return true;
        };
        setValid(getValid().and(m -> check(m, shapePredicate, schemaPredicate)));
        return this;
    }
}
