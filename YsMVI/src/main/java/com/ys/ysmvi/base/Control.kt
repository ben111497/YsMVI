package com.ys.ysmvi.base

import android.view.View


interface Control {
    fun getActivity(): YsAct
    fun hideKeyboard(view: View?)
    fun showDialog(layout: Int, cancelable: Boolean, transparency: Float = 0.5F, tag: String)
    fun showDialog(setupDialog: SetupDialog, cancelable: Boolean, transparency: Float = 0.5F, tag: String)
    fun closeDialog(tag: String? = null)
}