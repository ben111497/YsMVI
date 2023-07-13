package com.ys.ysmvi.ui.avtivity.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AcMainViewModel: ViewModel() {
    private val channel = MutableStateFlow<AcMainIntent>(AcMainIntent.Init)
    private val _state = MutableStateFlow<AcMainState>(AcMainState.Init)
    val state: StateFlow<AcMainState> get() = _state

    init {
        handleIntent()
    }

    private fun handleIntent() {
        viewModelScope.launch {
            channel.collect {
                when (it) {
                    AcMainIntent.Init -> {}
                }
            }
        }
    }
}