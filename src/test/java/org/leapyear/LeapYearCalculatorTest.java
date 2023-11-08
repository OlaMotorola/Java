package org.leapyear;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class LeapYearCalculatorTest {

    @Test
    void testLeapYear() {
        final LeapYearCalculator leapYearCalculator = new LeapYearCalculator();
        assertTrue(leapYearCalculator.isLeapYear(1600));  // 1600 is a leap year
        assertTrue(LeapYearCalculator.isLeapYear(2000));  // 2000 is a leap year
    }

    @Test
    void testNonLeapYear() {
        assertFalse(LeapYearCalculator.isLeapYear(1700)); // 1700 is not a leap year
        assertFalse(LeapYearCalculator.isLeapYear(2017)); // 2017 is not a leap year
    }

    @Test
    void testOutOfRangeYear() {
        assertFalse(LeapYearCalculator.isLeapYear(-1600));// -1600 is out of range
    }
}