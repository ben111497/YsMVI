package com.ys.ysmvi.base

import android.app.Dialog


abstract class SetupDialog(val layout: Int) {
    abstract fun setup(dialog: Dialog)
}