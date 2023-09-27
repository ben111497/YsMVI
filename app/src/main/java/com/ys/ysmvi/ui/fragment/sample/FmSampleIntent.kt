package com.ys.ysmvi.ui.fragment.sample

sealed class FmSampleIntent {
    object Init: FmSampleIntent()
    data class OnBtnDecreaseClick(val num: Int): FmSampleIntent()
    data class OnBtnIncreaseClick(val num: Int): FmSampleIntent()
    data class OnBtnGetClick(val name: String): FmSampleIntent()
    object OnBtnDialog: FmSampleIntent()
    object OnBtnToast: FmSampleIntent()
    object OnBtnNav: FmSampleIntent()
}