package com.ys.ysmvi.ui.fragment.test1

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ys.ysmvi.R
import com.ys.ysmvi.base.YsAct
import com.ys.ysmvi.base.YsBaseFragment
import com.ys.ysmvi.databinding.FmTest1Binding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FmTest1: YsBaseFragment<FmTest1ViewModel, FmTest1Binding>() {
    override fun initViewBinding() {
        binding = FmTest1Binding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[FmTest1ViewModel::class.java]
    }

    override fun argument(bundle: Bundle?) {}

    override fun collectViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        FmTest1State.Init -> {}
                        FmTest1State.Back -> YsAct.instance().goBack()
                        FmTest1State.NextPage -> YsAct.instance().navigate(R.id.fmTest2)
                    }
                }
            }
        }
    }

    override fun setListener() {
        binding?.let {
            it.btnBack.setOnClickListener { viewModel.processIntent(FmTest1Intent.OnBtnBack) }
            it.btnNext.setOnClickListener { viewModel.processIntent(FmTest1Intent.OnBtnNext) }
        }
    }
}