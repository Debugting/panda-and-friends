package com.example.mod_paper.adapter

import android.widget.ImageView
import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.lib_frame.base.BaseRcvAdapter
import com.example.lib_frame.extensions.load
import com.example.lib_frame.widgets.IconFontImageView
import com.example.mod_paper.dao.Paper
import com.example.mod_paper.R

class PaperRcvAdapter : BaseRcvAdapter<Paper>() {

    override fun initItemType(): List<Pair<Int, (QuickViewHolder, Int, Paper) -> Unit>> = listOf(
        Pair(R.layout.paper_item_0) { holder, _, item ->
            item.apply {
                holder.getView<IconFontImageView>(R.id.iv_head).load(user?.imgUrl())
                holder.setText(R.id.tv_name, user?.name())
                holder.setText(R.id.tv_time, createTime)

                holder.setText(R.id.tv_title, title)
                holder.setText(R.id.tv_word, word)

                holder.setText(R.id.tv_visited, "$visitedCount")
                holder.setText(R.id.tv_praised, "$praisedCount")
                holder.setText(R.id.tv_comment, "$commentCount")
            }
        },
        Pair(R.layout.paper_item_1) { holder, _, item ->
            item.apply {
                holder.getView<IconFontImageView>(R.id.iv_head).load(user?.imgUrl())
                holder.setText(R.id.tv_name, user?.name())
                holder.setText(R.id.tv_time, createTime)

                holder.setText(R.id.tv_title, title)
                holder.setText(R.id.tv_word, word)
                holder.getView<ImageView>(R.id.iv_img).load(imgUrl)

                holder.setText(R.id.tv_visited, "$visitedCount")
                holder.setText(R.id.tv_praised, "$praisedCount")
                holder.setText(R.id.tv_collected, "$collectedCount")
                holder.setText(R.id.tv_comment, "$commentCount")
            }
        },
        Pair(R.layout.paper_item_2) { holder, _, item ->
            item.apply {
                holder.getView<IconFontImageView>(R.id.iv_head).load(user?.imgUrl())
                holder.setText(R.id.tv_name, user?.name())
                holder.setText(R.id.tv_time, createTime)

                holder.setText(R.id.tv_title, title)
                holder.setText(R.id.tv_word, word)
                holder.getView<ImageView>(R.id.iv_img).load(imgUrl)

                holder.setText(R.id.tv_visited, "$visitedCount")
                holder.setText(R.id.tv_praised, "$praisedCount")
                holder.setText(R.id.tv_collected, "$collectedCount")
                holder.setText(R.id.tv_comment, "$commentCount")
            }
        },
        Pair(R.layout.paper_item_3) { holder, _, item ->
            item.apply {
                holder.getView<IconFontImageView>(R.id.iv_head).load(user?.imgUrl())
                holder.setText(R.id.tv_name, user?.name())
                holder.setText(R.id.tv_time, createTime)

                holder.setText(R.id.tv_title, title)
                holder.setText(R.id.tv_word, word)
                holder.getView<ImageView>(R.id.iv_img).load(imgUrl)

                holder.setText(R.id.tv_visited, "$visitedCount")
                holder.setText(R.id.tv_praised, "$praisedCount")
                holder.setText(R.id.tv_collected, "$collectedCount")
                holder.setText(R.id.tv_comment, "$commentCount")
            }
        },
    )

    override fun onItemViewType(pos: Int, datas: List<Paper>): Int = with(datas[pos]) {
        if (imgUrl3.isNotEmpty()) {
            R.layout.paper_item_3
        } else if (imgUrl2.isNotEmpty()) {
            R.layout.paper_item_2
        } else if (imgUrl.isNotEmpty()) {
            R.layout.paper_item_1
        } else {
            R.layout.paper_item_0
        }
    }
}
