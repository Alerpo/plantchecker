package com.example.plantchecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

val plantDatabase by lazy {
    println("🌱 База данных растений инициализирована!")
    mapOf(
        "d" to "Дуб",
        "D" to "Дуб",
        "r" to "Ромашка",
        "p" to "Папоротник"
    )
}

val LocalPlantDatabase = staticCompositionLocalOf<Map<String, String>> {
    error("PlantDatabase не предоставлен! Оберните UI в CompositionLocalProvider.")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppRoot()
        }
    }
}

@Composable
fun AppRoot() {
    CompositionLocalProvider(LocalPlantDatabase provides plantDatabase) {
        PlantCheckerScreen()
    }
}

@Composable
fun PlantCheckerScreen() {
    var inputLetter by rememberSaveable { mutableStateOf("") }
    var resultMessage by remember { mutableStateOf("Введите букву растения") }


    val db = plantDatabase

    Column(modifier = Modifier.padding(16.dp)) {

        TextField(
            value = inputLetter,
            onValueChange = { newText ->
                inputLetter = newText


                resultMessage = when (newText.lowercase()) {
                    "d" -> "это ${db["d"]}"
                    "r" -> "это ${db["r"]}"
                    "p" -> "это ${db["p"]}"
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