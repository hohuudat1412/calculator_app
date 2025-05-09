package com.example.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CalculatorTest {

    @Test
    public void testAddition() {
        assertEquals(8.0, calculator.calculate(5, 3, "+"), 0.0001);
    }

    @Test
    public void testSubtraction() {
        assertEquals(2.0, calculator.calculate(5, 3, "-"), 0.0001);
    }

    @Test
    public void testMultiplication() {
        assertEquals(15.0, calculator.calculate(5, 3, "×"), 0.0001);
    }

    @Test
    public void testDivision() {
        assertEquals(2.5, calculator.calculate(5, 2, "÷"), 0.0001);
    }

    @Test(expected = ArithmeticException.class)
    public void testDivideByZero() {
        calculator.calculate(5, 0, "÷");
    }
}

