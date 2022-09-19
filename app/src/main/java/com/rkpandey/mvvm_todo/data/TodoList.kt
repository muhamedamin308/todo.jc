package com.rkpandey.mvvm_todo.data

import android.content.ClipDescription
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rkpandey.mvvm_todo.R

@Entity(tableName = "Todo")
data class TodoList(
        val title:String,
        val description:String,
        var isDone:Boolean,
        @PrimaryKey val id:Int? = null
)
