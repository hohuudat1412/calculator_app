package com.example.calculator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class calculator_tp_test {

    @Test
    public void testAdd() {
        calculator_tp calc = new calculator_tp();
        assertEquals(1, calc.add(5, 4));  // Kiểm tra 3 + 4 = 7
    }
}
