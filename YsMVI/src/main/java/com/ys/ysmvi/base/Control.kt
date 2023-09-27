package com.ys.ysmvi.base

import android.view.View
import android.widget.Toast
import androidx.navigation.NavController


interface Control {
    fun getActivity(): YsAct
    fun getHandler(): android.os.Handler
    fun hideKeyboard(view: View?)
    fun showDialog(layout: Int, cancelable: Boolean, transparency: Float = 0.5F, tag: String)
    fun showSetupDialog(setupDialog: SetupDialog, cancelable: Boolean, transparency: Float = 0.5F, tag: String)
    fun closeDialog(tag: String? = null)
    fun initNavigation(nav: NavController)
    fun navigate(id: Int)
    fun goBack(id: Int? = null)
    fun setMenu(id: Int)
    fun goMenu()
    fun showToast(msg: String, duration: Int = Toast.LENGTH_SHORT)
}