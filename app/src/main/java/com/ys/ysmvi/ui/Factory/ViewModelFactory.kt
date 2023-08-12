package com.ys.ysmvi.ui.Factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ys.ysmvi.model.retrofit.Repository
import com.ys.ysmvi.ui.fragment.sample.FmSampleRepo
import com.ys.ysmvi.ui.fragment.sample.FmSampleViewModel

class ViewModelFactory(private val repository: Repository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(FmSampleViewModel::class.java) -> FmSampleViewModel(FmSampleRepo(repository)) as T
            else -> throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}