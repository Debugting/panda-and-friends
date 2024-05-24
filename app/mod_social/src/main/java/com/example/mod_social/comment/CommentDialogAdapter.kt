package com.example.mod_social.comment

import com.chad.library.adapter4.viewholder.QuickViewHolder
import com.example.lib_frame.base.BaseRcvAdapter
import com.example.lib_frame.utils.TimeUtils
import com.example.lib_frame.widgets.IconFontImageView
import com.example.mod_social.R
import com.example.mod_social.social.Comment

class CommentDialogAdapter : BaseRcvAdapter<Comment>() {

    override fun initItemType(): List<Pair<Int, (QuickViewHolder, Int, Comment) -> Unit>> =
        listOf(
            Pair(R.layout.comment_item) { holder, _, item ->
                holder.getView<IconFontImageView>(R.id.avatar).load(item.user?.imgUrl())
                holder.setText(R.id.userName, item.user?.name())
                holder.setText(R.id.word, item.word)
                holder.setText(R.id.time, TimeUtils.convertTimeCompareNow(item.createTime))
            },
            Pair(R.layout.comment_reply_item) { holder, _, item ->
                item.apply {
                    holder.getView<IconFontImageView>(R.id.avatar).load(user?.imgUrl())
                    holder.setText(R.id.userName, "${user?.name()} @ ${replyUser?.name()}")
                    holder.setText(R.id.word, word)
                    holder.setText(
                        R.id.time, TimeUtils.convertTimeCompareNow(item.createTime)
                    )
                }
            })

    override fun onItemViewType(pos: Int, datas: List<Comment>): Int =
        if (datas[pos].dataType == Comment.COMMENT_TYPE) {
            R.layout.comment_reply_item
        } else {
            R.layout.comment_item
        }
}
