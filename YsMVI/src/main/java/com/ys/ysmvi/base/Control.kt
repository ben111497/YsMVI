package com.ys.ysmvi.base

import android.view.View
import androidx.navigation.NavController
import androidx.navigation.Navigation


interface Control {
    fun getActivity(): YsAct
    fun hideKeyboard(view: View?)
    fun showDialog(layout: Int, cancelable: Boolean, transparency: Float = 0.5F, tag: String)
    fun showSetupDialog(setupDialog: SetupDialog, cancelable: Boolean, transparency: Float = 0.5F, tag: String)
    fun closeDialog(tag: String? = null)
    fun initNavigation(nav: NavController)
    fun navigate(id: Int)
    fun goBack(id: Int? = null)
    fun setMenu(id: Int)
    fun goMenu()
}