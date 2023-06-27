package com.example.hangmangame

import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ValidationTest {
    @Test
    fun testValidEmail() {
        val validEmails = listOf(
            "test@example.com",
            "user123@gmail.com",
            "john.doe@example.pl"
        )

        for (email in validEmails) {
            assertTrue(ValidationClass.isEmailValid(email))
        }
    }

    @Test
    fun testInvalidEmail() {
        val invalidEmails = listOf(
            "notanemail",
            "user@domain",
            "user@example",
        )

        for (email in invalidEmails) {
            assertFalse(ValidationClass.isEmailValid(email))
        }
    }
}