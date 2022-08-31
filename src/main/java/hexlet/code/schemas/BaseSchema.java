package hexlet.code.schemas;

import java.util.function.Predicate;

public abstract class BaseSchema<T> {

    private Predicate<T> allValidations = s -> true;
    private final Predicate<Object> requiredValidation;
    private boolean isCheckRequired = false;

    protected BaseSchema(Predicate<Object> requiredSchema) {
        requiredValidation = requiredSchema;
    }
    protected final void setIsCheckRequired(boolean checkRequired) {
        isCheckRequired = checkRequired;
    }

    protected final Predicate<Object> getRequiredValidation() {
        return requiredValidation;
    }

    protected final Predicate<T> getAllValidations() {
        return allValidations;
    }

    public final boolean isValid(Object value) {
        if (isCheckRequired) {
            return getRequiredValidation().test(value) && getAllValidations().test((T) value);
        } else {
            return !getRequiredValidation().test(value) || getAllValidations().test((T) value);
        }
    }

    protected final void addValidation(Predicate<T> predicate) {
        allValidations = getAllValidations().and(predicate);
    }
}
