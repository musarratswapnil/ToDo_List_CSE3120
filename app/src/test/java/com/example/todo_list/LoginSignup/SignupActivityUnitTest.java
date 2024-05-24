package com.example.todo_list.LoginSignup;

import static org.junit.Assert.*;
import org.junit.Test;
public class SignupActivityUnitTest {

        @Test
        public void PasswordIsValid() {
            String validPassword = "Abc@1234";
            assertTrue(SignupActivity.isValid(validPassword));

            String noSpecialChar = "Abc12345";
            assertFalse(SignupActivity.isValid(noSpecialChar));

            String noDigit = "Abcdef@gh";
            assertFalse(SignupActivity.isValid(noDigit));

            String noLetter = "12345@67";
            assertFalse(SignupActivity.isValid(noLetter));

            String tooShort = "A@1";
            assertFalse(SignupActivity.isValid(tooShort));

            String edgeCase = "A1b@1234";
            assertTrue(SignupActivity.isValid(edgeCase));
        }


}