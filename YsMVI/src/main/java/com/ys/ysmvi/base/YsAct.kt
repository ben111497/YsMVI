package com.ys.ysmvi.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.ys.ysmvi.R
import java.lang.Exception
import java.lang.ref.WeakReference


abstract class YsAct: AppCompatActivity() {
    private var navController: NavController? = null // 不要設置為靜態物件，會有內存洩漏問題
    private var menu: Int = -1
    private var tag = "YsAct"
    private var handler = Handler(Looper.myLooper()!!)
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
        navController = null
        super.onDestroy()
    }

    private fun checkNav(): Boolean {
        return if (navController == null) {
            log("Navigation is not initialized")
            false
        } else true
    }

    private fun log(str: String) { Log.e(tag, str) }

    private val control = object: Control {
        override fun getActivity() = this@YsAct

        override fun getHandler(): Handler = handler

        override fun hideKeyboard(view: View?) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view?.windowToken, 0)
        }

        /**
         * Dialog
         */
        override fun showDialog(layout: Int, cancelable: Boolean, transparency: Float, tag: String) {
            try {
                log("showDialog tag: $tag")
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
                log(" Dialog error: ${e.message}")
            }
        }

        override fun showSetupDialog(setupDialog: SetupDialog, cancelable: Boolean, transparency: Float, tag: String) {
            try {
                log("showSetupDialog tag: $tag")
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
                log("Dialog error: ${e.message}")
            }
        }

        override fun closeDialog(tag: String?) {
            log("closeDialog: ${tag.takeIf { it != null } ?: "all"}")
            if (tag == null) {
                dialogTag.forEach { (_, dialog) -> dialog.cancel() }
                dialogTag.clear()
            } else {
                dialogTag[tag]?.cancel()
                dialogTag.remove(tag)
            }
        }

        /**
         * Navigation
         */
        override fun initNavigation(nav: NavController) {
            navController = nav
            navController?.addOnDestinationChangedListener { _, destination, _ ->
                log("currentPage: ${destination.displayName.split("/")[1]}")
            }
        }

        override fun navigate(id: Int) {
            if (!checkNav()) return
            navController?.navigate(id)
        }

        override fun goBack(id: Int?) {
            if (!checkNav()) return
            if (id == null) navController?.navigateUp() else navController?.popBackStack(id, false)
        }

        override fun setMenu(id: Int) { menu = id }

        override fun goMenu() {
            if (!checkNav()) return
            if (menu == -1) {
                log("Do not set menu")
            } else {
                navController?.navigate(menu, null, NavOptions.Builder().setPopUpTo(menu, false).build())
            }
        }

        /**
         * Toast
         */
        override fun showToast(msg: String, duration: Int) {
            handler.post { Toast.makeText(getActivity(), msg, duration).show() }
        }
    }
}