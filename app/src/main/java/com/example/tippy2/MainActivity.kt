package com.example.tippy2

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var editTextBillAmount: EditText
    private lateinit var seekBarTipPercentage: SeekBar
    private lateinit var textViewTipPercentage: TextView
    private lateinit var spinnerCurrency: Spinner
    private lateinit var btnCalculate: Button
    private lateinit var textViewTipResult: TextView
    private lateinit var textViewTotalAmount: TextView
    private lateinit var textViewMadeBy: TextView

    @SuppressLint("StringFormatMatches")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextBillAmount = findViewById(R.id.editTextBillAmount)
        seekBarTipPercentage = findViewById(R.id.seekBarTipPercentage)
        textViewTipPercentage = findViewById(R.id.textViewTipPercentage)
        spinnerCurrency = findViewById(R.id.spinnerCurrency)
        btnCalculate = findViewById(R.id.btnCalculate)
        textViewTipResult = findViewById(R.id.textViewTipResult)
        textViewTotalAmount = findViewById(R.id.textViewTotalAmount)
        textViewMadeBy = findViewById(R.id.textViewMadeBy)

        seekBarTipPercentage.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            @SuppressLint("SetTextI18n")
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                textViewTipPercentage.text = "Tip Percentage: $progress%"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnCalculate.setOnClickListener {
            val billAmount = editTextBillAmount.text.toString().toDoubleOrNull()
            val tipPercentage = seekBarTipPercentage.progress
            val selectedCurrency = spinnerCurrency.selectedItem as String

            if (billAmount != null) {
                val tipAmount = calculateTip(billAmount, tipPercentage)
                val totalAmount = billAmount + tipAmount

                val formattedTip = getString(R.string.tip_result, tipAmount, selectedCurrency)
                val formattedTotal = getString(R.string.total_amount, totalAmount, selectedCurrency)

                textViewTipResult.text = formattedTip
                textViewTotalAmount.text = formattedTotal
            } else {
                textViewTipResult.text = getString(R.string.invalid_amount)
                textViewTotalAmount.text = getString(R.string.total_amount, 0.0, selectedCurrency)
            }
        }
    }

    private fun calculateTip(billAmount: Double, tipPercentage: Int): Double {
        val tipFraction = tipPercentage / 100.0
        return billAmount * tipFraction
    }
}
