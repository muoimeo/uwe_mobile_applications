package com.example.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tipcalculator.ui.theme.TipCalculatorTheme
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.NumberFormat

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    TipCalApp(
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun TipCalApp(modifier: Modifier = Modifier) {
    var amountInput by remember { mutableStateOf("") }

    val amount = amountInput.toDoubleOrNull() ?: 0.0

    val tip = calculateTip(amount, 15.0)
    val tipFormatted = NumberFormat.getCurrencyInstance().format(tip)

    Column(
        modifier = Modifier.fillMaxSize().padding(start=40.dp, top = 150.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Top
    ) {
        Text( text = stringResource(R.string.calculate_tip))

        Spacer(modifier = Modifier.height(20.dp))

        EditNumberField(value=amountInput, onValueChanged = {amountInput = it})

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = stringResource(R.string.tip_amount, tipFormatted),
            style = MaterialTheme.typography.titleLarge
        )

    }

}

@Composable
fun EditNumberField(value:String, onValueChanged:(String)->Unit) {
    TextField(
        value = value,
        onValueChange = onValueChanged,
        singleLine = true,
        label = { Text(stringResource(R.string.bill_amount)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),

    )
}

fun calculateTip(amount: Double, tipPercent: Double): Double {
    val raw = amount * tipPercent / 100.0
    return BigDecimal(raw).setScale(2, RoundingMode.HALF_UP).toDouble()
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TipCalAppPreview() {
    TipCalculatorTheme {
        TipCalApp()
    }
}