package hexlet.code.schemas;

import java.util.function.Predicate;

public class BaseSchema<T> {

    private Predicate<T> baseSchemaPredicate = s -> true;
    private Predicate<Object> schemaPredicate;
    private boolean isCheckRequired = false;

    public BaseSchema(Predicate<Object> newSchemaPredicate) {
        setSchemaPredicate(newSchemaPredicate);
    }

    public final boolean getIsCheckRequired() {
        return isCheckRequired;
    }
    public final void setIsCheckRequired(boolean checkRequired) {
        isCheckRequired = checkRequired;
    }

    public final Predicate<Object> getSchemaPredicate() {
        return schemaPredicate;
    }

    public final void setSchemaPredicate(Predicate<Object> newSchemaPredicate) {
        this.schemaPredicate = newSchemaPredicate;
    }

    public final Predicate<T> getBaseSchemaPredicate() {
        return baseSchemaPredicate;
    }

    public final void setBaseSchemaPredicate(Predicate<T> newValid) {
        this.baseSchemaPredicate = newValid;
    }

    public final boolean isValid(Object value) {
        if (getIsCheckRequired()) {
            return getSchemaPredicate().test(value) && getBaseSchemaPredicate().test((T) value);
        } else {
            return !getSchemaPredicate().test(value) || getBaseSchemaPredicate().test((T) value);
        }
    }

    public final void addValidation(Predicate<T> predicate) {
        setBaseSchemaPredicate(getBaseSchemaPredicate().and(predicate));
    }
}
