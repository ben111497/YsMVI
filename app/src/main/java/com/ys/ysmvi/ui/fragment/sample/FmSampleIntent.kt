package com.ys.ysmvi.ui.fragment.sample

sealed class FmSampleIntent {
    object Init: FmSampleIntent()
    data class ShowDialog(val tag: String): FmSampleIntent()
    data class ShowToast(val tag: String, val content: String? = null): FmSampleIntent()
    data class OnBtnDecreaseClick(val num: Int): FmSampleIntent()
    data class OnBtnIncreaseClick(val num: Int): FmSampleIntent()
}