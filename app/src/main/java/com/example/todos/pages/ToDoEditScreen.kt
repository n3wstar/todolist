package com.example.todos.pages

import android.app.DatePickerDialog
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.todos.TodoItemUI
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.UUID

@Composable
fun ToDoEditScreenWrapper(
    item: TodoItemUI?,
    onSave: (TodoItemUI) -> Unit
) {
    var text by remember { mutableStateOf(item?.text ?: "") }
    var isDone by remember { mutableStateOf(item?.isDone ?: false) }
    var importance by remember { mutableStateOf(Importance.NORMAL) }
    var deadline by remember { mutableStateOf<Long?>(null) }
    var color by remember { mutableStateOf<Color?>(null) }

    ToDoEditScreen(
        text = text,
        isDone = isDone,
        importance = importance,
        deadline = deadline,
        color = color,

        onTextChange = { text = it },
        onDoneChange = { isDone = it },
        onImportanceChange = { importance = it },
        onDeadlineChange = { deadline = it },
        onColorChange = { color = it },

        onSaveClick = {
            onSave(
                TodoItemUI(
                    uid = item?.uid ?: UUID.randomUUID().toString(),
                    text = text,
                    isDone = isDone
                )
            )
        }
    )
}
@Composable
fun ToDoEditScreen(
    text: String,
    isDone: Boolean,
    importance: Importance,
    deadline: Long?,
    color: Color?,

    onTextChange: (String) -> Unit,
    onDoneChange: (Boolean) -> Unit,
    onImportanceChange: (Importance) -> Unit,
    onDeadlineChange: (Long?) -> Unit,
    onColorChange: (Color?) -> Unit,

    onSaveClick: () -> Unit
) {


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Title()

        TodoTextField(
            text = text,
            onTextChange = onTextChange
        )

        DeadlinePicker(
            deadline = deadline,
            onDateSelected = onDeadlineChange
        )

        DoneCheckbox(
            isDone = isDone,
            onChecked = onDoneChange
        )

        ColorPicker(
            selectedColor = color,
            onColorSelected = onColorChange
        )

        ImportanceSelector(
            selected = importance,
            onSelect = onImportanceChange
        )

        Button(
            onClick = onSaveClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Сохранить")
        }
    }
}

@Composable
private fun Title() {
    Text(
        text = "Редактирование дела",
        fontSize = 22.sp,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun TodoTextField(
    text: String,
    onTextChange: (String) -> Unit
) {
    OutlinedTextField(
        value = text,
        onValueChange = onTextChange,
        label = { Text("Описание") },
        modifier = Modifier.fillMaxWidth(),
        maxLines = 5
    )
}

enum class Importance { LOW, NORMAL, HIGH }


@Composable
private fun ImportanceSelector(
    selected: Importance,
    onSelect: (Importance) -> Unit
) {
    Column {
        Text("Важность", fontWeight = FontWeight.Medium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Importance.values().forEach {
                FilterChip(
                    selected = selected == it,
                    onClick = { onSelect(it) },
                    label = {
                        Text(
                            when (it) {
                                Importance.LOW -> "Низкая"
                                Importance.NORMAL -> "Обычная"
                                Importance.HIGH -> "Высокая"
                            }
                        )
                    }
                )
            }
        }
    }
}
@Composable
private fun DeadlinePicker(
    deadline: Long?,
    onDateSelected: (Long?) -> Unit
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    if (deadline != null) calendar.timeInMillis = deadline

    val dialog = DatePickerDialog(
        context,
        { _, y, m, d ->
            calendar.set(y, m, d)
            onDateSelected(calendar.timeInMillis)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = deadline?.let {
                val date = java.text.SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
                    .format(Date(it))
                "Дедлайн: $date"
            } ?: "Выбрать дедлайн",
            fontSize = 16.sp
        )

        Button(onClick = { dialog.show() }) {
            Text("Выбрать дату")
        }
    }
}


@Composable
private fun DoneCheckbox(
    isDone: Boolean,
    onChecked: (Boolean) -> Unit
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = isDone, onCheckedChange = onChecked)
        Spacer(modifier = Modifier.width(8.dp))
        Text("Выполнено")
    }
}

@Composable
fun ColorPicker(
    selectedColor: Color?,
    onColorSelected: (Color) -> Unit
) {
    var showDialog by remember { mutableStateOf(false) }

    var colors by remember {
        mutableStateOf(
            listOf(Color.Red, Color.Green, Color.Blue, Color.Yellow)
        )
    }

    Column {
        Text("Цвет", fontWeight = FontWeight.Medium)

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            modifier = Modifier.horizontalScroll(rememberScrollState())
        ) {


            colors.forEach { color ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(color)
                        .border(
                            1.dp,
                            if (selectedColor == color) Color.Black else Color.Gray
                        )
                        .clickable { onColorSelected(color) },
                    contentAlignment = Alignment.Center
                ) {
                    if (selectedColor == color) {
                        Text("✓", fontWeight = FontWeight.Bold)
                    }
                }
            }


            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(
                        Brush.horizontalGradient(
                            listOf(
                                Color.Red,
                                Color.Yellow,
                                Color.Green,
                                Color.Cyan,
                                Color.Blue,
                                Color.Magenta
                            )
                        )
                    )
                    .border(1.dp, Color.Gray)
                    .clickable { showDialog = true }
            ) {
                if (selectedColor != null && selectedColor !in colors) {
                    Text("✓", fontWeight = FontWeight.Bold)
                }
            }
        }

        if (showDialog) {
            ColorPickerDialog(
                initialColor = selectedColor ?: Color.White,
                onDismiss = { showDialog = false },
                onColorSelected = { newColor ->


                    colors = if (newColor !in colors) {
                        listOf(newColor) + colors
                    } else {
                        colors
                    }

                    onColorSelected(newColor)

                    showDialog = false
                }
            )
        }
    }
}

@Composable
fun ColorPickerDialog(
    initialColor: Color,
    onDismiss: () -> Unit,
    onColorSelected: (Color) -> Unit
) {
    var hue by remember { mutableStateOf(0f) }
    var brightness by remember { mutableStateOf(1f) }

    Dialog(onDismissRequest = onDismiss) {
        Surface(
            shape = MaterialTheme.shapes.medium,
            tonalElevation = 8.dp,
            modifier = Modifier.padding(16.dp)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text("Выберите цвет", fontWeight = FontWeight.Medium)

                Spacer(modifier = Modifier.height(16.dp))

                // Слайдер яркости
                Slider(
                    value = brightness,
                    onValueChange = { brightness = it },
                    valueRange = 0f..1f,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .pointerInput(Unit) {
                            detectDragGestures { change, _ ->
                                val x = change.position.x
                                val width = size.width
                                hue = (x / width).coerceIn(0f, 1f)
                            }
                        }
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(
                                    Color.Red,
                                    Color.Yellow,
                                    Color.Green,
                                    Color.Cyan,
                                    Color.Blue,
                                    Color.Magenta,
                                    Color.Red
                                )
                            )
                        )
                )

                Spacer(modifier = Modifier.height(16.dp))

                Box(
                    modifier = Modifier
                        .size(50.dp)
                        .background(Color.hsv(hue * 360f, 1f, brightness))
                        .border(2.dp, Color.Black)
                )

                Spacer(modifier = Modifier.height(16.dp))

                Button(onClick = { onColorSelected(Color.hsv(hue * 360f, 1f, brightness)) }) {
                    Text("Done")
                }
            }
        }
    }
}