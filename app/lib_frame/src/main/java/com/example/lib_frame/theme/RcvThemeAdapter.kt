package com.example.lib_frame.theme

import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.lib_frame.R
import com.example.lib_frame.base.BaseRcvAdapter
import com.example.lib_frame.bean.ThemeBean

class RcvThemeAdapter : BaseRcvAdapter<ThemeBean>() {

    override fun initItemType(): List<Pair<Int, (QuickViewHolder, Int, ThemeBean) -> Unit>> =
        listOf(
            Pair(R.layout.theme_rcv_item) { holder, _, item ->
                holder.setBackgroundResource(R.id.theme, item.color)
                holder.setText(R.id.tvTitle, item.name)
            }
        )

    override fun onItemViewType(pos: Int, datas: List<ThemeBean>): Int = R.layout.theme_rcv_item

}
