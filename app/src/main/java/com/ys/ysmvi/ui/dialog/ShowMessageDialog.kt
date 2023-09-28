package com.ys.ysmvi.ui.dialog

import android.view.LayoutInflater
import androidx.lifecycle.ViewModel
import com.ys.ysmvi.base.YsBaseBindDialogFragment
import com.ys.ysmvi.databinding.DialogMessageBinding

class ShowMessageDialog<viewModel: ViewModel?>(private val created: (ShowMessageDialog<viewModel>) -> Unit)
    : YsBaseBindDialogFragment<viewModel, DialogMessageBinding>(0.75) {
    private lateinit var listener: Listener

    interface Listener {
        fun onOk()
    }

    override fun initViewBinding() {
        binding = DialogMessageBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun initViewModel() {}
    override fun collectViewModel() {}

    override fun setListener() {
        binding?.run {
            tvOk.setOnClickListener { listener.onOk() }
            tvCancel.setOnClickListener { dismiss() }
        }
    }

    override fun init() { created(this) }

    fun setListener(l: Listener) { listener = l }

    fun setTitle(str: String) { binding?.tvMessage?.text = str }
}
