package com.rkpandey.mvvm_todo.ui.add_edit_todo

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.rkpandey.mvvm_todo.ui.theme.Primary2
import com.rkpandey.mvvm_todo.util.UiEvent
import kotlinx.coroutines.flow.collect

@Composable
fun AddEditToDoScreen (
        onPopBackStack: () -> Unit,
        viewModel:AddEditTodoViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    LaunchedEffect(key1=true){
        viewModel.uiEvent.collect{
            when(it){
                is UiEvent.PopBackStack -> onPopBackStack()
                is UiEvent.showSnackBar -> {
                    scaffoldState.snackbarHostState.showSnackbar(
                        message = it.message,
                        actionLabel = it.action
                    )
                }
                else -> Unit
            }
        }
    }
    
    Scaffold (
        scaffoldState = scaffoldState ,
        modifier =Modifier
            .fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick={
                viewModel.onEvent(AddEditTodoEvent.OnSaveTodoClicked)
            }) {
                Icon(imageVector=Icons.Default.Check,contentDescription="Save")
            }
        }
            ) {
        Column(
            modifier = Modifier.background(Primary2).fillMaxSize().padding(16.dp)
        ) {
            OutlinedTextField(
                value=viewModel.title,
                onValueChange={
                    viewModel.onEvent(AddEditTodoEvent.OnTitleChanged(it))
                },
                placeholder = {
                    Text(text = "Title")
                },
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text="Title")
                }
            )
            Spacer(modifier=Modifier.height(8.dp))
            OutlinedTextField(
                value=viewModel.desc,
                onValueChange={
                    viewModel.onEvent(AddEditTodoEvent.OnDescChanged(it))
                },
                placeholder = {
                    Text(text = "Description")
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = false,
                maxLines = 5,
                label = {
                    Text(text="Description")
                }
            )
        }
    }
}