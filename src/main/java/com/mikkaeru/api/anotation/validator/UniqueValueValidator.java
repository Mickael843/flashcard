package com.mikkaeru.api.anotation.validator;

import com.mikkaeru.api.anotation.UniqueValue;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private Class<?> klass;
    private String domainAttribute;

    @PersistenceContext
    private EntityManager manager;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        klass = constraintAnnotation.domainClass();
        domainAttribute = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        Query query = manager.createQuery("SELECT 1 FROM " + klass.getName() + " WHERE " + domainAttribute + "=:value");
        query.setParameter("value", value);

        List<?> list = query.getResultList();

        Assert.state(list.size() <= 1, "Foi encontrado mais de um " + klass + " com o atributo " + domainAttribute+" = " + value);

        return list.isEmpty();
    }
}
