package com.mikkaeru.api.anotation.validator;

import javax.validation.*;
import java.util.Set;

public abstract class SelfValidator<T> {

    private Validator validator;
    public static final String UUID_FORMAT = "^[a-fA-F0-9]{8}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{4}-[a-fA-F0-9]{12}$";
    public static final String INVALID_UUID = "Formato do id externo é inválido!";

    public SelfValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
