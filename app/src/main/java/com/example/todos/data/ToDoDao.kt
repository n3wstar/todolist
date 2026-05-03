package com.example.todos.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ToDoDao {

    @Query("SELECT * FROM todo")
    fun observeAll(): Flow<List<ToDoEntity>>

    @Query("SELECT * FROM todo WHERE uid = :id")
    suspend fun getById(id: String): ToDoEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: ToDoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<ToDoEntity>)

    @Delete
    suspend fun delete(item: ToDoEntity)

    @Query("DELETE FROM todo WHERE uid = :id")
    suspend fun deleteById(id: String)

    @Query("DELETE FROM todo")
    suspend fun clear()
}