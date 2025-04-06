package com.example.speer_rinku_android_assesment.base

sealed class UIState<out T> {
    data class Success<T>(val data: T) : UIState<T>()
    data class Error(val message: String) : UIState<Nothing>()
    object Loading : UIState<Nothing>()
    data class NoDataFound(val message: String) : UIState<Nothing>()
}