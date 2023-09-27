package com.ys.ysmvi.ui.fragment.test2

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.ys.ysmvi.R
import com.ys.ysmvi.base.YsAct
import com.ys.ysmvi.base.YsBaseFragment
import com.ys.ysmvi.databinding.FmTest2Binding

class FmTest2: YsBaseFragment<ViewModel, FmTest2Binding>() {
    override fun initViewBinding() {
        binding = FmTest2Binding.inflate(layoutInflater)
    }

    override fun initViewModel() {}

    override fun argument(bundle: Bundle?) {}

    override fun collectViewModel() {}

    override fun setListener() {
        binding?.let {
            it.btnBack.setOnClickListener { YsAct.instance().goBack() }
            it.btnHome.setOnClickListener { YsAct.instance().goMenu() }
        }
    }
}