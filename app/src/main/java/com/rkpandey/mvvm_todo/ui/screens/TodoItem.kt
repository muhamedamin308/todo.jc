package com.rkpandey.mvvm_todo.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rkpandey.mvvm_todo.R
import com.rkpandey.mvvm_todo.data.TodoList

@Composable
fun TodoItem(
        todo : TodoList , onEvent : (ToDoListEvent) -> Unit , modifier : Modifier=Modifier
) {
    var checked by remember { mutableStateOf(todo.isDone) }
    val icon=
        if(checked) painterResource(id=R.drawable.check) else painterResource(id=R.drawable.check_box)
    Row(
        modifier=modifier ,
        verticalAlignment=Alignment.CenterVertically ,
    ) {
        Icon(
            painter=painterResource(id=R.drawable.task) ,
            contentDescription="To Do Icon" ,
            tint=Color.Unspecified
        )
        Column(
            modifier=modifier.weight(1f) , verticalArrangement=Arrangement.Center
        ) {
            Row(verticalAlignment=Alignment.CenterVertically) {
                Text(
                    text=todo.title , fontSize=20.sp , fontWeight=FontWeight.Bold
                )
                Spacer(modifier=Modifier.width(4.dp))
                IconButton(onClick={
                    onEvent(ToDoListEvent.OnDeleteTodoClicked(todo))
                }) {
                    Icon(
                        painter=painterResource(id=R.drawable.trash) ,
                        contentDescription="Trash" ,
                        tint=Color.Unspecified
                    )
                }
            }
            todo.description.let {
                Spacer(modifier=Modifier.height(4.dp))
                Text(text=it)
            }

        }
        IconButton(onClick={
            checked=!checked
            onEvent(ToDoListEvent.OnDoneChange(todo , checked))
        }) {
            Icon(
                painter=icon , contentDescription="Check" , tint=Color.Unspecified
            )
        }
    }
}