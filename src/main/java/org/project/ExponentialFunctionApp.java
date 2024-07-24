package org.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

/**
 * ExponentialFunctionApp is a JavaFX application that calculates the exponential function a * b^x.
 * It accepts inputs for a, b, and x in various formats and provides a GUI for interaction.
 */
public class ExponentialFunctionApp extends Application {

    private static final double PI = 3.141592653589793;
    private static final double E = 2.718281828459045;

    /**
     * Starts the JavaFX application.
     *
     * @param primaryStage the primary stage for this application.
     */
    @Override
    public void start(Stage primaryStage) {
        // GUI Components
        Label labelA = new Label("a value:");
        TextField inputA = new TextField();
        inputA.setPrefWidth(300);
        HBox hBoxA = new HBox(5, labelA, inputA);

        Label labelB = new Label("b value:");
        TextField inputB = new TextField();
        inputB.setPrefWidth(300);
        HBox hBoxB = new HBox(5, labelB, inputB);

        Label labelX = new Label("x value:");
        TextField inputX = new TextField();
        inputX.setPrefWidth(300);
        HBox hBoxX = new HBox(5, labelX, inputX);

        Button calculateButton = new Button("Calculate Exponential");
        Button helpButton = new Button("Help");
        Label resultLabel = new Label();

        inputA.setPromptText("real number, fraction, 'pi', 'e', 'sqrt(x)', 'root(n,x)'");
        inputB.setPromptText("real number, fraction, 'pi', 'e', 'sqrt(x)', 'root(n,x)'");
        inputX.setPromptText("real number, fraction, 'pi', 'e', 'sqrt(x)', 'root(n,x)'");

        calculateButton.setOnAction(e -> {
            StringBuilder errorMessage = new StringBuilder();
            StringBuilder detailedMessage = new StringBuilder();

            double a = 0, b = 0, x = 0;
            boolean hasError = false;

            try {
                a = parseInput(inputA.getText(), "a");
            } catch (NumberFormatException ex) {
                errorMessage.append("Invalid input for field a.\n");
                detailedMessage.append(ex.getMessage()).append("\n");
                hasError = true;
            }

            try {
                b = parseInput(inputB.getText(), "b");
            } catch (NumberFormatException ex) {
                errorMessage.append("Invalid input for field b.\n");
                detailedMessage.append(ex.getMessage()).append("\n");
                hasError = true;
            }

            try {
                x = parseInput(inputX.getText(), "x");
            } catch (NumberFormatException ex) {
                errorMessage.append("Invalid input for field x.\n");
                detailedMessage.append(ex.getMessage()).append("\n");
                hasError = true;
            }

            if (hasError) {
                showDetailedAlert("Input Error", errorMessage.toString(), detailedMessage.toString());
                resultLabel.setText("");
            } else {
                try {
                    double result = calculateExponentialFunction(a, b, x);
                    resultLabel.setText(String.format("The result of %.5f * %.5f^%.5f is: %.5f", a, b, x, result));
                } catch (ArithmeticException ex) {
                    showAlert("Calculation Error", ex.getMessage());
                    resultLabel.setText("");
                }
            }
        });

        helpButton.setOnAction(e -> showHelpDialog());

        VBox vbox = new VBox(10, hBoxA, hBoxB, hBoxX, calculateButton, resultLabel, helpButton);
        Scene scene = new Scene(vbox, 400, 300);

        primaryStage.setTitle("Exponential Function Calculator for a(b^x)");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * Main entry point for the application.
     *
     * @param args command-line arguments (not used).
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Parses the user input and converts it to a double value.
     *
     * @param input     the user input string.
     * @param fieldName the name of the field being parsed (for error messages).
     * @return the parsed double value.
     * @throws NumberFormatException if the input is not a valid number.
     */
    public static double parseInput(String input, String fieldName) throws NumberFormatException {
        input = input.trim().toLowerCase();

        if (input.equals("pi")) {
            return PI;
        } else if (input.equals("e")) {
            return E;
        } else if (input.contains("/")) {
            String[] parts = input.split("/");
            if (parts.length == 2) {
                double numerator = Double.parseDouble(parts[0]);
                double denominator = Double.parseDouble(parts[1]);
                if (denominator != 0) {
                    return numerator / denominator;
                } else {
                    throw new NumberFormatException("Denominator cannot be zero for field " + fieldName);
                }
            } else {
                throw new NumberFormatException("Invalid fraction format for field " + fieldName + ". Use numerator/denominator.");
            }
        } else if (input.startsWith("sqrt(") && input.endsWith(")")) {
            String number = input.substring(5, input.length() - 1);
            double value = Double.parseDouble(number);
            if (value < 0) {
                throw new NumberFormatException("Cannot take square root of a negative number for field " + fieldName);
            }
            return sqrt(value);
        } else if (input.startsWith("root(") && input.endsWith(")")) {
            String[] parts = input.substring(5, input.length() - 1).split(",");
            if (parts.length == 2) {
                int n = Integer.parseInt(parts[0]);
                double x = Double.parseDouble(parts[1]);
                if (x < 0 && n % 2 == 0) {
                    throw new NumberFormatException("Cannot take even root of a negative number for field " + fieldName);
                }
                return nthRoot(n, x);
            } else {
                throw new NumberFormatException("Invalid root format for field " + fieldName + ". Use root(n,x).");
            }
        } else {
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                throw new NumberFormatException("Invalid input for field " + fieldName + ". Enter a real number, fraction, 'pi', 'e', 'sqrt(x)', or 'root(n,x)'.");
            }
        }
    }

    /**
     * Calculates the exponential function a * b^x.
     *
     * @param a the multiplier (real constant).
     * @param b the base (real constant).
     * @param x the exponent (real variable).
     * @return the calculated value of a * b^x.
     * @throws ArithmeticException if the calculation cannot be performed.
     */
    public static double calculateExponentialFunction(double a, double b, double x) throws ArithmeticException {
        if (b < 0 && customFloor(x) != x) {
            throw new ArithmeticException("Cannot raise a negative base to a non-integer power in the real number system.");
        }
        return a * power(b, x);
    }

    /**
     * Custom implementation of the power function.
     *
     * @param base     the base value.
     * @param exponent the exponent value.
     * @return the result of raising the base to the given exponent.
     */
    public static double power(double base, double exponent) {
        if (exponent == 0) {
            return 1;
        }
        boolean isNegativeExponent = exponent < 0;
        if (isNegativeExponent) {
            exponent = -exponent;
        }
        double result = 1;
        for (int i = 0; i < exponent; i++) {
            result *= base;
        }
        if (isNegativeExponent) {
            return 1 / result;
        }
        return result;
    }

    /**
     * Custom implementation of the square root function.
     *
     * @param number the value to compute the square root of.
     * @return the square root of the given number.
     */
    public static double sqrt(double number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot compute the square root of a negative number");
        }
        double epsilon = 1e-10;
        double guess = number / 2.0;
        while (customAbs(guess * guess - number) > epsilon) {
            guess = (guess + number / guess) / 2.0;
        }
        return guess;
    }

    /**
     * Custom implementation of the nth root function.
     *
     * @param n the root to compute.
     * @param x the value to compute the nth root of.
     * @return the nth root of the given value.
     */
    public static double nthRoot(int n, double x) {
        if (x < 0 && n % 2 == 0) {
            throw new IllegalArgumentException("Cannot compute even root of a negative number");
        }
        double guess = x / n;
        double epsilon = 1e-10;
        while (customAbs(power(guess, n) - x) > epsilon) {
            guess = ((n - 1) * guess + x / power(guess, n - 1)) / n;
        }
        return guess;
    }

    /**
     * Custom implementation of the absolute value function.
     *
     * @param value the value to compute the absolute value of.
     * @return the absolute value of the given number.
     */
    public static double customAbs(double value) {
        return value < 0 ? -value : value;
    }

    /**
     * Custom implementation of the floor function.
     *
     * @param value the value to compute the floor of.
     * @return the largest integer less than or equal to the given value.
     */
    public static double customFloor(double value) {
        int intValue = (int) value;
        return value >= 0 || intValue == value ? intValue : intValue - 1;
    }

    /**
     * Displays a help dialog with instructions for using the application.
     */
    private void showHelpDialog() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Instructions for Exponential Function Calculator a(b^x)");
        alert.setContentText(
                "You can enter values in the following formats for a, b and x:\n" +
                        "- Real numbers (e.g., 5, -3.2, 0.25)\n" +
                        "- Fractions as 'numerator/denominator' (e.g., 1/2, 3/4)\n" +
                        "- Constants 'pi' and 'e' for mathematical constants π and e\n" +
                        "- Roots in the format 'root(n,x)' for the n-th root of x (e.g., root(3,8))\n" +
                        "- 'sqrt(x)' for the square root of x.\n" +
                        "Please enter the values as instructed when prompted.\n"
        );
        alert.showAndWait();
    }

    /**
     * Displays an alert dialog with a given title and message.
     *
     * @param title   the title of the alert.
     * @param message the message to display in the alert.
     */
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays an alert dialog with a given title, summary message, and detailed message.
     *
     * @param title            the title of the alert.
     * @param summaryMessage   the summary message to display in the alert.
     * @param detailedMessage  the detailed message to display in the alert.
     */
    private void showDetailedAlert(String title, String summaryMessage, String detailedMessage) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);

        Hyperlink moreDetailsLink = new Hyperlink("More details");
        moreDetailsLink.setOnAction(e -> {
            Alert detailsAlert = new Alert(Alert.AlertType.INFORMATION);
            detailsAlert.setTitle("Detailed Error Information");
            detailsAlert.setHeaderText("Detailed Error Information");

            TextArea textArea = new TextArea(detailedMessage);
            textArea.setWrapText(true);
            textArea.setEditable(false);

            ScrollPane scrollPane = new ScrollPane(textArea);
            scrollPane.setFitToWidth(true);
            scrollPane.setPrefSize(400, 200);

            detailsAlert.getDialogPane().setContent(scrollPane);
            detailsAlert.showAndWait();
        });

        TextFlow content = new TextFlow(
                new Text(summaryMessage),
                new Text("\n"),
                moreDetailsLink
        );
        alert.getDialogPane().setContent(content);

        alert.showAndWait();
    }
}
