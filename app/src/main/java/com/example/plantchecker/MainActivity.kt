package com.example.plantchecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val plantDatabase by lazy {
    println("🌱 База данных растений инициализирована!")
    mapOf(
        "d" to "Дуб",
        "r" to "Ромашка",
        "p" to "Папоротник"
    )
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PlantCheckerScreen()
        }
    }
}

@Composable
fun PlantCheckerScreen() {

    var inputLetter by remember { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("Введите букву растения") }

    Column(modifier = Modifier.padding(16.dp)) {

        TextField(
            value = inputLetter,
            onValueChange = { newText ->
                inputLetter = newText

                resultMessage = when (newText.lowercase()) {
                    "d" -> "это ${plantDatabase["d"]}"
                    "r" -> "это ${plantDatabase["r"]}"
                    "p" -> "это ${plantDatabase["p"]}"
                    ""  -> "Введите букву растения"
                    else -> "Растение не найдено"
                }
            },
            label = { Text("Введите букву") },
            singleLine = true
        )

        Text(
            text = resultMessage,
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}