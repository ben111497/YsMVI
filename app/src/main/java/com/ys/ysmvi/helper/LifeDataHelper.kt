package com.example.mvvm.helper

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

fun <T> LifecycleOwner.observe(liveData: LiveData<T>, action: (t: T) -> Unit) {
    liveData.observe(this, Observer { it?.let { t -> action(t) } })
}

fun <T> MutableLiveData<ArrayList<T>>.add(liveData: T) {
    val value = this.value ?: ArrayList<T>()
    value.add(liveData)
    this.value = value
}

fun <T> MutableLiveData<ArrayList<T>>.addAll(liveData: ArrayList<T>) {
    val value = this.value ?: ArrayList<T>()
    value.addAll(liveData)
    this.value = value
}

fun <T> MutableLiveData<ArrayList<T>>.remove(liveData: T) {
    val value = this.value ?: ArrayList<T>()
    value.remove(liveData)
    this.value = value
}

fun <T> MutableLiveData<ArrayList<T>>.clear() {
    this.value = ArrayList<T>()
}