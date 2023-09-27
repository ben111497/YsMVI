package com.ys.ysmvi.ui.fragment.test1

sealed class FmTest1Intent {
    object Init : FmTest1Intent()
    object OnBtnBack : FmTest1Intent()
    object OnBtnNext : FmTest1Intent()
}