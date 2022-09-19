package com.rkpandey.mvvm_todo.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rkpandey.mvvm_todo.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun TodoListScreen (
        onNavigate: (UiEvent.Navigate) -> Unit,
        viewModel:TodoListViewModel = hiltViewModel()
){
    val todos = viewModel.Todos.collectAsState(initial=emptyList())
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1=true){
        viewModel.uiEvent.collect{
            when(it){
                is UiEvent.showSnackBar -> {
                    val result = scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = it.action
                    )
                    if(result == SnackbarResult.ActionPerformed){
                        viewModel.onEvent(ToDoListEvent.OnUndoDeleteClick)
                    }
                }
                is UiEvent.Navigate -> {
                    onNavigate(it)
                }
                else -> Unit
            }
        }
    }
    Scaffold(
        scaffoldState = scaffoldState,
        floatingActionButton = {
            FloatingActionButton(onClick={
                viewModel.onEvent(ToDoListEvent.OnAddTodoClicked)
            }) {
                Icon (imageVector = Icons.Filled.Add,
                contentDescription = "Add")
            }
        }

    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ){
            items(todos.value){ todo ->
                TodoItem(
                    todo = todo,
                    onEvent = viewModel::onEvent,
                    modifier = Modifier.fillMaxWidth().clickable {
                        viewModel.onEvent(ToDoListEvent.OnTodoClick(todo))
                    }
                        .padding(16.dp)
                )
            }
        }
    }
}