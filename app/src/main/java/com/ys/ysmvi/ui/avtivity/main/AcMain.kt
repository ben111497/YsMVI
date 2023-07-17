package com.ys.ysmvi.ui.avtivity.main


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ys.ysmvi.base.YsBaseActivity
import com.ys.ysmvi.databinding.AcMainBinding
import kotlinx.coroutines.launch

class AcMain : YsBaseActivity<AcMainViewModel, AcMainBinding>() {
    override fun initViewBinding() {
        binding = AcMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[AcMainViewModel::class.java]
    }

    override fun collectViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                when (it) {
                    AcMainState.Init -> {}
                    AcMainState.Error -> {}
                }
            }
        }
    }

    override fun setListener() {}
}