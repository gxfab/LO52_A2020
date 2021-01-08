package com.codep25.codep25.model.entity

import androidx.annotation.StringRes

data class Resource<out T> constructor(
    val state: State,
    val data: T? = null,
    @StringRes val message: Int? = null
) {
    sealed class State {
        object LOADING : State()
        object SUCCESS : State()
        object ERROR : State()
    }
}
