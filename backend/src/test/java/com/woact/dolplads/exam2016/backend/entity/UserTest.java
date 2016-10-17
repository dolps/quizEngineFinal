package com.woact.dolplads.exam2016.backend.entity;

import com.woact.dolplads.exam2016.backend.enums.CountryEnum;
import com.woact.dolplads.exam2016.backend.testUtils.TestUtils;
import lombok.extern.java.Log;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import java.lang.reflect.Field;

import static com.woact.dolplads.exam2016.backend.testUtils.TestUtils.violations;
import static org.junit.Assert.*;

/**
 * Created by dolplads on 12/10/2016.
 * <p>
 * Testing annotations on user object
 */
@Log
@Ignore
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

        constraintTest(1, user, field, null);
        constraintTest(1, user, field, "");
        constraintTest(1, user, field, "   ");
        constraintTest(1, user, field, getLongString(100));
    }

    @Test
    public void setSurName() throws Exception {
        User user = getValidUser();
        String field = "surName";

        constraintTest(1, user, field, null);
        constraintTest(1, user, field, "");
        constraintTest(1, user, field, "   ");
        constraintTest(1, user, field, getLongString(100));
    }

    private void constraintTest(int nOfViolations, Object entity, String field, Object value) throws Exception {
        Field declared = entity.getClass().getDeclaredField(field);

        declared.setAccessible(true);
        declared.set(entity, value);
        declared.setAccessible(false);

        assertEquals(nOfViolations, violations(entity, field));
    }

    private String getLongString(int length) {
        String longString = "";

        for (int i = 0; i < (length / 3); i++) {
            longString += "foo";
        }
        return longString;
    }


    private User getValidUser() {
        User u = new User("userName", "user", "us", "userson");
        u.setPasswordHash("hash");
        u.setSalt("salt");
        assertEquals(0, TestUtils.violations(u));

        return u;
    }
}