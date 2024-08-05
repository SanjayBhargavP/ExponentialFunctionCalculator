package org.project;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.util.Locale;

/**
 * ExponentialFunctionApp is a JavaFX application that calculates the exponential function a * b^x.
 * It provides a graphical user interface (GUI) for inputting values and calculating results.
 */
public class ExponentialFunctionApp extends Application {

    // Constants for mathematical operations
    private static final double PI_CONSTANT = 3.141_592_653_589_793;
    private static final double E_CONSTANT = 2.718_281_828_459_045;
    private static final int EVEN = 2;
    private static final double EPSILON = 1e-10;

    /**
     * Constructor for ExponentialFunctionApp.
     * Sets up initial configurations for the application.
     */
    public ExponentialFunctionApp() {
        super(); // Calls the superclass constructor (Application)
        setupDefaultConfiguration();
    }

    /**
     * Setup default configurations for the application.
     */
    private void setupDefaultConfiguration() {
        // Initialize components, set default values, or prepare resources.
    }

    /**
     * The main entry point for the JavaFX application.
     *
     * @param primaryStage The primary stage for this application.
     */
    @Override
    public void start(final Stage primaryStage) {
        // GUI Components
        final Label labelA = new Label("Value a:");
        final TextField inputFieldA = new TextField();
        inputFieldA.setPrefWidth(150);
        final HBox hBoxA = new HBox(5, labelA, inputFieldA);

        final Label labelB = new Label("Value b:");
        final TextField inputFieldB = new TextField();
        inputFieldB.setPrefWidth(150);
        final HBox hBoxB = new HBox(5, labelB, inputFieldB);

        final Label labelX = new Label("Value x:");
        final TextField inputFieldX = new TextField();
        inputFieldX.setPrefWidth(150);
        final HBox hBoxX = new HBox(5, labelX, inputFieldX);

        final Button calculateButton = new Button("Calculate Exponential");
        final Button helpButton = new Button("Help");
        final Label resultLabel = new Label();

        // Prompt text for user guidance
        inputFieldA.setPromptText("real number, fraction, 'pi', 'e', 'sqrt(x)', 'root(n,x)'");
        inputFieldB.setPromptText("real number, fraction, 'pi', 'e', 'sqrt(x)', 'root(n,x)'");
        inputFieldX.setPromptText("real number, fraction, 'pi', 'e', 'sqrt(x)', 'root(n,x)'");

        calculateButton.setOnAction(e -> {
            final StringBuilder errorMessage = new StringBuilder(1000);
            final StringBuilder detailedMessage = new StringBuilder(1000);

            boolean hasError = false;

            double coefficientA;
            try {
                coefficientA = parseInput(inputFieldA.getText());
            } catch (NumberFormatException ex) {
                errorMessage.append("Invalid input for field a. (more details)\n");
                detailedMessage.append("Allowed values for 'a': real numbers, fractions (e.g., 1/2), constants (pi, e), roots (e.g., sqrt(2)).\n");
                hasError = true;
                coefficientA = 0.0;
            }

            double baseB;
            try {
                baseB = parseInput(inputFieldB.getText());
            } catch (NumberFormatException ex) {
                errorMessage.append("Invalid input for field b. (more details)\n");
                detailedMessage.append("Allowed values for 'b': real numbers, fractions (e.g., 1/2), constants (pi, e), roots (e.g., sqrt(2)).\n");
                hasError = true;
                baseB = 0.0;
            }

            double exponentX;
            try {
                exponentX = parseInput(inputFieldX.getText());

            } catch (NumberFormatException ex) {
                errorMessage.append("Invalid input for field x. (more details)\n");
                detailedMessage.append("Allowed values for 'x': real numbers, fractions (e.g., 1/2), constants (pi, e), roots (e.g., sqrt(2)).\n");
                hasError = true;
                exponentX = 0.0; // Assign a default value to avoid uninitialized error
            }

            if (hasError) {
                showDetailedErrorAlert("Input Error", errorMessage.toString(), detailedMessage.toString());
                resultLabel.setText("");
            } else {
                try {
                    final double result = calculateExponentialFunction(coefficientA, baseB, exponentX);
                    resultLabel.setText(String.format("The result of %.5f * %.5f^%.5f is: %.5f", coefficientA, baseB, exponentX, result));
                } catch (ArithmeticException ex) {
                    showErrorAlert("Calculation Error", ex.getMessage());
                }
            }
        });

        helpButton.setOnAction(e -> showHelp());

        final VBox vbox = new VBox(10, hBoxA, hBoxB, hBoxX, calculateButton, helpButton, resultLabel);
        final Scene scene = new Scene(vbox, 400, 300);

        primaryStage.setTitle("Exponential Function Calculator v1.0.0");
        primaryStage.setScene(scene);
        primaryStage.show();
    }



    /**
     * Main method to launch the JavaFX application.
     *
     * @param args Command-line arguments.
     */
    public static void main(final String[] args) {
        launch(args);
    }

    /**
     * Parses the user input and converts it to a double value.
     *
     * @param input The user input string.
     * @return The parsed double value.
     * @throws NumberFormatException If the input is not a valid number.
     */
    public static double parseInput(final String input) throws NumberFormatException {
        final String normalizedInput = input.trim().toLowerCase(Locale.ROOT);
        double result;

        if ("pi".equals(normalizedInput)) {
            result = PI_CONSTANT;
        } else if ("e".equals(normalizedInput)) {
            result = E_CONSTANT;
        } else if (normalizedInput.contains("/")) {
            final String[] parts = normalizedInput.split("/");
            if (parts.length == EVEN) {
                final double numerator = Double.parseDouble(parts[0]);
                final double denominator = Double.parseDouble(parts[1]);
                if (denominator == 0) {
                    throw new NumberFormatException("Denominator cannot be zero.");
                } else {
                    result = numerator / denominator;
                }
            } else {
                throw new NumberFormatException("Invalid fraction format.");
            }
        } else if (normalizedInput.startsWith("sqrt(") && normalizedInput.endsWith(")")) {
            final String number = normalizedInput.substring(5, normalizedInput.length() - 1);
            final double value = Double.parseDouble(number);
            if (value < 0) {
                throw new NumberFormatException("Cannot take square root of a negative number.");
            }
            result = sqrt(value);
        } else if (normalizedInput.startsWith("root(") && normalizedInput.endsWith(")")) {
            final String[] parts = normalizedInput.substring(5, normalizedInput.length() - 1).split(",");
            if (parts.length == EVEN ) {
                final int rootDegree = Integer.parseInt(parts[0]);
                final double rootValue = Double.parseDouble(parts[1]);
                if (rootValue < 0 && rootDegree % EVEN == 0) {
                    throw new NumberFormatException("Cannot take even root of a negative number.");
                }
                result = Math.pow(rootValue, 1.0 / rootDegree);
            } else {
                throw new NumberFormatException("Invalid root format. Use root(n,x)");
            }
        } else {
            result = Double.parseDouble(normalizedInput);
        }

        return result;
    }

    /**
     * Calculates the exponential function a * b^x.
     *
     * @param coefficientA The coefficient (a).
     * @param baseB The base (b).
     * @param exponentX The exponent (x).
     * @return The result of the exponential function.
     * @throws ArithmeticException If the base is negative and the exponent is non-integer.
     */
    public static double calculateExponentialFunction(final double coefficientA, final double baseB, final double exponentX) throws ArithmeticException {
        if (baseB < 0 && exponentX != Math.floor(exponentX)) {
            throw new ArithmeticException("Cannot raise a negative base to a non-integer power in the real number system.");
        }
        return coefficientA * power(baseB, exponentX);
    }

    /**
     * Custom method to compute power.
     *
     * @param base     The base number.
     * @param exponent The exponent to raise the base.
     * @return The calculated power.
     */
    public static double power(final double base, final double exponent) {
        double result = 1.0; // Initial result value

        if (exponent != 0) {  // Check for non-zero exponent
            final boolean isNegExponent = exponent < 0;
            final double absExponent = Math.abs(exponent);

            for (int i = 0; i < absExponent; i++) {
                result *= base;
            }

            if (isNegExponent) {
                result = 1.0 / result;  // Adjust for negative exponents
            }
        }

        return result; // Single return statement at the end
    }

    /**
     * Custom method to compute square root using Newton's method.
     *
     * @param number The number to find the square root of.
     * @return The calculated square root.
     * @throws IllegalArgumentException If the number is negative.
     */
    public static double sqrt(final double number) {
        if (number < 0) {
            throw new IllegalArgumentException("Cannot compute the square root of a negative number");
        }

        double guess = number / 2.0;
        while (Math.abs(guess * guess - number) > EPSILON) {
            guess = (guess + number / guess) / 2.0;
        }
        return guess;
    }

    /**
     * Displays an error alert with a detailed message.
     *
     * @param title   The title of the alert dialog.
     * @param message The message to display in the alert dialog.
     */
    private static void showErrorAlert(final String title, final String message) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    /**
     * Displays a detailed error alert with additional information.
     *
     * @param title          The title of the alert dialog.
     * @param message        The main message to display in the alert dialog.
     * @param detailedMessage The detailed message with additional information.
     */
    private static void showDetailedErrorAlert(final String title, final String message, final String detailedMessage) {
        final Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(message);

        final Label label = new Label("Details:");

        final TextArea textArea = new TextArea(detailedMessage);
        textArea.setEditable(false);
        textArea.setWrapText(true);

        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
        GridPane.setHgrow(textArea, Priority.ALWAYS);

        final GridPane expContent = new GridPane();
        expContent.setMaxWidth(Double.MAX_VALUE);
        expContent.add(label, 0, 0);
        expContent.add(textArea, 0, 1);

        alert.getDialogPane().setExpandableContent(expContent);
        alert.showAndWait();
    }

    /**
     * Displays a help dialog with usage instructions for the application.
     */
    private static void showHelp() {
        final Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Help");
        alert.setHeaderText("Usage Instructions");
        alert.setContentText("This application calculates the exponential function a * b^x.\n\n" +
                "You can enter values in the following formats:\n" +
                "- Real numbers (e.g., 5, -3.2, 0.25)\n" +
                "- Fractions as 'numerator/denominator' (e.g., 1/2, 3/4)\n" +
                "- Constants 'pi' and 'e' for mathematical constants π and e\n" +
                "- Roots in the format 'root(n,x)' for the n-th root of x\n" +
                "- 'sqrt(x)' for the square root of x");

        alert.showAndWait();
    }
}
