package com.ys.ysmvi.ui.fragment.sample

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.ys.ysmvi.base.YsBaseFragment
import com.ys.ysmvi.data.room.RoomSample
import com.ys.ysmvi.databinding.FmSampleBinding
import com.ys.ysmvi.helper.setOnBackPressed
import com.ys.ysmvi.model.retrofit.Repository
import com.ys.ysmvi.ui.dialog.DialogSample
import com.ys.ysmvi.ui.dialog.ShowMessageDialog
import com.ys.ysmvi.ui.Factory.ViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FmSample: YsBaseFragment<FmSampleViewModel, FmSampleBinding>() {
    override fun initViewBinding() {
        binding = FmSampleBinding.inflate(layoutInflater)
    }

    override fun initViewModel() {
        //viewModel = ViewModelProvider(this)[FmSampleViewModel::class.java]
        val repo = Repository.getInstance(requireContext(), RoomSample.instance(requireContext()))
        viewModel = ViewModelProvider(this, ViewModelFactory(repo))[FmSampleViewModel::class.java]
    }

    override fun argument(bundle: Bundle?) {}

    override fun collectViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect {
                withContext(Dispatchers.Main) {
                    when (it) {
                        FmSampleState.Init -> {}
                        is FmSampleState.ShowDialog -> {
                            when (it.tag) {
                                "Message" -> showMessageDialog(it.tag, it.content)
                            }
                        }
                        is FmSampleState.ShowToast -> {
                            when (it.tag) {
                                "CurrentNumber" -> showToast(it.content)
                            }
                        }
                        is FmSampleState.NumberChange -> binding?.tvNumber?.text = "${it.num}"
                    }
                }
            }
        }
    }

    override fun setListener() {
        requireActivity().setOnBackPressed(this) { showBackDialog("Back", "...") }

        binding?.run {
            btnAdd.setOnClickListener { viewModel.processIntent(FmSampleIntent.OnBtnIncreaseClick(1)) }
            btnDecrease.setOnClickListener { viewModel.processIntent(FmSampleIntent.OnBtnDecreaseClick(1)) }
            btnDialog.setOnClickListener { viewModel.processIntent(FmSampleIntent.OnBtnDialog) }
            btnToast.setOnClickListener { viewModel.processIntent(FmSampleIntent.OnBtnToast) }
        }
    }

    /**
     * Function
     */
    private fun showToast(content: String) = Toast.makeText(requireActivity(), content, Toast.LENGTH_SHORT).show()

    private fun showBackDialog(tag: String, content: String) {
        ShowMessageDialog<FmSampleViewModel> {
            it.setTitle("$content")
            it.setListener(object: ShowMessageDialog.Listener {
                override fun onOk() { it.dismiss() }
            })
        }.show(requireActivity().supportFragmentManager, tag)
    }

    private fun showMessageDialog(tag: String, content: String) {
        DialogSample(content).also {
            it.setListener(object: DialogSample.Listener {
                override fun onLeft() {
                    viewModel.processIntent(FmSampleIntent.OnBtnDecreaseClick(1))
                }

                override fun onRight() {
                    viewModel.processIntent(FmSampleIntent.OnBtnIncreaseClick(1))
                }
            })
        }.show(requireActivity().supportFragmentManager, tag)
    }
}