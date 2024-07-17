package validation;

public class InputValidator {

    public static boolean isValidNumber(String input) {
        try {
            Double.valueOf(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
