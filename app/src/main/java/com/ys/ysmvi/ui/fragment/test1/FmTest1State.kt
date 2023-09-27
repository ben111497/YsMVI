package com.ys.ysmvi.ui.fragment.test1

sealed class FmTest1State {
    object Init: FmTest1State()
    object NextPage: FmTest1State()
    object Back: FmTest1State()
}