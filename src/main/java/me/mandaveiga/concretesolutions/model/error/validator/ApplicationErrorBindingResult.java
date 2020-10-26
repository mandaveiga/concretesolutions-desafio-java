package me.mandaveiga.concretesolutions.model.error.validator;

import org.springframework.validation.AbstractBindingResult;
import org.springframework.validation.DefaultMessageCodesResolver;

import java.lang.reflect.Field;

public class ApplicationErrorBindingResult extends AbstractBindingResult {

    private Object target;

    /**
     * Create a new AbstractBindingResult instance.
     *
     * @param objectName the name of the target object
     * @see DefaultMessageCodesResolver
     */
    protected ApplicationErrorBindingResult(String objectName) {
        super(objectName);
    }

    public ApplicationErrorBindingResult(Object target) {
        super(target.getClass().getName());
        this.target = target;
    }

    @Override
    public Object getTarget() {
        return this.target;
    }

    @Override
    protected Object getActualFieldValue(String fieldName) {
        try {
            Field field = this.target.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);

            return field.get(getTarget());
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }
}
