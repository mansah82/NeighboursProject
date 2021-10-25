package com.example.neighbourproject.user

import junit.framework.TestCase
import org.junit.Test

class EvaluationHelperTest : TestCase(){
    @Test
    fun testPassword() {
        assertEquals(null, EvaluationHelper.evaluatePassword("1"))
        assertEquals(null, EvaluationHelper.evaluatePassword("1234678"))
        assertEquals(null , EvaluationHelper.evaluatePassword("pwd123"))
        assertEquals(null , EvaluationHelper.evaluatePassword("Pwd12"))

        assertEquals("Pwd123", EvaluationHelper.evaluatePassword("Pwd123"))
        assertEquals("Password", EvaluationHelper.evaluatePassword("Password"))
        assertEquals("Password", EvaluationHelper.evaluatePassword(" Password "))
    }

    @Test
    fun testUsername() {
        assertEquals(null, EvaluationHelper.evaluateUsername("1"))
        assertEquals(null, EvaluationHelper.evaluateUsername("@."))
        assertEquals(null, EvaluationHelper.evaluateUsername("@test.com"))
        assertEquals(null, EvaluationHelper.evaluateUsername("@.com"))
        assertEquals(null, EvaluationHelper.evaluateUsername("a2d@aswwww"))


        assertEquals("a@b.c", EvaluationHelper.evaluateUsername("a@b.c"))
        assertEquals("a@test.com", EvaluationHelper.evaluateUsername("a@test.com"))


        assertEquals("te2st1@test1.com", EvaluationHelper.evaluateUsername("te2st1@test1.com"))
        assertEquals(null, EvaluationHelper.evaluateUsername("test@test.co1m"))
        assertEquals("test@test.com", EvaluationHelper.evaluateUsername("test@test.com"))
        assertEquals("test@test.com", EvaluationHelper.evaluateUsername(" test@test.com "))
    }
}


