package com.example.lib_frame.base

import android.content.Context
import android.view.ViewGroup
import com.chad.library.adapter4.BaseMultiItemAdapter
import com.chad.library.adapter4.viewholder.QuickViewHolder

abstract class BaseRcvAdapter<T : Any> : BaseMultiItemAdapter<T>() {

    init {
        initItemType().onEach {
            addItemType(it.first, object : OnMultiItemAdapterListener<T, QuickViewHolder> {
                override fun onBind(holder: QuickViewHolder, position: Int, item: T?) {
                    item?.apply {
                        it.second.invoke(holder, position, this)
                    }
                }

                override fun onCreate(
                    context: Context, parent: ViewGroup, viewType: Int
                ): QuickViewHolder = QuickViewHolder(it.first, parent)
            })
        }
        onItemViewType { position, list ->
            onItemViewType(position, list)
        }
    }

    abstract fun initItemType(): List<Pair<Int, (QuickViewHolder, Int, T) -> Unit>>

    abstract fun onItemViewType(pos: Int, datas: List<T>): Int

    fun update(dataList: List<T>) {
        items = dataList
        notifyDataSetChanged()
    }

}
