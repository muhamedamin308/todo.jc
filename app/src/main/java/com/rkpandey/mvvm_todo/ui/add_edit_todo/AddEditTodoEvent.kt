package com.rkpandey.mvvm_todo.ui.add_edit_todo

sealed class AddEditTodoEvent {
    data class OnTitleChanged(val title : String) : AddEditTodoEvent()
    data class OnDescChanged(val desc : String) : AddEditTodoEvent()
    object OnSaveTodoClicked : AddEditTodoEvent()
}
