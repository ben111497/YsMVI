package com.ys.ysmvi.ui.avtivity.main

sealed class AcMainState {
    object Init: AcMainState()
    object Error: AcMainState()
}