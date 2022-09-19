package com.rkpandey.mvvm_todo.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTodo(todo:TodoList)

    @Delete
    suspend fun deleteTodo(todo:TodoList)

    @Query("SELECT * FROM Todo WHERE id = :id")
    suspend fun getTodoById(id:Int?):TodoList?

    @Query("SELECT * FROM Todo")
    fun getTodos(): Flow<List<TodoList>>
}