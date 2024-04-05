package com.example.android.architecture.blueprints.websocketclient.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.architecture.blueprints.websocketclient.R
import com.example.android.architecture.blueprints.websocketclient.service.WebSocketListener
import com.example.android.architecture.blueprints.websocketclient.viewmodels.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    private lateinit var webSocketListener: WebSocketListener
    private val okHttpClient = OkHttpClient()
    private var webSocket: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        webSocketListener = WebSocketListener(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        webSocket = okHttpClient.newWebSocket(createRequest(), webSocketListener)

        val textView = view.findViewById<TextView>(R.id.text_view)
        viewModel.text.observe(viewLifecycleOwner, textView::setText)
    }

    private fun createRequest(): Request {
        val websocketURL = "ws://10.0.2.2:443"
        return Request.Builder()
            .url(websocketURL)
            .build()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        okHttpClient.dispatcher.executorService.shutdown()
    }
}