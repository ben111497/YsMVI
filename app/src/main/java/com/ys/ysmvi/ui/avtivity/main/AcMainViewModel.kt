package com.ys.ysmvi.ui.avtivity.main

import com.ys.ysmvi.base.YsBaseViewModel

class AcMainViewModel: YsBaseViewModel<AcMainIntent, AcMainState>() {
    override fun createInitialState(): AcMainState { return AcMainState.Init }

    override fun handleIntent(intent: AcMainIntent) {
        when (intent) {
            AcMainIntent.Init -> {}
        }
    }
}