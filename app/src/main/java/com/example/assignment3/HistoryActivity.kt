package com.example.assignment3

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        val historyFragment = supportFragmentManager.findFragmentById(R.id.history_fragment_container) as? HistoryFragment
            ?: HistoryFragment().also {
                supportFragmentManager.beginTransaction()
                    .add(R.id.history_fragment_container, it)
                    .commitNow()
            }

        val historyList = intent.getStringArrayListExtra("HISTORY_LIST")

        // 使用 LifecycleScope 推迟历史记录添加的执行，确保视图已经完全加载
        lifecycleScope.launch(Dispatchers.Main) {
            historyList?.forEach { expression ->
                historyFragment.addToHistory(expression)
            }
        }
    }
}


