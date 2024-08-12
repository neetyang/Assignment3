package com.example.assignment3

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var firstNumberEditText: EditText
    private lateinit var secondNumberEditText: EditText
    private lateinit var operatorSpinner: Spinner
    private lateinit var resultTextView: TextView
    private val historyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        firstNumberEditText = findViewById(R.id.first_number_edit_text)
        secondNumberEditText = findViewById(R.id.second_number_edit_text)
        operatorSpinner = findViewById(R.id.operator_spinner)
        resultTextView = findViewById(R.id.result_text_view)

        ArrayAdapter.createFromResource(
            this,
            R.array.operators_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            operatorSpinner.adapter = adapter
        }

        val calculateButton: Button = findViewById(R.id.calculate_button)
        calculateButton.setOnClickListener {
            performCalculation()
        }

        val historyButton: Button = findViewById(R.id.history_button)
        historyButton.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            intent.putStringArrayListExtra("HISTORY_LIST", ArrayList(historyList))
            startActivity(intent)
        }
    }

    private fun performCalculation() {
        val firstNumber = firstNumberEditText.text.toString().toDoubleOrNull()
        val secondNumber = secondNumberEditText.text.toString().toDoubleOrNull()
        val operator = operatorSpinner.selectedItem.toString()

        if (firstNumber == null || secondNumber == null) {
            Toast.makeText(this, "Please enter valid numbers", Toast.LENGTH_SHORT).show()
            return
        }

        val result = when (operator) {
            "+" -> firstNumber + secondNumber
            "-" -> firstNumber - secondNumber
            "*" -> firstNumber * secondNumber
            "/" -> {
                if (secondNumber != 0.0) {
                    firstNumber / secondNumber
                } else {
                    Toast.makeText(this, "Cannot divide by zero", Toast.LENGTH_SHORT).show()
                    return
                }
            }
            else -> 0.0
        }

        resultTextView.text = result.toString()
        saveToHistory("$firstNumber $operator $secondNumber = $result")
        showResultDialog("$firstNumber $operator $secondNumber = $result")
    }

    private fun saveToHistory(expression: String) {
        historyList.add(expression)
    }

    private fun showResultDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Calculation Result")
            .setMessage("Result: $message")
            .setPositiveButton("OK") { dialog, _ -> dialog.dismiss() }
            .setNeutralButton("View History") { dialog, _ ->
                dialog.dismiss()
                val intent = Intent(this, HistoryActivity::class.java)
                intent.putStringArrayListExtra("HISTORY_LIST", ArrayList(historyList))
                startActivity(intent)
            }
            .show()
    }
}

