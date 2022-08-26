package hexlet.code.schemas;

import java.util.Map;

public final class MapSchema extends BaseSchema {

    public MapSchema required() {
        setValid(m -> m instanceof Map<?, ?>);
        return this;
    }

    public MapSchema sizeof(int count) {
        setValid(getValid().and(m -> ((Map<?, ?>) m).size() == count));
        return this;
    }
}
