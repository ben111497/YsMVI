package com.ys.ysmvi.ui.fragment.sample

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ys.ysmvi.base.YsBaseViewModel
import com.ys.ysmvi.data.DataStoreKeys
import com.ys.ysmvi.data.retrofit.Repo
import com.ys.ysmvi.data.retrofit.RequestInterface
import com.ys.ysmvi.model.retrofit.Repository
import com.ys.ysmvi.model.retrofit.YsResponse
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date

class FmSampleViewModel(private val repo: FmSampleRepo): YsBaseViewModel<FmSampleIntent, FmSampleState>() {
    private val number = MutableStateFlow<Int>(0)
    override fun createInitialState(): FmSampleState { return FmSampleState.Init }
    override fun handleIntent(intent: FmSampleIntent) {
        when (intent) {
            FmSampleIntent.Init -> getNumber()
            FmSampleIntent.OnBtnDialog -> _state.value = FmSampleState.ShowDialog("Message", "${number.value}")
            FmSampleIntent.OnBtnToast -> _state.value = FmSampleState.ShowToast("CurrentNumber", "${number.value}")
            is FmSampleIntent.OnBtnDecreaseClick -> setNumber(number.value - intent.num)
            is FmSampleIntent.OnBtnIncreaseClick -> setNumber(number.value + intent.num)
            is FmSampleIntent.OnBtnGetClick -> {
                if (intent.name.isEmpty())
                    _state.value = FmSampleState.ShowToast("InputEmpty", "Name is empty.")
                else
                    getGitDate(intent.name)
            }
        }
    }

    /**
     * Init
     */
    init {
        getNumber()
        dataProcess()
        apiResponse()
    }

    private fun getNumber() {
        viewModelScope.launch {
//            launch {
//                (repo.room as RoomSample).ApiDao().getByName("number")?.collect {
//                    Log.e("room", it.json)
//                    this.cancel()
//                }
//            }
            launch {
                repo.repo.dataStore.getPDS(DataStoreKeys.NUMBER, 0).collect {
                    Log.e("dataStore", "$it")
                    number.value = it
                    this.cancel()
                }
            }
        }
    }
    private fun dataProcess() {
        viewModelScope.launch {
            launch { number.collect { _state.value = FmSampleState.NumberChange(number.value) } }
        }
    }
    private fun apiResponse() {
        viewModelScope.launch {
            repo.repo.retrofit.ApiResponse.collect {
                when (it) {
                    is YsResponse.Success -> {
                        when (it.tag) {
                            "GitData" -> {
                                //API成功 但是沒有 toast?
                                _state.value = FmSampleState.ShowToast("Success", "${it.data}")
                            }
                        }
                    }
                    is YsResponse.Failed -> _state.value = FmSampleState.ShowToast("Failed", "Request Failed")
                    is YsResponse.TimeOut -> _state.value = FmSampleState.ShowToast("TimeOut", "Time Out")
                    else -> {}
                }
            }
        }
    }

    /**
     * Function
     */
    private fun setNumber(num: Int) {
        number.value = num
        viewModelScope.launch {
//            (repo.room as RoomSample).ApiDao().insert(Api("number", "{number: $num}"))
            repo.repo.dataStore.setPDS(DataStoreKeys.NUMBER, num)
        }
    }

    private fun getGitDate(name: String) {
        viewModelScope.launch { repo.getGitData("GitData", name) }
    }
}
