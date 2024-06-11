package com.example.android.architecture.blueprints.websocketclient.viewmodels


import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainViewModel : ViewModel() {

    private val _socketStatus = MutableLiveData(false)
    val socketStatus: LiveData<Boolean> = _socketStatus

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun setStatus(status: Boolean) = viewModelScope.launch(Dispatchers.Main) {
        _socketStatus.value = status
    }

    fun setText(text: String) {
        val json = JSONObject(text)

        Log.d("APP_TAG", "json: $json")
        if (json.get("isGreeting") == true) {
            _text.postValue("${json.get("contents")} has joined the chat")
        }
    }
}