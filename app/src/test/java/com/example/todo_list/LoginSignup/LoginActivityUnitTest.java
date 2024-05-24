package com.example.todo_list.LoginSignup;

import static org.junit.Assert.*;

import org.junit.Test;

public class LoginActivityUnitTest {


    @Test
    public void testCheckEmailEmpty() {

        assertTrue(LoginActivity.checkEmailEmpty(""));


        assertFalse(LoginActivity.checkEmailEmpty("test@example.com"));
    }

    @Test
    public void testCheckPasswordEmpty() {

        assertTrue(LoginActivity.checkPasswordEmpty(""));

        // Test with a non-empty password
        assertFalse(LoginActivity.checkPasswordEmpty("password123"));
    }

    @Test
    public void testCheckPasswordLength() {

        assertTrue(LoginActivity.checkPasswordLength("12345"));


        assertFalse(LoginActivity.checkPasswordLength("123456"));


        assertFalse(LoginActivity.checkPasswordLength("1234567"));
    }
}