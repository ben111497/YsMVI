package com.ys.ysmvi.ui.fragment.sample

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.ys.ysmvi.base.YsBaseFragment
import com.ys.ysmvi.databinding.FmSampleBinding

class FmSample: YsBaseFragment<FmSampleViewModel, FmSampleBinding>() {
    override fun initViewModel() {
        viewModel = ViewModelProvider(this)[FmSampleViewModel::class.java]
    }

    override fun initViewBinding() {
        binding = FmSampleBinding.inflate(layoutInflater)
    }

    override fun argument(bundle: Bundle?) {}

    override fun collectViewModel() {}

    override fun init() {}

    override fun setListener() {
//        requireActivity().setOnBackPressed(this) {
//            ShowMessageDialog<ViewModel> {
//                it.setTitle("此路不通")
//                it.setListener(object: ShowMessageDialog.Listener {
//                    override fun onOk() {
//                        it.dismiss()
//                    }
//                })
//            }.show(requireActivity().supportFragmentManager, "message")
//        }

        binding?.run {
//            btnAdd.setOnClickListener { viewModel.addNumber("", 1) }
//
//            btnDecrease.setOnClickListener { viewModel.addNumber("", -1) }
//
//            btnDialog.setOnClickListener {
//                DialogSample(tvNumber.text.toString()).also {
//                    it.setListener(object: DialogSample.Listener {
//                        override fun onLeft() {
//                            viewModel.addNumber("", -1)
//                        }
//
//                        override fun onRight() {
//                            viewModel.addNumber("", 1)
//                        }
//                    })
//                }.show(requireActivity().supportFragmentManager, "hi")
//            }
//
//            btnToast.setOnClickListener { context?.showToast(tvNumber.text.toString()) }
        }
    }
}