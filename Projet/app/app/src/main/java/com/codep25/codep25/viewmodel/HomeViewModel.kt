package com.codep25.codep25.viewmodel

import android.content.Context
import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.codep25.codep25.extension.postSuccess
import com.codep25.codep25.extension.setLoading
import com.codep25.codep25.model.entity.Resource
import javax.inject.Inject

class HomeViewModel @Inject constructor() : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Home Fragment"
    }
    val text: LiveData<String> = _text

}
