package com.mityushov.calculator

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.mityushov.calculator.databinding.ActivityMainBinding

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // create Model
        val calculator: AbstractCalculator<Double> = DoubleCalculator()

        binding.button1.setOnClickListener {
            if (!checkIfFirstIsZero(binding.editText)) {
                binding.editText.setText(binding.editText.text.toString() + "1")
            } else {
                binding.editText.setText("1")
            }

            Log.d(TAG, "1 is pushed")
        }

        binding.button2.setOnClickListener {
            if (!checkIfFirstIsZero(binding.editText)) {
                binding.editText.setText(binding.editText.text.toString() + "2")
            } else {
                binding.editText.setText("2")
            }

            Log.d(TAG, "2 is pushed")
        }

        binding.button3.setOnClickListener {
            if (!checkIfFirstIsZero(binding.editText)) {
                binding.editText.setText(binding.editText.text.toString() + "3")
            } else {
                binding.editText.setText("3")
            }

            Log.d(TAG, "3 is pushed")
        }

        binding.button4.setOnClickListener {
            if (!checkIfFirstIsZero(binding.editText)) {
                binding.editText.setText(binding.editText.text.toString() + "4")
            } else {
                binding.editText.setText("4")
            }

            Log.d(TAG, "4 is pushed")
        }

        binding.button5.setOnClickListener {
            if (!checkIfFirstIsZero(binding.editText)) {
                binding.editText.setText(binding.editText.text.toString() + "5")
            } else {
                binding.editText.setText("5")
            }

            Log.d(TAG, "5 is pushed")
        }

        binding.button6.setOnClickListener {
            if (!checkIfFirstIsZero(binding.editText)) {
                binding.editText.setText(binding.editText.text.toString() + "6")
            } else {
                binding.editText.setText("6")
            }

            Log.d(TAG, "6 is pushed")
        }

        binding.button7.setOnClickListener {
            if (!checkIfFirstIsZero(binding.editText)) {
                binding.editText.setText(binding.editText.text.toString() + "7")
            } else {
                binding.editText.setText("7")
            }

            Log.d(TAG, "7 is pushed")
        }

        binding.button8.setOnClickListener {
            if (!checkIfFirstIsZero(binding.editText)) {
                binding.editText.setText(binding.editText.text.toString() + "8")
            } else {
                binding.editText.setText("8")
            }

            Log.d(TAG, "8 is pushed")
        }

        binding.button9.setOnClickListener {
            if (!checkIfFirstIsZero(binding.editText)) {
                binding.editText.setText(binding.editText.text.toString() + "9")
            } else {
                binding.editText.setText("9")
            }

            Log.d(TAG, "9 is pushed")
        }

        binding.button0.setOnClickListener {
            if (!this.checkIfFirstIsZero(binding.editText)) {
                Log.d(TAG, "0 is pushed")
                binding.editText.setText(binding.editText.text.toString() + "0")
            }
        }

        binding.dotButton.setOnClickListener {
            if (binding.editText.text.isEmpty()) {
                binding.editText.setText("0")
            }

            if (!checkIfContainsPoint(binding.editText)) {
                Log.d(TAG, "Dot is pushed")
                binding.editText.setText(binding.editText.text.toString() + ".")
            }
        }

        binding.clearButton.setOnClickListener {
            if (binding.editText.text.isNotEmpty()) {
                binding.editText.setText("")
                binding.editText.hint = "0"
            }
        }

        binding.addButton.setOnClickListener {
            if (binding.editText.text.isNotEmpty()) {
                calculator.a = binding.editText.text.toString().toDouble()
                binding.editText.setText("")

                if (checkIfDoubleIsWholeNumber(calculator.a)) {
                    binding.editText.hint = calculator.a.toString().substring(0, calculator.a.toString().indexOf("."))
                } else {
                    binding.editText.hint = calculator.a.toString()
                }

                calculator.currentOperation = CalculatorAPI.Operations.ADD
                Log.d(TAG, "\"+\" is pushed")
            }
        }

        binding.subtractButton.setOnClickListener {
            if (binding.editText.text.isNotEmpty()) {
                calculator.a = binding.editText.text.toString().toDouble()
                binding.editText.setText("")

                if (checkIfDoubleIsWholeNumber(calculator.a)) {
                    binding.editText.hint = calculator.a.toString().substring(0, calculator.a.toString().indexOf("."))
                } else {
                    binding.editText.hint = calculator.a.toString()
                }

                calculator.currentOperation = CalculatorAPI.Operations.SUB
                Log.d(TAG, "\"-\" is pushed")
            } else {
                binding.editText.setText("-")
            }
        }

        binding.multiplyButton.setOnClickListener {
            if (binding.editText.text.isNotEmpty()) {
                calculator.a = binding.editText.text.toString().toDouble()
                binding.editText.setText("")

                if (checkIfDoubleIsWholeNumber(calculator.a)) {
                    binding.editText.hint = calculator.a.toString().substring(0, calculator.a.toString().indexOf("."))
                } else {
                    binding.editText.hint = calculator.a.toString()
                }

                calculator.currentOperation = CalculatorAPI.Operations.MULT
                Log.d(TAG, "\"*\" is pushed")
            }
        }

        binding.divideButton.setOnClickListener {
            if (binding.editText.text.isNotEmpty()) {
                calculator.a = binding.editText.text.toString().toDouble()
                binding.editText.setText("")

                if (checkIfDoubleIsWholeNumber(calculator.a)) {
                    binding.editText.hint = calculator.a.toString().substring(0, calculator.a.toString().indexOf("."))
                } else {
                    binding.editText.hint = calculator.a.toString()
                }

                calculator.currentOperation = CalculatorAPI.Operations.DIV
                Log.d(TAG, "\"/\" is pushed")
            }
        }

        binding.equalButton.setOnClickListener {
            if (binding.editText.text.isNotEmpty()) {
                calculator.b = binding.editText.text.toString().toDouble()
                val result = calculator.calculate()
                // check if Double is whole number
                /*
                if ((result % 1) != 0.0) {
                    Log.d(TAG, "float result")
                    editText.setText(calculator.calculate().toString())
                } else {
                    Log.d(TAG, "ParseInt result")
                    editText.setText(result.toString().substring(0, result.toString().indexOf(".")))
                }

                 */

                if (checkIfDoubleIsWholeNumber(result)) {
                    Log.d(TAG, "Int result")
                    binding.editText.setText(result.toString().substring(0, result.toString().indexOf(".")))
                } else {
                    Log.d(TAG, "float result")
                    binding.editText.setText(calculator.calculate().toString())
                }
            }
        }
    }

    /**
     * private method checks if first number in value of EditText View is 0
     */
    private fun checkIfFirstIsZero(view: EditText): Boolean {
        val text = view.text.toString()
        return text.isNotEmpty() && text[0].equals('0', true) && text.length == 1
    }

    /**
     * private method checks if value of EditText View already contains dot
     */
    private fun checkIfContainsPoint(view: EditText): Boolean {
        val text = view.text.toString()
        return text.contains(".")
    }

    /**
     *
     */
    private fun checkIfDoubleIsWholeNumber(value: Double): Boolean {
        return value % 1 == 0.0
    }
}