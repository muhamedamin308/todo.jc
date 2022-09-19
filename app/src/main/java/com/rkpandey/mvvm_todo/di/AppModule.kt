package com.rkpandey.mvvm_todo.di

import android.app.Application
import androidx.room.Room
import com.rkpandey.mvvm_todo.data.ToDoDatabase
import com.rkpandey.mvvm_todo.data.TodoRepo
import com.rkpandey.mvvm_todo.data.TodoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(
    SingletonComponent::class
)
object AppModule {

    @Provides
    @Singleton
    fun provideToDodb(app : Application): ToDoDatabase{
        return Room.databaseBuilder(
            app,
            ToDoDatabase::class.java,
            "todo_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTodoRepo(db: ToDoDatabase): TodoRepo{
        return TodoRepository(db.todoDao)
    }
}