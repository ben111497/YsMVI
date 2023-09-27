package com.ys.ysmvi.ui.fragment.sample

import java.util.Date

sealed class FmSampleState {
    object Init: FmSampleState()
    data class NumberChange(val num: Int): FmSampleState()
    data class GetGitData(val text: String, val hash: Long = Date().time): FmSampleState()
    data class ShowToast(val tag: String, val content: String, val hash: Long = Date().time): FmSampleState()
    data class ShowDialog(val tag: String, val content: String, val hash: Long= Date().time): FmSampleState()
    data class Nav(val hash: Long= Date().time): FmSampleState()
}