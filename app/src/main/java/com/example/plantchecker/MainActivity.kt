package com.example.plantchecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimplePlantScreen()
        }
    }
}

@Composable
fun SimplePlantScreen() {
    var inputLetter by remember { mutableStateOf("") }
    var resultText by remember { mutableStateOf("Введите букву d") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {

            TextField(
                value = inputLetter,
                onValueChange = { newText ->
                    inputLetter = newText

                    resultText = when (newText.lowercase()) {
                        "d" -> "это Дуб"
                        ""  -> "Введите букву d"
                        else -> "Вы ввели '${newText}'. Это не Дуб."
                    }
                },
                label = { Text("Введите букву") },
                singleLine = true
            )

            Text(
                text = resultText,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}