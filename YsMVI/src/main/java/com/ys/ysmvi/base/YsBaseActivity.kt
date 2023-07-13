package com.ys.ysmvi.base

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.viewbinding.ViewBinding


abstract class YsBaseActivity<ViewModel: androidx.lifecycle.ViewModel, binding: ViewBinding>: AppCompatActivity() {
    lateinit var viewModel: ViewModel
    lateinit var binding: binding
    var mRequestPermission: RequestPermission? = null

    interface RequestPermission {
        fun onCallBack(isSuccess: Boolean, requestCode: Int)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initViewModel()
        collectViewModel()
        setListener()
        init()
    }

    abstract fun initViewBinding()
    abstract fun initViewModel()
    abstract fun collectViewModel()
    abstract fun init()
    abstract fun setListener()

    fun checkAndRequestPermission(permission: String, TAG: Int): Boolean {
        return if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, Array<String>(1) { permission }, TAG)
            false
        } else true
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mRequestPermission?.onCallBack(true, requestCode)
        } else {
            mRequestPermission?.onCallBack(false, requestCode)
        }
    }
}