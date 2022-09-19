package com.rkpandey.mvvm_todo.util

import android.os.Message

sealed class UiEvent {
    object PopBackStack : UiEvent()
    data class Navigate(val route:String):UiEvent()
    data class showSnackBar(
            val message:String,
            val action: String? = null
    ): UiEvent()
}