package com.woact.dolplads.constraints;

import lombok.extern.java.Log;

import javax.validation.*;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by dolplads on 07/10/2016.
 */
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
@Documented
@ReportAsSingleViolation
@Constraint(validatedBy = {InBetween.Validator.class})
public @interface InBetween {
    String message() default "{com.woact.dolplads.constraints.InBetween.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    /**
     * @return size the element must be higher or equal to
     */
    int min() default 0;

    int max() default 100;

    @Log
    class Validator implements ConstraintValidator<InBetween, String> {
        private int max;
        private int min;

        @Override
        public void initialize(InBetween constraintAnnotation) {
            max = constraintAnnotation.max();
            min = constraintAnnotation.min();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            context.disableDefaultConstraintViolation();

            String template = context.getDefaultConstraintMessageTemplate() + " " + min + " and " + max;
            context.buildConstraintViolationWithTemplate(template).addConstraintViolation();

            return value != null && value.length() < max && value.length() > min;
        }
    }
}
