package com.rkpandey.mvvm_todo.ui.screens

import com.rkpandey.mvvm_todo.data.TodoList

sealed class ToDoListEvent{
    data class OnDeleteTodoClicked(val todo:TodoList):ToDoListEvent()
    data class OnDoneChange(val todo:TodoList,val isDone : Boolean): ToDoListEvent()
    object OnUndoDeleteClick : ToDoListEvent()
    data class OnTodoClick(val todo:TodoList): ToDoListEvent()
    object OnAddTodoClicked : ToDoListEvent()
}
