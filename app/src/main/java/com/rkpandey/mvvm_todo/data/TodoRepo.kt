package com.rkpandey.mvvm_todo.data

import kotlinx.coroutines.flow.Flow

interface TodoRepo {

    suspend fun insertTodo(todo:TodoList)

    suspend fun deleteTodo(todo:TodoList)

    suspend fun getTodoById(id:Int?):TodoList?

    fun getTodos():Flow<List<TodoList>>
}