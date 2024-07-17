package calculator;

public class Calculator {

    // Method to calculate percentage
    public static double calculatePercentage(double part, double whole) {
        if (whole == 0) {
            throw new IllegalArgumentException("The whole value cannot be zero.");
        }
        return (part / whole) * 100;
    }

    // Method to calculate percentage increase
    public static double calculatePercentageIncrease(double original, double newAmount) {
        if (original == 0) {
            throw new IllegalArgumentException("The original value cannot be zero.");
        }
        return ((newAmount - original) / original) * 100;
    }

    // Method to calculate percentage decrease
    public static double calculatePercentageDecrease(double original, double newAmount) {
        if (original == 0) {
            throw new IllegalArgumentException("The original value cannot be zero.");
        }
        return ((original - newAmount) / original) * 100;
    }

    // Method to find the whole given a part and percentage
    public static double findWhole(double part, double percentage) {
        if (percentage == 0) {
            throw new IllegalArgumentException("The percentage value cannot be zero.");
        }
        return (part * 100) / percentage;
    }
}
