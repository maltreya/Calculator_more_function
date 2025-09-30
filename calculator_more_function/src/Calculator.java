import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
    int boardWidth = 360;
    int boardHeight = 540;
    Color customLightGray = new Color(212, 212, 210);
    Color customDarkGray = new Color(80, 80, 80);
    Color customBlack = new Color(28, 28, 28);
    Color customSalad = new Color(133, 198, 35);

    String[] buttonsValues = {
            "AC", "+/-", "%", "÷",
            "7", "8", "9", "×",
            "4", "5", "6", "-",
            "1", "2", "3", "+",
            "0", ".", "√", "="
    };
    String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};


    JFrame frame = new JFrame("Calculator");
    JLabel displayLabel = new JLabel();
    JPanel displayPanel = new JPanel();
    JPanel buttonsPanel = new JPanel();

    String A = "0";
    String operator = null;
    String B = null;

    Calculator() {

        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        displayLabel.setBackground(customBlack);
        displayLabel.setForeground(Color.WHITE);
        displayLabel.setFont(new Font("Arial", Font.PLAIN, 60));
        displayLabel.setHorizontalAlignment(JLabel.RIGHT);
        displayLabel.setText(A);
        displayLabel.setPreferredSize(new Dimension(300, 150));
        displayLabel.setOpaque(true);

        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(displayLabel);
        frame.add(displayPanel, BorderLayout.NORTH);

        buttonsPanel.setLayout(new GridLayout(5, 4));
        buttonsPanel.setBackground(customBlack);
        frame.add(buttonsPanel);

        for (int i = 0; i < buttonsValues.length; i++) {
            JButton button = new JButton();
            String buttonValue = buttonsValues[i];
            button.setFont(new Font("Arial", Font.PLAIN, 30));
            button.setText(buttonValue);
            button.setFocusable(false);
            button.setBorder(new LineBorder(customBlack));
            if (Arrays.asList(topSymbols).contains(buttonValue)) {
                button.setBackground(customLightGray);
                button.setForeground(customBlack);
            } else if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                button.setBackground(customSalad);
                button.setForeground(Color.WHITE);
            } else {
                button.setBackground(customDarkGray);
                button.setForeground(Color.WHITE);
            }
            buttonsPanel.add(button);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();
                    String buttonValue = button.getText();

                    if (Arrays.asList(rightSymbols).contains(buttonValue)) {
                        if (buttonValue == "=") {
                            if (A != null) {
                                B = displayLabel.getText();
                                double numA = Double.parseDouble(A);
                                double numB = Double.parseDouble(B);

                                if (operator == "+") {
                                    displayLabel.setText(removeZeroDecimal(numA + numB));
                                } else if (operator == "-") {
                                    displayLabel.setText(removeZeroDecimal(numA - numB));
                                } else if (operator == "×") {
                                    displayLabel.setText(removeZeroDecimal(numA * numB));
                                } else if (operator == "÷") {
                                    displayLabel.setText(removeZeroDecimal(numA / numB));
                                }
                            }
                        } else if ("+-×÷".contains(buttonValue)) {
                            if (operator == null) {
                                A = displayLabel.getText();
                                displayLabel.setText("0");
                                B = "0";
                            }
                            operator = buttonValue;

                        }

                    } else if (Arrays.asList(topSymbols).contains(buttonValue)) {
                        if (buttonValue == "AC") {
                            clearAll();
                            displayLabel.setText("0");
                        } else if (buttonValue == "+/-") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay *= -1;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        } else if (buttonValue == "%") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            numDisplay /= 100;
                            displayLabel.setText(removeZeroDecimal(numDisplay));
                        }
                    } else { //digit or .
                        if (buttonValue == ".") {
                            if (!displayLabel.getText().contains(buttonValue)) {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        } else if ("0123456789".contains(buttonValue)) {
                            if (displayLabel.getText() == "0") {
                                displayLabel.setText(buttonValue); //not "05" -> "5"
                            } else {
                                displayLabel.setText(displayLabel.getText() + buttonValue);
                            }
                        } else if (buttonValue == "√") {
                            double numDisplay = Double.parseDouble(displayLabel.getText());
                            if (numDisplay < 0) {
                                displayLabel.setText("Incorrect input");
                            } else {
                                numDisplay = Math.sqrt(numDisplay);
                                displayLabel.setText(removeZeroDecimal(numDisplay));
                            }
                        }
                    }
                }
            });
            frame.setVisible(true);

        }
    }

    void clearAll() {
        A = "0";
        operator = null;
        B = null;
    }

    String removeZeroDecimal(double numDisplay) {
        if (numDisplay % 1 == 0) {
            return Integer.toString((int) numDisplay);
        }
        return Double.toString(numDisplay);
    }
}
