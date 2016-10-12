package com.woact.dolplads.entity;

import com.woact.dolplads.testUtils.TestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.lang.reflect.Field;

import static com.woact.dolplads.testUtils.TestUtils.getValidUser;
import static com.woact.dolplads.testUtils.TestUtils.violations;
import static org.junit.Assert.*;

/**
 * Created by dolplads on 12/10/2016.
 * <p>
 * Testing constraints on user object
 */
public class UserTest {
    private ValidatorFactory validatorFactory;

    @Before
    public void setUp() throws Exception {
        validatorFactory = Validation.buildDefaultValidatorFactory();
        Validator validator = validatorFactory.getValidator();
        TestUtils.setValidator(validator);
    }

    @After
    public void tearDown() throws Exception {
        validatorFactory.close();
    }

    @Test
    public void canConstruct() throws Exception {
        assertEquals(0, violations(getValidUser()));
    }

    @Test
    public void setName() throws Exception {
        User user = getValidUser();
        String field = "name";

        constraintTest(user, 1, field, null);
        constraintTest(user, 2, field, "");
        constraintTest(user, 1, field, "   ");
        constraintTest(user, 1, field, getLongString(100));
    }

    @Test
    public void setSurName() throws Exception {
        User user = getValidUser();
        String field = "surName";

        constraintTest(user, 1, field, null);
        constraintTest(user, 2, field, "");
        constraintTest(user, 1, field, "   ");
        constraintTest(user, 1, field, getLongString(100));
    }

    @Test
    public void setAddress() throws Exception {
        User user = getValidUser();
        String field = "address";

        constraintTest(user, 1, field, null);
    }

    private void constraintTest(User userInstance, int nOfViolations, String field, Object value) throws Exception {
        Field declared = User.class.getDeclaredField(field);

        declared.setAccessible(true);
        declared.set(userInstance, value);
        declared.setAccessible(false);

        assertEquals(nOfViolations, violations(userInstance));
    }

    private String getLongString(int length) {
        String longString = "";

        for (int i = 0; i < (length / 3); i++) {
            longString += "foo";
        }
        return longString;
    }
}