package com.woact.dolplads.annotations;

import lombok.extern.java.Log;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.logging.Level;

/**
 * Created by dolplads on 14/10/2016.
 */
@Log
public class FieldMatchValidator implements ConstraintValidator<EqualTo, Object> {
    private String field1;
    private String field2;

    public void initialize(EqualTo constraint) {
        field1 = constraint.first();
        field2 = constraint.second();
    }

    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        try {
            final Field first = obj.getClass().getDeclaredField(field1);
            final Field second = obj.getClass().getDeclaredField(field2);

            first.setAccessible(true);
            second.setAccessible(true);

            String firstValue = first.get(obj).toString();
            String secondValue = second.get(obj).toString();

            first.setAccessible(false);
            second.setAccessible(false);

            return firstValue.equals(secondValue);

        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }

        return false;
    }
}
