package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {
    private TextView displayResult;
    private TextView displayOperation;
    private String currentNumber = "";
    private String currentOperation = "";
    private double firstNumber = 0;
    private double secondNumber = 0;
    private boolean isOperationClicked = false;
    private boolean isEqualClicked = false;
    private DecimalFormat decimalFormat = new DecimalFormat("#.######");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize TextViews
        displayResult = findViewById(R.id.display_result);
        displayOperation = findViewById(R.id.display_operation);

        // Initialize number buttons
        setupNumberButton(R.id.button_0, "0");
        setupNumberButton(R.id.button_1, "1");
        setupNumberButton(R.id.button_2, "2");
        setupNumberButton(R.id.button_3, "3");
        setupNumberButton(R.id.button_4, "4");
        setupNumberButton(R.id.button_5, "5");
        setupNumberButton(R.id.button_6, "6");
        setupNumberButton(R.id.button_7, "7");
        setupNumberButton(R.id.button_8, "8");
        setupNumberButton(R.id.button_9, "9");
        setupNumberButton(R.id.button_comma, ".");

        // Initialize operation buttons
        setupOperationButton(R.id.button_plus, "+");
        setupOperationButton(R.id.button_minus, "-");
        setupOperationButton(R.id.button_multiply, "×");
        setupOperationButton(R.id.button_divide, "÷");

        // AC button
        Button clearButton = findViewById(R.id.button_ac);
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });

        // Equal button
        Button equalButton = findViewById(R.id.button_equals);
        equalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentNumber.isEmpty()) {
                    secondNumber = Double.parseDouble(currentNumber);
                    calculateResult();
                    isEqualClicked = true;
                }
            }
        });

        // Delete button
        Button deleteButton = findViewById(R.id.button_delete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentNumber.isEmpty()) {
                    currentNumber = currentNumber.substring(0, currentNumber.length() - 1);
                    if (currentNumber.isEmpty()) {
                        currentNumber = "0";
                    }
                    displayResult.setText(currentNumber);
                }
            }
        });

        // Percentage button
        Button percentButton = findViewById(R.id.button_percent);
        percentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentNumber.isEmpty()) {
                    double number = Double.parseDouble(currentNumber);
                    number = number / 100;
                    currentNumber = decimalFormat.format(number);
                    displayResult.setText(currentNumber);
                }
            }
        });

        // Toggle sign button
        Button toggleSignButton = findViewById(R.id.button_toggle_sign);
        toggleSignButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentNumber.isEmpty() && !currentNumber.equals("0")) {
                    if (currentNumber.charAt(0) == '-') {
                        currentNumber = currentNumber.substring(1);
                    } else {
                        currentNumber = "-" + currentNumber;
                    }
                    displayResult.setText(currentNumber);
                }
            }
        });
    }

    private void setupNumberButton(int buttonId, final String number) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isEqualClicked) {
                    clear();
                    isEqualClicked = false;
                }

                if (isOperationClicked) {
                    currentNumber = "";
                    isOperationClicked = false;
                }

                // Don't allow multiple decimal points
                if (number.equals(".") && currentNumber.contains(".")) {
                    return;
                }

                // Handle initial zero or decimal point
                if (currentNumber.equals("0") && !number.equals(".")) {
                    currentNumber = number;
                } else {
                    currentNumber += number;
                }

                displayResult.setText(currentNumber);
            }
        });
    }

    private void setupOperationButton(int buttonId, final String operation) {
        Button button = findViewById(buttonId);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentNumber.isEmpty()) {
                    if (!isOperationClicked) {
                        if (currentOperation.isEmpty()) {
                            firstNumber = Double.parseDouble(currentNumber);
                        } else {
                            secondNumber = Double.parseDouble(currentNumber);
                            calculateResult();
                            firstNumber = Double.parseDouble(currentNumber);
                        }
                    }

                    currentOperation = operation;
                    String displayText = formatNumber(firstNumber) + " " + operation;
                    displayOperation.setText(displayText);
                    isOperationClicked = true;
                    isEqualClicked = false;
                }
            }
        });
    }

    private void calculateResult() {
        double result = 0;

        switch (currentOperation) {
            case "+":
                result = firstNumber + secondNumber;
                break;
            case "-":
                result = firstNumber - secondNumber;
                break;
            case "×":
                result = firstNumber * secondNumber;
                break;
            case "÷":
                if (secondNumber != 0) {
                    result = firstNumber / secondNumber;
                } else {
                    displayResult.setText("Error");
                    return;
                }
                break;
        }

        currentNumber = formatNumber(result);
        displayResult.setText(currentNumber);

        // Update calculation display
        String displayText = formatNumber(firstNumber) + " " + currentOperation + " " +
                formatNumber(secondNumber) + " = " + currentNumber;
        displayOperation.setText(displayText);
    }

    private String formatNumber(double number) {
        if (number == (long) number) {
            return String.format("%d", (long) number);
        } else {
            return decimalFormat.format(number);
        }
    }

    private void clear() {
        currentNumber = "0";
        currentOperation = "";
        firstNumber = 0;
        secondNumber = 0;
        isOperationClicked = false;
        displayResult.setText("0");
        displayOperation.setText("");
    }
}