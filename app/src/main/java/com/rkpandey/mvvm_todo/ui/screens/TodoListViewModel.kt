package com.rkpandey.mvvm_todo.ui.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkpandey.mvvm_todo.data.TodoList
import com.rkpandey.mvvm_todo.data.TodoRepo
import com.rkpandey.mvvm_todo.util.Routes
import com.rkpandey.mvvm_todo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class TodoListViewModel @Inject constructor(
        private val repo: TodoRepo
) : ViewModel(){
    val Todos = repo.getTodos()
    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()
    private var deletedTodo : TodoList? = null


    fun onEvent(event:ToDoListEvent){
        when(event){
            is ToDoListEvent.OnTodoClick -> {
                sendUiEvents(UiEvent.Navigate(Routes.ADD_EDIT_TODO + "?todoId=${event.todo.id}"))
            }
            is ToDoListEvent.OnAddTodoClicked -> {
                sendUiEvents(UiEvent.Navigate(Routes.ADD_EDIT_TODO))
            }
            is ToDoListEvent.OnDeleteTodoClicked -> {
                viewModelScope.launch {
                    deletedTodo = event.todo
                    repo.deleteTodo(event.todo)
                    sendUiEvents(UiEvent.showSnackBar(
                        message = "${event.todo.title} Todo Deleted",
                        action = "Undo"
                    ))
                }
            }
            is ToDoListEvent.OnDoneChange -> {
                viewModelScope.launch {
                    repo.insertTodo(
                        event.todo.copy(
                            isDone = event.isDone
                        )
                    )
                }
            }
            is ToDoListEvent.OnUndoDeleteClick -> {
                deletedTodo?.let {
                    viewModelScope.launch {
                        repo.insertTodo(it)
                    }
                }
            }
        }
    }
    private fun sendUiEvents (uiEvent:UiEvent){
        viewModelScope.launch {
            _uiEvents.send(uiEvent)
        }
    }
}