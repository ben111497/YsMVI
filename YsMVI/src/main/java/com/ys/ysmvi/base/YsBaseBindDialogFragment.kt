package com.ys.ysmvi.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.ys.ysmvi.R
import com.ys.ysmvi.helper.getScreenWidthPixel

abstract class YsBaseBindDialogFragment<ViewModel: androidx.lifecycle.ViewModel?, Binding: ViewBinding>
constructor(private val widthPercent: Double = -1.0, private val heightPercent: Double = -1.0): DialogFragment() {
    var viewModel: ViewModel? = null
    var binding: Binding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initViewModel()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val screenWidth = context?.getScreenWidthPixel() ?: 0

        /**
        x == 0.0 -> Match
        x < 0.0 -> Wrap (預設)
        other -> 指定百分比寬度
         */
        val width = when {
            widthPercent == 0.0 -> screenWidth
            widthPercent < 0.0 -> WindowManager.LayoutParams.WRAP_CONTENT
            else -> screenWidth * if (widthPercent > 1.0) 1.0 else widthPercent
        }.toInt()

        val height = when {
            heightPercent == 0.0 -> screenWidth
            heightPercent < 0.0 -> WindowManager.LayoutParams.WRAP_CONTENT
            else -> screenWidth * if (heightPercent > 1.0) 1.0 else heightPercent
        }.toInt()

        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setWindowAnimations(R.style.dialog_style_zoom)

        collectViewModel()
        setListener()
        init()
    }

    abstract fun initViewBinding()
    abstract fun initViewModel()
    abstract fun collectViewModel()
    abstract fun setListener()
    abstract fun init()
}