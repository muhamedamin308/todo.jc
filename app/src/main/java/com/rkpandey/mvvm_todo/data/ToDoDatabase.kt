package com.rkpandey.mvvm_todo.data

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [TodoList::class],
    version = 3
)
abstract class ToDoDatabase : RoomDatabase() {
    abstract val todoDao : TodoDao
}