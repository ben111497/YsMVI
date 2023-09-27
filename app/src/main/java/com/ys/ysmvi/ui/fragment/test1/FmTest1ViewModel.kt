package com.ys.ysmvi.ui.fragment.test1

import com.ys.ysmvi.base.YsBaseViewModel


class FmTest1ViewModel(): YsBaseViewModel<FmTest1Intent, FmTest1State>() {
    override fun createInitialState(): FmTest1State { return FmTest1State.Init }
    override fun handleIntent(intent: FmTest1Intent) {
        when (intent) {
            FmTest1Intent.Init -> {}
            FmTest1Intent.OnBtnBack -> _state.value = FmTest1State.Back
            FmTest1Intent.OnBtnNext -> _state.value = FmTest1State.NextPage
        }
    }
}
