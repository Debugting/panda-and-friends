package com.example.lib_frame.widgets.search

import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.lib_frame.R
import com.example.lib_frame.base.BaseRcvAdapter
import com.example.lib_frame.bean.ThemeBean

open class SearchHintRcvAdapter : BaseRcvAdapter<String>() {

    override fun initItemType(): List<Pair<Int, (QuickViewHolder, Int, String) -> Unit>> =
        listOf(
            Pair(R.layout.search_rcv_item) { holder, _, item ->
                holder.setText(R.id.tv_title, item)
            }
        )

    override fun onItemViewType(pos: Int, datas: List<String>): Int = R.layout.search_rcv_item
}
