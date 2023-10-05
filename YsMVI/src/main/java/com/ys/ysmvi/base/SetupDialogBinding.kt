package com.ys.ysmvi.base

import android.app.Dialog
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding


abstract class SetupDialogBinding(val binding: ViewBinding) {
    abstract fun setup(dialog: Dialog, binding: ViewBinding)
}