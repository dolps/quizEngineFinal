package com.woact.dolplads.testUtils;

import com.woact.dolplads.entity.Address;
import com.woact.dolplads.entity.User;
import com.woact.dolplads.enums.CountryEnum;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by dolplads on 12/10/2016.
 */
public class TestUtils {
    private final static Logger logger = Logger.getLogger(TestUtils.class.getSimpleName());
    private static Validator validator;

    /**
     * @param user
     * @param property
     * @return the number of failed constraint for a given property
     */
    public static int violations(User user, String property) {
        Set<ConstraintViolation<Object>> violations = validator.validateProperty(user, property);
        logViolations(violations);

        return violations.size();
    }

    private static void logViolations(Set<ConstraintViolation<Object>> violations) {
        violations.forEach(v -> {
            logger.log(Level.INFO, v.toString() + "\n");
        });
    }

    /**
     * Validates all attributes for a given object
     *
     * @param user
     * @return number of violations
     */
    public static int violations(Object user) {
        if (validator == null) {
            throw new RuntimeException("validator == null, set validator");
        }
        Set<ConstraintViolation<Object>> violations = validator.validate(user);
        logViolations(violations);

        return violations.size();
    }

    public static User getValidUser() {
        return new User("thomas", "dolplads", new Address("street", "1342", CountryEnum.Norway));
    }

    public static void setValidator(Validator validator) {
        TestUtils.validator = validator;
    }

}
