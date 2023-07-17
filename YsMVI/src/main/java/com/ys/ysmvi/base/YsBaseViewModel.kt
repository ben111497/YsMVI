package com.ys.ysmvi.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class YsBaseViewModel<Intent: Any, State: Any>: ViewModel() {
    private val initialState : State by lazy { createInitialState() }
    private val channel = MutableSharedFlow<Intent>(1)
    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<State> get() = _state

    abstract fun createInitialState(): State
    abstract fun handleIntent(intent: Intent)

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            channel.collect { intent -> handleIntent(intent) }
        }
    }

    fun processIntent(intent: Intent) { viewModelScope.launch { channel.emit(intent) } }
}