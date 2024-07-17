package guifolder;

import calculator.Calculator;
import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import validation.InputValidator;

public class PercentageCalculatorGUI extends JFrame {
    public JTextField input1, input2, result;
    public  JComboBox<String> operationSelector;
    public  JButton calculateButton;

    public PercentageCalculatorGUI() {
        setTitle("Percentage Calculator");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Input 1
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Input 1:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        input1 = new JTextField();
        add(input1, gbc);

        // Input 2
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Input 2:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        input2 = new JTextField();
        add(input2, gbc);

        // Operation
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Operation:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        String[] operations = {"Percentage", "Percentage Increase", "Percentage Decrease", "Find Whole"};
        operationSelector = new JComboBox<>(operations);
        add(operationSelector, gbc);

        // Calculate Button
        gbc.gridx = 1;
        gbc.gridy = 3;
        calculateButton = new JButton("Calculate");
        add(calculateButton, gbc);

        // Result
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Result:"), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        result = new JTextField();
        result.setEditable(false);
        add(result, gbc);

        calculateButton.addActionListener((ActionEvent e) -> {
            performCalculation();
        });
    }

    private void performCalculation() {
        String value1 = input1.getText();
        String value2 = input2.getText();
        String operation = (String) operationSelector.getSelectedItem();

        if (!InputValidator.isValidNumber(value1) || !InputValidator.isValidNumber(value2)) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers.");
            return;
        }

        double num1 = Double.parseDouble(value1);
        double num2 = Double.parseDouble(value2);
        double calculationResult = 0;

        try {
            switch (operation) {
                case "Percentage":
                    calculationResult = Calculator.calculatePercentage(num1, num2);
                    break;
                case "Percentage Increase":
                    calculationResult = Calculator.calculatePercentageIncrease(num1, num2);
                    break;
                case "Percentage Decrease":
                    calculationResult = Calculator.calculatePercentageDecrease(num1, num2);
                    break;
                case "Find Whole":
                    calculationResult = Calculator.findWhole(num1, num2);
                    break;
            }
            result.setText(String.format("%.2f", calculationResult));
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public static void main(String[] args) {
        PercentageCalculatorGUI calculatorGUI = new PercentageCalculatorGUI();
        calculatorGUI.setVisible(true);
    }
}
