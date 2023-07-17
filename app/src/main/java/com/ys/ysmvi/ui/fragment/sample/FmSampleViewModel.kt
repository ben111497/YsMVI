package com.ys.ysmvi.ui.fragment.sample

import androidx.lifecycle.viewModelScope
import com.ys.ysmvi.base.YsBaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class FmSampleViewModel(): YsBaseViewModel<FmSampleIntent, FmSampleState>() {
    private val number = MutableStateFlow<Int>(0)

    override fun createInitialState(): FmSampleState { return FmSampleState.Init }

    override fun handleIntent(intent: FmSampleIntent) {
        when (intent) {
            FmSampleIntent.Init -> {}
            FmSampleIntent.OnBtnDialog -> _state.value = FmSampleState.ShowDialog("Message", "${number.value}", Date().time)
            FmSampleIntent.OnBtnToast -> _state.value = FmSampleState.ShowToast("CurrentNumber", "${number.value}", Date().time)
            is FmSampleIntent.OnBtnDecreaseClick -> number.value -= intent.num
            is FmSampleIntent.OnBtnIncreaseClick -> number.value += intent.num
        }
    }

    init {
        dataProcess()
    }

    private fun dataProcess() {
        viewModelScope.launch {
            launch { number.collect { _state.value = FmSampleState.NumberChange(number.value) } }
        }
    }
}