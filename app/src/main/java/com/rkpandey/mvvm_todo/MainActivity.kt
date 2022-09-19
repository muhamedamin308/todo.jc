package com.rkpandey.mvvm_todo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.rkpandey.mvvm_todo.ui.add_edit_todo.AddEditToDoScreen
import com.rkpandey.mvvm_todo.ui.screens.TodoListScreen
import com.rkpandey.mvvm_todo.ui.theme.MVVM_ToDoTheme
import com.rkpandey.mvvm_todo.ui.theme.Primary2
import com.rkpandey.mvvm_todo.util.Routes
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState : Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MVVM_ToDoTheme {
                    val navController=rememberNavController()
                    NavHost(
                        navController=navController , startDestination=Routes.TODO_LIST
                    ) {
                        composable(Routes.TODO_LIST) {
                            TodoListScreen(onNavigate={
                                navController.navigate(it.route)
                            })
                        }
                        composable(route=Routes.ADD_EDIT_TODO + "?todoId={todoId}" ,
                                   arguments=listOf(navArgument(name="todoId") {
                                       type=NavType.IntType
                                       defaultValue=-1
                                   })) {
                            AddEditToDoScreen(onPopBackStack={
                                navController.popBackStack()
                            })
                        }
                    }
                }
            }
        }
    }