package com.example.plantchecker

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// ============================================
// 1. LAZY: База данных создастся только
//    при первом обращении
// ============================================
val plantDatabase by lazy {
    println("🌱 База данных растений инициализирована!")
    mapOf(
        "d" to "Дуб",
        "r" to "Ромашка",
        "p" to "Папоротник"
    )
}

// ============================================
// 2. COMPOSITION LOCAL OF: «Канал» для передачи
//    БД по всему дереву UI без параметров
// ============================================
val LocalPlantDatabase = staticCompositionLocalOf<Map<String, String>> {
    error("PlantDatabase не предоставлен!")
}

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppRoot()
        }
    }
}

// ============================================
// 3. COMPOSITION LOCAL PROVIDER: Заполняем
//    канал данными для всех дочерних компонентов
// ============================================
@Composable
fun AppRoot() {
    CompositionLocalProvider(LocalPlantDatabase provides plantDatabase) {
        PlantCheckerScreen()
    }
}

@Composable
fun PlantCheckerScreen() {

    // 4. REMEMBER SAVEABLE: Ввод переживёт поворот экрана
    var inputLetter by rememberSaveable { mutableStateOf("") }

    // 5. REMEMBER: Результат переживёт рекомпозицию
    var resultMessage by remember { mutableStateOf("Введите букву растения") }

    // 6. Чтение из CompositionLocal через .current
    val db = LocalPlantDatabase.current

    Column(modifier = Modifier.padding(16.dp)) {

        TextField(
            value = inputLetter,
            onValueChange = { newText ->
                inputLetter = newText

                // 7. WHEN: Определяем растение по букве
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

        // 8. ВЛОЖЕННЫЙ КОМПОНЕНТ:
        //    PlantResultDisplay НЕ получает db как параметр!
        //    Он сам читает из LocalPlantDatabase.current
        //    Это и есть магия CompositionLocal.
        PlantResultDisplay(message = resultMessage)
    }
}

// ============================================
// Глубоко вложенный компонент.
// Обратите внимание: в сигнатуре НЕТ параметра db.
// Компонент сам достаёт БД из CompositionLocal.
// ============================================
@Composable
fun PlantResultDisplay(message: String) {

    // Читаем БД из того же канала, хотя этот компонент
    // находится на 2 уровня глубже AppRoot
    val db = LocalPlantDatabase.current

    Column(
        modifier = Modifier
            .padding(top = 16.dp)
            .background(Color(0xFFE8F5E9))
            .padding(12.dp)
    ) {
        Text(
            text = "📋 Результат:",
            fontSize = 14.sp
        )
        Text(
            text = message,
            fontSize = 20.sp
        )
        Text(
            text = "(Доступно растений в БД: ${db.size})",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}