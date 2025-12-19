package com.example.todos.storage

import android.content.Context
import com.example.todos.model.ToDoItem
import com.example.todos.model.json
import com.example.todos.model.parseTodoItem
import org.json.JSONArray
import org.slf4j.LoggerFactory
import java.io.File

class FileStorage(private val context: Context, private val fileName: String = "todos.json") {

    private val logger = LoggerFactory.getLogger(FileStorage::class.java)

    private val _items = mutableListOf<ToDoItem>()

    val items: List<ToDoItem>
        get() = _items.toList()

    /**
     * Добавление нового дела
     */
    fun add(item: ToDoItem) {
        logger.debug("Добавление дела: ${item.uid}")
        _items.add(item)
        saveToFile()
    }

    /**
     * Удаление дела по uid
     */
    fun remove(uid: String) {
        logger.debug("Удаление дела: $uid")
        _items.removeAll { it.uid == uid }
        saveToFile()
    }

    /**
     * Сохраняет все дела в файл
     */
    private fun saveToFile() {
        try {
            logger.debug("Сохранение ${_items.size} дел в файл")

            val jsonArray = JSONArray()
            _items.forEach { jsonArray.put(it.json) }

            val file = File(context.filesDir, fileName)
            file.writeText(jsonArray.toString())

            logger.info("Файл успешно сохранён")
        } catch (e: Exception) {
            logger.error("Ошибка при сохранении файла", e)
        }
    }

    /**
     * Загружает все дела из файла
     */
    fun loadFromFile() {
        try {
            val file = File(context.filesDir, fileName)

            if (!file.exists()) {
                logger.info("Файл не найден, загрузка пропущена")
                return
            }

            logger.debug("Загрузка дел из файла")

            val content = file.readText()
            val jsonArray = JSONArray(content)

            _items.clear()

            for (i in 0 until jsonArray.length()) {
                val obj = jsonArray.getJSONObject(i)
                parseTodoItem(obj)?.let { _items.add(it) }
            }

            logger.info("Загружено дел: ${_items.size}")
        } catch (e: Exception) {
            logger.error("Ошибка при загрузке файла", e)
        }
    }
}