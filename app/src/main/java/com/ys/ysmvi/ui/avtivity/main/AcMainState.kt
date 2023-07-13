package com.ys.ysmvi.ui.avtivity.main

sealed class AcMainState {
    object Init: AcMainState()
    class TvTitleState(val str: String): AcMainState()
    class TvContentState(val num: Int): AcMainState()
    class DialogLoadingState(val open: Boolean): AcMainState()
    object Error: AcMainState()
}