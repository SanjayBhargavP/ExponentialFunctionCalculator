package org.project;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit test class for the ExponentialFunctionApp.
 * This class contains test cases for the methods of ExponentialFunctionApp,
 * ensuring they behave as expected for various input scenarios.
 */
public class ExponentialFunctionAppTest {

    /**
     * Test case for parsing the mathematical constant pi.
     * Verifies that the parseInput method correctly returns Math.PI
     * when the input string is "pi".
     */
    @Test
    public void testParseInputPi() {
        double expected = Math.PI;
        double result = ExponentialFunctionApp.parseInput("pi");
        assertEquals(expected, result, 0.0001, "Parsing 'pi' should return Math.PI");
    }

    /**
     * Test case for parsing the mathematical constant e.
     * Verifies that the parseInput method correctly returns Math.E
     * when the input string is "e".
     */
    @Test
    public void testParseInputE() {
        double expected = Math.E;
        double result = ExponentialFunctionApp.parseInput("e");
        assertEquals(expected, result, 0.0001, "Parsing 'e' should return Math.E");
    }

    /**
     * Test case for parsing a simple fraction.
     * Verifies that the parseInput method correctly parses the input "1/2"
     * and returns 0.5.
     */
    @Test
    public void testParseInputFraction() {
        double expected = 0.5;
        double result = ExponentialFunctionApp.parseInput("1/2");
        assertEquals(expected, result, 0.0001, "Parsing '1/2' should return 0.5");
    }

    /**
     * Test case for parsing an invalid fraction with a zero denominator.
     * Verifies that the parseInput method throws a NumberFormatException
     * when attempting to parse "1/0".
     */
    @Test
    public void testParseInputInvalidFraction() {
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            ExponentialFunctionApp.parseInput("1/0");
        });
        assertEquals("Denominator cannot be zero.", exception.getMessage());
    }

    /**
     * Test case for parsing a square root.
     * Verifies that the parseInput method correctly parses the input "sqrt(4)"
     * and returns 2.0.
     */
    @Test
    public void testParseInputSquareRoot() {
        double expected = 2.0;
        double result = ExponentialFunctionApp.parseInput("sqrt(4)");
        assertEquals(expected, result, 0.0001, "Parsing 'sqrt(4)' should return 2.0");
    }

    /**
     * Test case for parsing a root.
     * Verifies that the parseInput method correctly parses the input "root(3,8)"
     * and returns 2.0.
     */
    @Test
    public void testParseInputRoot() {
        double expected = 2.0;
        double result = ExponentialFunctionApp.parseInput("root(3,8)");
        assertEquals(expected, result, 0.0001, "Parsing 'root(3,8)' should return 2.0");
    }

    /**
     * Test case for parsing an invalid root.
     * Verifies that the parseInput method throws a NumberFormatException
     * when attempting to parse an even root of a negative number, such as "root(2,-4)".
     */
    @Test
    public void testParseInputInvalidRoot() {
        Exception exception = assertThrows(NumberFormatException.class, () -> {
            ExponentialFunctionApp.parseInput("root(2,-4)");
        });
        assertEquals("Cannot take even root of a negative number.", exception.getMessage());
    }

    /**
     * Test case for calculating exponential functions.
     * Verifies that the calculateExponentialFunction method correctly calculates
     * the expression 2 * 3^2, returning 18.0.
     */
    @Test
    public void testCalculateExponentialFunction() {
        double a = 2.0;
        double b = 3.0;
        double x = 2.0;
        double expected = 18.0; // 2 * 3^2
        double result = ExponentialFunctionApp.calculateExponentialFunction(a, b, x);
        assertEquals(expected, result, 0.0001, "2 * 3^2 should equal 18.0");
    }

    /**
     * Test case for calculating exponential functions with a negative exponent.
     * Verifies that the calculateExponentialFunction method correctly calculates
     * the expression 1 * 2^-2, returning 0.25.
     */
    @Test
    public void testCalculateExponentialFunctionNegativeExponent() {
        double a = 1.0;
        double b = 2.0;
        double x = -2.0;
        double expected = 0.25; // 1 * 2^-2
        double result = ExponentialFunctionApp.calculateExponentialFunction(a, b, x);
        assertEquals(expected, result, 0.0001, "1 * 2^-2 should equal 0.25");
    }

    /**
     * Test case for calculating exponential functions with a zero exponent.
     * Verifies that the calculateExponentialFunction method correctly calculates
     * the expression 3 * 4^0, returning 3.0.
     */
    @Test
    public void testCalculateExponentialFunctionZeroExponent() {
        double a = 3.0;
        double b = 4.0;
        double x = 0.0;
        double expected = 3.0; // 3 * 4^0
        double result = ExponentialFunctionApp.calculateExponentialFunction(a, b, x);
        assertEquals(expected, result, 0.0001, "3 * 4^0 should equal 3.0");
    }

    /**
     * Test case for calculating exponential functions with a zero base.
     * Verifies that the calculateExponentialFunction method correctly calculates
     * the expression 3 * 0^2, returning 0.0.
     */
    @Test
    public void testCalculateExponentialFunctionZeroBase() {
        double a = 3.0;
        double b = 0.0;
        double x = 2.0;
        double expected = 0.0; // 3 * 0^2
        double result = ExponentialFunctionApp.calculateExponentialFunction(a, b, x);
        assertEquals(expected, result, 0.0001, "3 * 0^2 should equal 0.0");
    }

    /**
     * Test case for calculating exponential functions with a negative base
     * and a non-integer exponent.
     * Verifies that the calculateExponentialFunction method throws an ArithmeticException
     * when attempting to calculate such an expression.
     */
    @Test
    public void testCalculateExponentialFunctionInvalidBase() {
        double a = 1.0;
        double b = -2.0;
        double x = 0.5;
        Exception exception = assertThrows(ArithmeticException.class, () -> {
            ExponentialFunctionApp.calculateExponentialFunction(a, b, x);
        });
        assertEquals("Cannot raise a negative base to a non-integer power in the real number system.", exception.getMessage());
    }
}
