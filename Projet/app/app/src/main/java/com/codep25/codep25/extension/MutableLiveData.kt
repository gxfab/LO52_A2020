package com.codep25.codep25.extension

import androidx.lifecycle.MutableLiveData
import com.codep25.codep25.model.entity.Resource


fun <T> MutableLiveData<Resource<T>>.setSuccess(data: T? = null) {
    value = Resource(Resource.State.SUCCESS, data)
}

fun <T> MutableLiveData<Resource<T>>.setLoading() {
    value = Resource(Resource.State.LOADING, value?.data)
}

fun <T> MutableLiveData<Resource<T>>.setError(message: Int? = null) {
    value = Resource(Resource.State.ERROR, value?.data, message)
}

fun <T> MutableLiveData<Resource<T>>.postSuccess(data: T? = null) =
    postValue(Resource(Resource.State.SUCCESS, data))

fun <T> MutableLiveData<Resource<T>>.postLoading() =
    postValue(Resource(Resource.State.LOADING, value?.data))

fun <T> MutableLiveData<Resource<T>>.postError(message: Int? = null) =
    postValue(Resource(Resource.State.ERROR, value?.data, message))
