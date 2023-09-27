package com.ys.ysmvi.ui.fragment.test2

sealed class FmTest2State {
    object Init: FmTest2State()
    object Home: FmTest2State()
    object Back: FmTest2State()
}