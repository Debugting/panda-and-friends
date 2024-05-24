package com.example.lib_frame.widgets.search

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.lib_frame.R
import com.example.lib_frame.manager.AppManager

class SearchHintDialog : PopupWindow() {

    private val mRcvAdapter = SearchHintRcvAdapter()

    init {
        this.width = ViewGroup.LayoutParams.MATCH_PARENT
        this.height = ViewGroup.LayoutParams.WRAP_CONTENT
        val rootView: View = LayoutInflater.from(AppManager.activity)
            .inflate(R.layout.search_hint_list_layout, null, false)
        val recyclerView = rootView.findViewById<RecyclerView>(R.id.rcv_search_hint)
        recyclerView.setAdapter(mRcvAdapter)
        contentView = rootView
    }

    fun setOnClickListener(onClickListener: BaseQuickAdapter.OnItemClickListener<String>) {
        mRcvAdapter.setOnItemClickListener(onClickListener)
    }

    fun setItems(dataList: List<String>) {
        mRcvAdapter.items = dataList
    }
}
