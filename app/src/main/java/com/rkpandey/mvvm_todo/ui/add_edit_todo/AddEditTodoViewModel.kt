package com.rkpandey.mvvm_todo.ui.add_edit_todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rkpandey.mvvm_todo.data.TodoList
import com.rkpandey.mvvm_todo.data.TodoRepo
import com.rkpandey.mvvm_todo.util.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditTodoViewModel @Inject constructor(
        private val repo : TodoRepo,
        saveStateHandle : SavedStateHandle
) : ViewModel(){
    var todo by mutableStateOf<TodoList?>(null)
        private set

    var title by mutableStateOf("")
        private set

    var desc by mutableStateOf("")
        private set

    private val _uiEvents = Channel<UiEvent>()
    val uiEvent = _uiEvents.receiveAsFlow()

    init {
        val todoId = saveStateHandle.get<Int>("todoId")!!
        if(todoId != -1){
            viewModelScope.launch {
                repo.getTodoById(todoId)?.let {
                    title = it.title
                    desc = it.description
                    this@AddEditTodoViewModel.todo = it
                }
            }
        }
    }

    fun onEvent(event: AddEditTodoEvent){
        when(event){
            is AddEditTodoEvent.OnTitleChanged -> {
                title = event.title
            }
            is AddEditTodoEvent.OnDescChanged -> {
                desc = event.desc
            }
            is AddEditTodoEvent.OnSaveTodoClicked -> {
                viewModelScope.launch {
                    if(title.isBlank()){
                        sendUiEvents(UiEvent.showSnackBar(
                            message = "The title can't be empty"
                        ))
                        return@launch
                    }
                    repo.insertTodo(
                        TodoList(
                            title = title,
                            description = desc,
                            isDone = todo?.isDone ?: false,
                            id = todo?.id
                        )
                    )
                    sendUiEvents(UiEvent.PopBackStack)
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