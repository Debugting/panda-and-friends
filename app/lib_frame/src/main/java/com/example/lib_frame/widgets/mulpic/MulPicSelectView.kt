package com.example.lib_frame.widgets.mulpic

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_frame.R
import com.example.lib_frame.extensions.load
import com.example.lib_frame.utils.MediaUtils
import com.example.lib_frame.widgets.IconFontImageView
import com.example.lib_frame.widgets.mulpic.MulPicSelectView.MulPicSelectRcvAdapter.MulPicSelectViewHolder
import com.luck.picture.lib.decoration.GridSpacingItemDecoration
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener

class MulPicSelectView : RecyclerView {
    private val mRcvAdapter = MulPicSelectRcvAdapter()
    private var mMaxImgNum = 9

    constructor(context: Context) : super(context) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context, attrs, defStyleAttr
    ) {
        init(context)
    }

    private fun init(context: Context) {
        layoutManager = GridLayoutManager(context, 3)
        addItemDecoration(GridSpacingItemDecoration(3, 1, false))
        adapter = mRcvAdapter
    }

    fun setMaxImgNum(maxNum: Int) {
        mMaxImgNum = maxNum
    }

    fun updateData(paths: List<String>) {
        mRcvAdapter.mImgPaths.clear()
        mRcvAdapter.mImgPaths.addAll(paths)
        mRcvAdapter.notifyDataSetChanged()
    }

    fun getData(): List<String> {
        return mRcvAdapter.mImgPaths
    }

    private inner class MulPicSelectRcvAdapter : Adapter<MulPicSelectViewHolder>() {
        val mImgPaths = mutableListOf<String>()

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MulPicSelectViewHolder {
            val rootView: View =
                LayoutInflater.from(context).inflate(R.layout.mul_pic_select_rcv_item, null, false)
            return MulPicSelectViewHolder(rootView)
        }

        override fun onBindViewHolder(holder: MulPicSelectViewHolder, position: Int) {
            holder.apply {
                ivIcon.apply {
                    setOnClickListener {
                        MediaUtils.choiceImage(object : OnResultCallbackListener<LocalMedia> {
                            override fun onResult(result: ArrayList<LocalMedia>?) {
                                result?.onEach {
                                    mImgPaths.add(it.cutPath)
                                }
                                notifyDataSetChanged()
                            }

                            override fun onCancel() {
                            }
                        }, maxNum = mMaxImgNum - mImgPaths.size)
                    }
                }

                if (position == mImgPaths.size) {
                    ivIcon.load(R.mipmap.ic_add_photo)
                    btnDelete.isGone = true
                    return
                }
                val path = mImgPaths[position]
                ivIcon.load(path)
                btnDelete.apply {
                    isVisible = true
                    setOnClickListener {
                        mImgPaths.removeAt(position)
                        notifyDataSetChanged()
                    }
                }
            }
        }

        override fun getItemCount(): Int {
            return if (mImgPaths.size < mMaxImgNum) mImgPaths.size + 1 else mImgPaths.size
        }

        inner class MulPicSelectViewHolder(itemView: View) : ViewHolder(itemView) {
            val ivIcon: ImageView
            val btnDelete: IconFontImageView

            init {
                ivIcon = itemView.findViewById(R.id.iv_icon)
                btnDelete = itemView.findViewById(R.id.btnDelete)
            }
        }
    }
}
