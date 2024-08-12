package com.example.assignment3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryFragment : Fragment() {

    private lateinit var historyTextView: TextView
    private val pendingHistory = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        historyTextView = view.findViewById(R.id.history_text_view)

        // 添加pending的历史记录
        lifecycleScope.launch {
            pendingHistory.forEach { expression ->
                withContext(Dispatchers.Main) {
                    historyTextView.append("$expression\n")
                }
            }
            pendingHistory.clear() // 清除已经添加的历史记录
        }

        return view
    }

    fun addToHistory(expression: String) {
        if (::historyTextView.isInitialized) {
            historyTextView.append("$expression\n")
        } else {
            pendingHistory.add(expression) // 如果视图未创建，则将记录添加到待处理队列
        }
    }
}


