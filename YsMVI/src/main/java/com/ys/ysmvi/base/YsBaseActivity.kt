package com.ys.ysmvi.base

import android.os.Bundle
import androidx.viewbinding.ViewBinding


abstract class YsBaseActivity<ViewModel: androidx.lifecycle.ViewModel, binding: ViewBinding>: YsAct() {
    lateinit var viewModel: ViewModel
    lateinit var binding: binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initViewModel()
        viewInit()
        collectViewModel()
        setListener()
    }

    abstract fun initViewBinding()
    abstract fun initViewModel()
    abstract fun viewInit()
    abstract fun collectViewModel()
    abstract fun setListener()
}