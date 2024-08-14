package com.example.android.architecture.blueprints.websocketclient.viewmodels


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

    private val _greetingsData = MutableLiveData<List<Contents>>()
    val greetingsData: LiveData<List<Contents>> = _greetingsData

    private val _messageData = MutableLiveData<List<Contents>>()
    val messageData: LiveData<List<Contents>> = _messageData

    fun setStatus(status: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _socketStatus.value = status
    }

    fun setGreetingsList(jsonList: String) {
        val jsonArray = JSONArray(jsonList)
        val greetingsData = mutableListOf<Contents>()
        val jArray = jsonArray as JSONArray?
        if (jArray != null) {
            for (i in 0 until jArray.length()) {
                val text1 = (jArray.get(i) as JSONObject).get("contents") as String
                val id = (jArray.get(i) as JSONObject).get("id") as Int
                val isGreeting = (jArray.get(i) as JSONObject).get("isGreeting") as Boolean
                if (isGreeting) {
                    val contents = Contents(id = id, text = text1, isGreeting = true)
                    greetingsData.add(contents)
                }
            }
        }
        val filteredGreetings = greetingsData.filter { it.isGreeting }
        if (filteredGreetings.isNotEmpty()) {
            _greetingsData.postValue(greetingsData)
        }
    }
}