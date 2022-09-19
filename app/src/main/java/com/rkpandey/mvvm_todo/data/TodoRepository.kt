package com.rkpandey.mvvm_todo.data

import kotlinx.coroutines.flow.Flow

class TodoRepository(
        private val dao : TodoDao
) : TodoRepo{
    override suspend fun insertTodo(todo:TodoList) {
        dao.insertTodo(todo)
    }

    override suspend fun deleteTodo(todo:TodoList) {
        dao.deleteTodo(todo)
    }

    override suspend fun getTodoById(id:Int?):TodoList? {
        return dao.getTodoById(id)
    }

    override fun getTodos():Flow<List<TodoList>> {
        return dao.getTodos()
    }


}