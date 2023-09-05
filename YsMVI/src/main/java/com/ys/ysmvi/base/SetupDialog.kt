package com.ys.ysmvi.base

import android.app.Dialog


abstract class SetupDialog(val layout: Int) {
    lateinit var dialog: Dialog
    abstract fun setup(dialog: Dialog)
}