package com.example.android.architecture.blueprints.websocketclient.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.android.architecture.blueprints.websocketclient.R
import com.example.android.architecture.blueprints.websocketclient.service.WebSocketListener
import com.example.android.architecture.blueprints.websocketclient.ui.adapter.GreetingsRecyclerAdapter
import com.example.android.architecture.blueprints.websocketclient.viewmodels.MainViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import org.json.JSONObject

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private val greetingsRecyclerAdapter: GreetingsRecyclerAdapter = GreetingsRecyclerAdapter()

    private lateinit var viewModel: MainViewModel

    private lateinit var webSocketListener: WebSocketListener
    private val okHttpClient = OkHttpClient()
    private var webSocket: WebSocket? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
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

        val button = view.findViewById<Button>(R.id.button)
        val editText = view.findViewById<EditText>(R.id.edit_text)
        val linearLayout = view.findViewById<LinearLayout>(R.id.linear_layout)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recycler_view)

        recyclerView.adapter = greetingsRecyclerAdapter

        button.setOnClickListener {
            if (webSocket == null) {
                webSocket = okHttpClient.newWebSocket(createRequest(), webSocketListener)
            }
            val json = JSONObject().apply {
                put("isMessage", false)
                put("isGreeting", true)
                put("contents", editText.text)
            }
            webSocket?.send("$json")
            linearLayout.visibility = View.GONE
        }

        viewModel.list.observe(viewLifecycleOwner){ list ->
            Log.d("Test", "onViewCreated: I am observed!")
            Log.d("Test", "onViewCreated: list: $list")
            recyclerView.visibility = View.VISIBLE
            greetingsRecyclerAdapter.submitList(list.filter { contents -> contents.isGreeting })
        }
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