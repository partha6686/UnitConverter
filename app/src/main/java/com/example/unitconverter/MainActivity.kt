package com.example.unitconverter

import android.graphics.drawable.Icon
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.unitconverter.ui.theme.UnitConverterTheme
import kotlin.math.roundToInt
import kotlin.time.times

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            UnitConverterTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    UnitConverter()
                }
            }
        }
    }
}

@Composable
fun UnitConverter(){
    var inputValue by remember {
        mutableStateOf("")
    }
    var inputUnit by remember {
        mutableStateOf("Centimeters")
    }
    var outputValue by remember {
        mutableStateOf("")
    }
    var outputUnit by remember {
        mutableStateOf("Meters")
    }
    var inputDropdown by remember {
        mutableStateOf(false)
    }
    var outputDropdown by remember {
        mutableStateOf(false)
    }
    val conversionFactor = remember {
        mutableStateOf(0.01)
    }
    val outputFactor = remember {
        mutableStateOf(1.0)
    }

    val customTextStyles = TextStyle(
        fontFamily = FontFamily.Default,
        fontSize = 32.sp,
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Bold
    )

    fun convertUnit(){
        val inputValueDouble = inputValue.toDoubleOrNull() ?: 0.0
        val result = (inputValueDouble * conversionFactor.value * 100.0/outputFactor.value).roundToInt() / 100.0
        outputValue = result.toString()
    }

    fun setInputUnit(unit: String){
        inputUnit = unit
        inputDropdown = false
        conversionFactor.value = when (unit) {
            "Centimeters" -> 0.01
            "Meter" -> 1.0
            "Feet" -> 0.3048
            else -> 0.001
        }
        convertUnit()
    }
    fun setOutputUnit(unit: String){
        outputUnit = unit
        outputDropdown = false
        outputFactor.value = when (unit) {
            "Centimeters" -> 0.01
            "Meter" -> 1.0
            "Feet" -> 0.3048
            else -> 0.001
        }
        convertUnit()
    }




    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("UNIT CONVERTER",style = customTextStyles)
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                convertUnit()
            },
            label = { Text("Enter Value") }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row {
            Box{
                Button(onClick = { inputDropdown = true }) {
                    Text(text = inputUnit)
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Open Dropdown"
                    )
                }
                DropdownMenu(expanded = inputDropdown , onDismissRequest = { inputDropdown = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = { setInputUnit("Centimeters") }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Meter") },
                        onClick = { setInputUnit("Meter") }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Feet") },
                        onClick = { setInputUnit("Feet") }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Millimeters") },
                        onClick = { setInputUnit("Millimeters") }
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Box{
                Button(onClick = { outputDropdown = true }) {
                    Text(text = outputUnit)
                    Icon(
                        Icons.Default.ArrowDropDown,
                        contentDescription = "Open Dropdown"
                    )
                }
                DropdownMenu(expanded = outputDropdown, onDismissRequest = { outputDropdown = false }) {
                    DropdownMenuItem(
                        text = { Text(text = "Centimeters") },
                        onClick = { setOutputUnit("Centimeters") }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Meter") },
                        onClick = { setOutputUnit("Meter") }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Feet") },
                        onClick = { setOutputUnit("Feet") }
                    )
                    DropdownMenuItem(
                        text = { Text(text = "Millimeters") },
                        onClick = { setOutputUnit("Millimeters") }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Result: $outputValue $outputUnit",
            style = MaterialTheme.typography.headlineMedium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun UnitConverterPreview(){
    UnitConverter()
}
