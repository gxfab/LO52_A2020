package com.codep25.codep25.viewmodel

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codep25.codep25.extension.postSuccess
import com.codep25.codep25.extension.setLoading
import com.codep25.codep25.model.entity.Resource
import javax.inject.Inject

class SplashViewModel @Inject constructor() : ViewModel() {
    private val _isReady = MutableLiveData<Resource<Boolean>>()

    // Exported LiveData
    val isReady: LiveData<Resource<Boolean>> = _isReady

    init {
        _isReady.setLoading()
    }

    fun runInternalChecks() {
        // Delay a little bit to see the logo
        val handler = Handler()
        handler.postDelayed({ _isReady.apply { postSuccess(true) } }, 666)
    }
}
