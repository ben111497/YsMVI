package com.ys.ysmvi.ui.fragment.sample

sealed class FmSampleState {
    object Init: FmSampleState()
    data class ShowDialog(val tag: String): FmSampleState()
    data class ShowToast(val tag: String, val content: String): FmSampleState()
    data class NumberChange(val num: Int): FmSampleState()
}