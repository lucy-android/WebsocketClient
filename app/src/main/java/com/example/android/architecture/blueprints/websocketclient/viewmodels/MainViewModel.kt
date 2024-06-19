package com.example.android.architecture.blueprints.websocketclient.viewmodels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.android.architecture.blueprints.websocketclient.ui.adapter.Contents
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject


class MainViewModel : ViewModel() {

    private val _socketStatus = MutableLiveData(false)
    val socketStatus: LiveData<Boolean> = _socketStatus

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    private val _list = MutableLiveData<List<Contents>>()
    val list: LiveData<List<Contents>> = _list

    fun setStatus(status: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _socketStatus.value = status
    }

    fun setList(jsonList: String) {
        val jsonArray = JSONArray(jsonList)
        val listData = mutableListOf<Contents>()
        val jArray = jsonArray as JSONArray?
        if (jArray != null) {
            for (i in 0 until jArray.length()) {
                val text1 = (jArray.get(i) as JSONObject).get("contents") as String
                val id = (jArray.get(i) as JSONObject).get("id") as Int
                val isGreeting = (jArray.get(i) as JSONObject).get("isGreeting") as Boolean
                val contents = Contents(id = id, text = text1, isGreeting = isGreeting)
                listData.add(contents)
            }
        }
        _list.postValue(listData)
        Log.d("Test", "setList: $jsonArray")
    }
}