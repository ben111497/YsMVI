package com.ys.ysmvi.ui.fragment.sample

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ys.ysmvi.base.YsBaseViewModel
import com.ys.ysmvi.data.DataStoreKeys
import com.ys.ysmvi.data.retrofit.Repo
import com.ys.ysmvi.data.room.Api
import com.ys.ysmvi.data.room.RoomSample
import com.ys.ysmvi.model.retrofit.YsResponse
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class FmSampleViewModel(private val repo: FmSampleRepo): YsBaseViewModel<FmSampleIntent, FmSampleState>() {
    private val number = MutableStateFlow<Int>(0)
    override fun createInitialState(): FmSampleState { return FmSampleState.Init }
    override fun handleIntent(intent: FmSampleIntent) {
        when (intent) {
            FmSampleIntent.Init -> {}
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
        initData()
        dataProcess()
        apiResponse()
    }

    private fun initData() {
        viewModelScope.launch {
            launch {
                (repo.repo.room as RoomSample).ApiDao().getByName("GitData").collect {
                    if (it == null) {
                        this.cancel()
                        return@collect
                    }

                    val data = Gson().fromJson<ArrayList<Repo>>(it.json, object : TypeToken<ArrayList<Repo>>() {}.type)[0]
                    val str = "name: ${data.name}\nid: ${data.id}\ndata: ${data.owner}"
                    _state.value = FmSampleState.GetGitData(str)
                    this.cancel()
                }
            }
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
                    is YsResponse.Success<*> -> {
                        when (it.tag) {
                            "GitData" -> {
                                val data = (it.data as ArrayList<Repo>)[0]
                                val str = "name: ${data.name}\nid: ${data.id}\ndata: ${data.owner}"
                                (repo.repo.room as RoomSample).ApiDao().insert(Api(it.tag, Gson().toJson(it.data)))
                                _state.value = FmSampleState.GetGitData(str)
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
            repo.repo.dataStore.setPDS(DataStoreKeys.NUMBER, num)
        }
    }

    private fun getGitDate(name: String) {
        viewModelScope.launch { repo.getGitData("GitData", name) }
    }
}
