package pogi.tiger.com.sph;

import org.junit.Test;

import pogi.tiger.com.sph.utils.FormValidator;

import static org.junit.Assert.assertEquals;

/**
 * Created by Pogi on 30/09/2016.
 */

public class ValidationTest {

    @Test
    public void emailValidation() throws Exception {

        final String VALID_EMAIL = "test.valid.email@domain.com";

        assertEquals("Check email if null", FormValidator.isValidEmail(null), false);
        assertEquals("Check email if " + VALID_EMAIL, FormValidator.isValidEmail(VALID_EMAIL), true);
    }

}
