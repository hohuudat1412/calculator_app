package com.example.calculator;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
public class calculator_tp_test {
    @Test
    public void testDivideWithPrecision() {
        calculator_tp calc = new calculator_tp();
        assertEquals(2.5, calc.divide(5.0, 2.0), 0.0001);
    }
}
