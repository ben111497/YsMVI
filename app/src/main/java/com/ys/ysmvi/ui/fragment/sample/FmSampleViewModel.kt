package com.ys.ysmvi.ui.fragment.sample

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ys.ysmvi.base.YsBaseViewModel
import com.ys.ysmvi.data.DataStoreKeys
import com.ys.ysmvi.data.room.Api
import com.ys.ysmvi.data.room.RoomSample
import com.ys.ysmvi.model.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class FmSampleViewModel(private val repository: Repository): YsBaseViewModel<FmSampleIntent, FmSampleState>() {
    private val number = MutableStateFlow<Int>(0)

    override fun createInitialState(): FmSampleState { return FmSampleState.Init }

    override fun handleIntent(intent: FmSampleIntent) {
        when (intent) {
            FmSampleIntent.Init -> getNumber()
            FmSampleIntent.OnBtnDialog -> _state.value = FmSampleState.ShowDialog("Message", "${number.value}", Date().time)
            FmSampleIntent.OnBtnToast -> _state.value = FmSampleState.ShowToast("CurrentNumber", "${number.value}", Date().time)
            is FmSampleIntent.OnBtnDecreaseClick -> setNumber(number.value - intent.num)
            is FmSampleIntent.OnBtnIncreaseClick -> setNumber(number.value + intent.num)
        }
    }

    init {
        getNumber()
        dataProcess()
    }

    private fun getNumber() {
        viewModelScope.launch {
//            launch {
//                (repository.room as RoomSample).ApiDao().getByName("number")?.collect {
//                    Log.e("room", it.json)
//                    this.cancel()
//                }
//            }
            launch {
                repository.dataStore.getPDS(DataStoreKeys.NUMBER, 0).collect {
                    Log.e("dataStore", "$it")
                    number.value = it
                    this.cancel()
                }
            }
        }
    }

    private fun setNumber(num: Int) {
        number.value = num
        viewModelScope.launch {
//            (repository.room as RoomSample).ApiDao().insert(Api("number",  "{number: $num}"))
            repository.dataStore.setPDS(DataStoreKeys.NUMBER, num)
        }
    }

    private fun dataProcess() {
        viewModelScope.launch {
            launch { number.collect { _state.value = FmSampleState.NumberChange(number.value) } }
        }
    }
}