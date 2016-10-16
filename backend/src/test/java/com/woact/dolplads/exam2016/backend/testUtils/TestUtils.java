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
     * @param entityInstance
     * @param property
     * @return the number of failed constraint for a given property
     */
    public static int violations(Object entityInstance, String property) {
        Set<ConstraintViolation<Object>> violations = validator.validateProperty(entityInstance, property);
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
     * @param entityInstance
     * @return number of violations
     */
    public static int violations(Object entityInstance) {
        if (validator == null) {
            throw new RuntimeException("validator == null, set validator");
        }
        Set<ConstraintViolation<Object>> violations = validator.validate(entityInstance);
        logViolations(violations);

        return violations.size();
    }

    public static User getValidUser() {
        User u = new User("thomas", "dolplads", "userName", new Address("street", "1342", CountryEnum.Norway));
        u.setPasswordHash("hashedpassword");
        u.setSalt("aSalts");

        return u;
    }

    public static void setValidator(Validator validator) {
        TestUtils.validator = validator;
    }

}
