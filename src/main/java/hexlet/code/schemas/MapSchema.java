package hexlet.code.schemas;

import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

public final class MapSchema extends BaseSchema<Map<?, ?>> {

    public MapSchema() {
        super(value -> value instanceof Map<?, ?>);
    }

    public MapSchema required() {
        setIsCheckRequired(true);
        return this;
    }

    public MapSchema sizeof(int count) {
        Predicate<Map<?, ?>> sizeofPredicate = m1 -> m1.size() == count;
        addValidation(sizeofPredicate);
        return this;
    }

    public MapSchema shape(Map<?, BaseSchema> schemas) {
        Predicate<Map<?, ?>> shapePredicate = m1 -> {
            Set<?> keys = m1.keySet();
            keys.retainAll(schemas.keySet());
            for (Object el : keys) {
                if (!schemas.get(el).isValid(m1.get(el))) {
                    return false;
                }
            }
            return true;
        };
        addValidation(shapePredicate);
        return this;
    }
}
