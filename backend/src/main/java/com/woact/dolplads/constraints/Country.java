package com.woact.dolplads.constraints;

import com.woact.dolplads.entity.CountryList;
import com.woact.dolplads.enums.CountryEnum;

import javax.validation.*;
import javax.validation.constraints.NotNull;
import java.lang.annotation.*;
import java.util.List;

/**
 * Created by dolplads on 02/10/16.
 */
@Constraint(validatedBy = {Country.Validator.class})
@Target({
        ElementType.METHOD,
        ElementType.FIELD,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@ReportAsSingleViolation
@NotNull
@Documented
public @interface Country {

    String message() default "{com.woact.dolplads.Country.message}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class Validator implements ConstraintValidator<Country, CountryEnum> {
        private List<CountryEnum> countries;

        @Override
        public void initialize(Country constraintAnnotation) {
            countries = new CountryList().getCountryEnumList();
        }

        @Override
        public boolean isValid(CountryEnum country, ConstraintValidatorContext context) {
            return country != null && countries.contains(country);
        }
    }
}
