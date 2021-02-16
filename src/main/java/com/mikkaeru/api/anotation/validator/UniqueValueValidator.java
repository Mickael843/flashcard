package com.mikkaeru.api.anotation.validator;

import com.mikkaeru.api.anotation.UniqueValue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class UniqueValueValidator implements ConstraintValidator<UniqueValue, Object> {

    private Class<?> klass;
    private String domainAttribute;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void initialize(UniqueValue constraintAnnotation) {
        klass = constraintAnnotation.domainClass();
        domainAttribute = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {

        String[] tmp = klass.getName().split("\\.");
        String klassName = tmp[tmp.length - 1].toLowerCase(Locale.ROOT);

        String sql = "SELECT " + domainAttribute + " FROM " + klassName + " WHERE " + domainAttribute + "=" + "'" +value+ "'";

        List<String> list = jdbcTemplate.query(sql, rs -> {

            List<String> listTmp = new ArrayList<>();

            while (rs.next()) {

                var field = rs.getString(domainAttribute);

                listTmp.add(field);

            }

            return listTmp;
        });

        Assert.state(list.size() <= 1, "Foi encontrado mais de um " + klass + " com o atributo " + domainAttribute+" = " + value);

        return list.isEmpty();
    }
}
