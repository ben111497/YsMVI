package com.ys.ysmvi.base

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.GestureDetector
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.ys.ysmvi.R
import com.ys.ysmvi.helper.getScreenHeightPixel
import com.ys.ysmvi.helper.getScreenWidthPixel
import java.lang.Math.abs

open class YsBaseIdDialogFragment(private val layout: Int, private val pWidth: Double = 1.0, private val pHeight: Double = 1.0,
                                  val setup: (Dialog) -> Unit) : DialogFragment() {
    private var root: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        root = inflater.inflate(layout, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val screenWidth = context?.getScreenWidthPixel() ?: 0
        val screenHeight = context?.getScreenHeightPixel() ?: 0

        val width = when {
            pWidth == 0.0 -> screenWidth
            pWidth < 0.0 -> WindowManager.LayoutParams.WRAP_CONTENT
            else -> screenWidth * if (pWidth > 1.0) 1.0 else pWidth
        }.toInt()

        val height = when {
            pHeight == 0.0 -> screenHeight
            pHeight < 0.0 -> WindowManager.LayoutParams.WRAP_CONTENT
            else -> screenHeight * if (pHeight > 1.0) 1.0 else pHeight
        }.toInt()

        dialog?.window?.setLayout(width, height)
        dialog?.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog?.window?.setWindowAnimations(R.style.dialog_style_zoom)

        setup(dialog!!)
    }
}