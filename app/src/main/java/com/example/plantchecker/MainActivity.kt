package com.example.plantchecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

// ============================================
// LAZY: База данных создастся только при
// первом обращении к переменной plantDatabase
// ============================================
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
    // Буква захардкожена для демонстрации lazy + when
    val inputLetter = "d"

    // WHEN: Определяем растение по букве
    val resultMessage = when (inputLetter.lowercase()) {
        "d" -> "это ${plantDatabase["d"]}"
        "r" -> "это ${plantDatabase["r"]}"
        "p" -> "это ${plantDatabase["p"]}"
        else -> "Растение не найдено"
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(text = "Введённая буква: $inputLetter")
        Text(text = resultMessage)
    }
}