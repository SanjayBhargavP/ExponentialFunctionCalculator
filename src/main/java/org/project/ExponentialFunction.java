package org.project;

import java.util.Scanner;

public class ExponentialFunction {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Input variables
        double a = 0;
        double b = 0;
        double x = 0;

        // Input and validation for 'a'
        while (true) {
            try {
                System.out.print("Enter the value for a (real constant): ");
                a = parseInput(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a real number.");
            }
        }

        // Input and validation for 'b'
        while (true) {
            try {
                System.out.print("Enter the value for b (real constant): ");
                b = parseInput(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a real number.");
            }
        }

        // Input and validation for 'x'
        while (true) {
            try {
                System.out.print("Enter the value for x (real variable): ");
                x = parseInput(scanner.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a real number.");
            }
        }

        // Calculation
        try {
            double result = calculateExponentialFunction(a, b, x);
            // Output the result
            System.out.printf("The result of the function %.5f * %.5f^%.5f is: %.5f%n", a, b, x, result);
        } catch (ArithmeticException e) {
            System.out.println("Error in calculation: " + e.getMessage());
        }
    }

    /**
     * This method parses the input and handles fractions in the form of numerator/denominator.
     *
     * @param input the user input string
     * @return the parsed double value
     * @throws NumberFormatException if the input is not a valid number
     */
    public static double parseInput(String input) throws NumberFormatException {
        if (input.contains("/")) {
            String[] parts = input.split("/");
            if (parts.length == 2) {
                double numerator = Double.parseDouble(parts[0]);
                double denominator = Double.parseDouble(parts[1]);
                if (denominator != 0) {
                    return numerator / denominator;
                } else {
                    throw new NumberFormatException("Denominator cannot be zero.");
                }
            } else {
                throw new NumberFormatException("Invalid fraction format.");
            }
        } else {
            return Double.parseDouble(input);
        }
    }

    /**
     * This method calculates the value of the exponential function a * b^x.
     *
     * @param a the multiplier (real constant)
     * @param b the base (real constant)
     * @param x the exponent (real variable)
     * @return the calculated value of a * b^x
     * @throws ArithmeticException if the calculation cannot be performed
     */
    public static double calculateExponentialFunction(double a, double b, double x) throws ArithmeticException {
        if (b < 0 && x != Math.floor(x)) {
            throw new ArithmeticException("Cannot raise a negative base to a non-integer power in the real number system.");
        }
        return a * Math.pow(b, x);
    }
}
