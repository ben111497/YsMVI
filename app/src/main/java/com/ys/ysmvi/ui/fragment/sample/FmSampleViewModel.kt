package com.ys.ysmvi.ui.fragment.sample

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FmSampleViewModel() : ViewModel() {
    private val channel = MutableStateFlow<FmSampleIntent>(FmSampleIntent.Init)
    private val _state = MutableStateFlow<FmSampleState>(FmSampleState.Init)
    private val number = MutableStateFlow<Int>(0)
    val state: StateFlow<FmSampleState> get() = _state

    init {
        handleIntent()
        dataProcess()
    }

    fun processIntent(intent: FmSampleIntent) { channel.value = intent }
    private fun handleIntent() {
        viewModelScope.launch {
            channel.collect { intent ->
                when (intent) {
                    FmSampleIntent.Init -> {}
                    is FmSampleIntent.ShowDialog -> _state.value = FmSampleState.ShowDialog(intent.tag)
                    is FmSampleIntent.ShowToast -> {
                        when (intent.tag) {
                            "currentNumber" -> _state.value = FmSampleState.ShowToast(intent.tag, "${number.value}")
                            "content" -> _state.value = FmSampleState.ShowToast(intent.tag, "${intent.content}")
                        }
                    }
                    is FmSampleIntent.OnBtnDecreaseClick -> number.value -= intent.num
                    is FmSampleIntent.OnBtnIncreaseClick -> number.value += intent.num
                }
            }
        }
    }

    private fun dataProcess() {
        viewModelScope.launch {
            launch { number.collect { _state.value = FmSampleState.NumberChange(number.value) } }
        }
    }
}