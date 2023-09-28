package com.ys.ysmvi.ui.dialog

import android.view.*
import androidx.lifecycle.ViewModel
import com.ys.ysmvi.base.YsBaseBindDialogFragment
import com.ys.ysmvi.databinding.DialogSampleBinding

class DialogSample(private val hint: String): YsBaseBindDialogFragment<ViewModel, DialogSampleBinding>(0.75) {
    private lateinit var listener: Listener

    interface Listener {
        fun onLeft()
        fun onRight()
    }

    override fun initViewBinding() {
        binding = DialogSampleBinding.inflate(LayoutInflater.from(requireContext()))
    }

    override fun initViewModel() {}

    override fun collectViewModel() {}

    override fun setListener() {
        binding?.run {
            tvLeft.setOnClickListener {
                listener.onLeft()
                dismiss()
            }
            tvRight.setOnClickListener {
                listener.onRight()
                dismiss()
            }
        }
    }

    override fun init() {
        binding?.tvSample?.text = hint
    }

    fun setListener(l: Listener) { listener = l }
}
