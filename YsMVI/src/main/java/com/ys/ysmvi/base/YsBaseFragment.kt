package com.ys.ysmvi.base

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.viewbinding.ViewBinding


abstract class YsBaseFragment<ViewModel: androidx.lifecycle.ViewModel, binding: ViewBinding>: Fragment(), LifecycleOwner {
    lateinit var viewModel: ViewModel
    var binding: binding? = null
    var resource = this.context?.resources

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewBinding()
        initViewModel()
        argument(arguments)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        collectViewModel()
        setListener()
    }

    abstract fun initViewBinding()
    abstract fun initViewModel()
    abstract fun argument(bundle: Bundle?)
    abstract fun collectViewModel()
    abstract fun setListener()

    fun checkAndRequestPermission(permission: String, TAG: Int, result: (Boolean, Int) -> Unit): Boolean {
        return if (ActivityCompat.checkSelfPermission(requireActivity(), permission) != PackageManager.PERMISSION_GRANTED) {
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted -> result(isGranted, TAG) }.launch(permission)
            false
        } else true
    }
}