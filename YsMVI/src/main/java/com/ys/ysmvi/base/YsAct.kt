package com.ys.ysmvi.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import com.ys.ysmvi.R
import java.lang.Exception


abstract class YsAct: AppCompatActivity() {
    companion object {
        private val dialogTag = HashMap<String, Dialog>()
        private lateinit var instance: Control
        private fun setInstance(ct: Control) { instance = ct }
        fun instance() = instance
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setInstance(control)
    }

    override fun onDestroy() {
        control.closeDialog()
        super.onDestroy()
    }

    private val control = object: Control {
        override fun getActivity() = this@YsAct

        override fun hideKeyboard(view: View?) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }

        override fun showDialog(layout: Int, cancelable: Boolean, transparency: Float, tag: String) {
            try {
                Log.e("Dialog", "showDialog tag: $tag")
                dialogTag[tag]?.cancel()
                dialogTag[tag] = Dialog(this@YsAct, R.style.SwipDialog).also { dialog ->
                    dialog.setContentView(layout)
                    dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
                    dialog.window!!.setDimAmount(transparency)
                    dialog.setCancelable(cancelable)
                    dialog.setCanceledOnTouchOutside(cancelable)

                    dialog.create()
                    dialog.show()
                }
            } catch (e: Exception) {
                Log.e("Dialog", "error: ${e.message}")
            }
        }

        override fun showSetupDialog(setupDialog: SetupDialog, cancelable: Boolean, transparency: Float, tag: String) {
            try {
                Log.e("Dialog", "showSetupDialog tag: $tag")
                dialogTag[tag]?.cancel()
                dialogTag[tag] = Dialog(this@YsAct, R.style.SwipDialog).also { dialog ->
                    dialog.setContentView(setupDialog.layout)
                    setupDialog.dialog = dialog
                    setupDialog.setup(dialog)
                    dialog.window!!.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT)
                    dialog.window!!.setDimAmount(transparency)
                    dialog.setCancelable(cancelable)
                    dialog.setCanceledOnTouchOutside(cancelable)

                    dialog.create()
                    dialog.show()
                }
            } catch (e: Exception) {
                Log.e("Dialog", "error: ${e.message}")
            }
        }

        override fun closeDialog(tag: String?) {
            Log.e("Dialog", "closeDialog: ${tag.takeIf { it != null } ?: "all"}")
            if (tag == null) {
                dialogTag.forEach { (_, dialog) -> dialog.cancel() }
                dialogTag.clear()
            } else {
                dialogTag[tag]?.cancel()
                dialogTag.remove(tag)
            }
        }
    }
}