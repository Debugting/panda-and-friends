package com.example.mod_social.social

import android.annotation.SuppressLint
import android.view.MotionEvent
import android.view.View
import androidx.core.view.isVisible
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.utils.DisplayUtils
import com.example.lib_frame.utils.RegexUtils
import com.example.lib_frame.utils.ShareUtil
import com.example.mod_social.R
import com.example.mod_social.databinding.SocialDetailsFragmentBinding
import com.example.mod_social.comment.CommentDialog
import com.example.mod_social.slike.BitmapProvider
import com.example.mod_user.dao.UserDataManager

class SocialDetailsFragment : BaseFragment<SocialDetailsFragmentBinding>(SocialDetailsFragmentBinding::inflate) {

    private lateinit var mCommentDialog: CommentDialog
    private lateinit var mData: SocialBean<*>
    private var mPraised: Praised? = null
    private var mCollected: Collected? = null

    override fun initView() {
        mToolbarController.initTitle("详情")
        mData = mBundle.getSerializable("bean") as SocialBean<*>
        initWebView()
        initBottomView()
        addVisit()
    }

    override suspend fun initData() {
        if (mBundle.getBoolean("hideBottom")) {
            binding.llBottom.isVisible = false
        }
        //获取点赞状态
        mPraised = mData.mPraised
        binding.btnPraised.isSelected = mPraised != null

        //获取收藏状态
        mCollected = mData.mCollected
        binding.btnCollected.isSelected = mCollected != null

        //获取评论列表
        request {
            mCommentDialog.updateData(
                SocialDataManager.getComment(mData.dataType(), mData.id)
            )
        }
    }

    private fun addVisit() {
        request {
            VisitedDataManager.insert(Visited().apply {
                outId = mData.id
                userId = UserDataManager.loginUserId
                dataType = mData.dataType()
            })
        }
    }

    private fun initWebView() {
        val webFragment: WebContentFragment = WebContentFragment.newInstance(mData)
        childFragmentManager.beginTransaction().add(R.id.mWebContent, webFragment).commit()
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initBottomView() {
        mCommentDialog = CommentDialog(mActivity) { adapter, comment, word, pos ->
            request {
                Comment().apply {
                    this.word = word
                    userId = UserDataManager.loginUserId
                    if (comment == null) {
                        outId = mData.id
                        dataType = mData.dataType()
                    } else {
                        outId = if (comment.replyUserId == 0L) {
                            comment.id
                        } else {
                            comment.outId
                        }
                        dataType = Comment.COMMENT_TYPE
                        replyUserId = comment.userId
                    }
                    CommentDataManager.insert(this)?.apply {
                        user = UserDataManager.loginUser
                        if (comment == null) {
                            adapter.add(0, this)
                            adapter.recyclerView.smoothScrollBy(0, -Int.MAX_VALUE)
                        } else {
                            replyUser = comment.user
                            adapter.add(pos + 1, this)
                        }
                    }
                }
            }
        }
        binding.llInput.setOnClickListener { mCommentDialog.show() }
        binding.mSuperLikeLayout.provider = BitmapProvider.Builder(mActivity).setDrawableArray(
            intArrayOf(
                R.mipmap.praised_emoji_1,
                R.mipmap.praised_emoji_2,
                R.mipmap.praised_emoji_3,
                R.mipmap.praised_emoji_4,
                R.mipmap.praised_emoji_5,
                R.mipmap.praised_emoji_6,
                R.mipmap.praised_emoji_7,
                R.mipmap.praised_emoji_8,
                R.mipmap.praised_emoji_9,
                R.mipmap.praised_emoji_10
            )
        ).setNumberDrawableArray(
            intArrayOf(
                R.mipmap.multi_digg_num_0,
                R.mipmap.multi_digg_num_1,
                R.mipmap.multi_digg_num_2,
                R.mipmap.multi_digg_num_3,
                R.mipmap.multi_digg_num_4,
                R.mipmap.multi_digg_num_5,
                R.mipmap.multi_digg_num_6,
                R.mipmap.multi_digg_num_7,
                R.mipmap.multi_digg_num_8,
                R.mipmap.multi_digg_num_9
            )
        ).setLevelDrawableArray(
            intArrayOf(
                R.mipmap.multi_digg_word_level_1,
                R.mipmap.multi_digg_word_level_2,
                R.mipmap.multi_digg_word_level_3
            )
        ).setIconSize(DisplayUtils.dp2px(20f).toFloat()).build()
        binding.btnPraised.setOnTouchListener(object : View.OnTouchListener {
            var isPlaying = false
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> {
                        request {
                            if (v.isSelected) {
                                mPraised?.apply {
                                    PraisedDataManager.delete(this)?.apply {
                                        mPraised = null
                                        binding.btnPraised.isSelected = false
                                    }
                                }
                            } else {
                                isPlaying = true
                                play()
                                PraisedDataManager.insert(Praised().apply {
                                    outId = mData.id
                                    userId = UserDataManager.loginUserId
                                    dataType = mData.dataType()
                                })?.apply {
                                    mPraised = this
                                    binding.btnPraised.isSelected = true
                                }
                            }
                        }
                    }

                    MotionEvent.ACTION_UP -> isPlaying = false
                }
                return true
            }

            private fun play() {
                mHandler.postDelayed({
                    binding.mSuperLikeLayout.launch(
                        (binding.btnPraised.x + binding.btnPraised.width / 2).toInt(),
                        binding.mSuperLikeLayout.height - DisplayUtils.dp2px(65f)
                    )
                    if (isPlaying) {
                        play()
                    }
                }, 150)
            }
        })
        binding.btnCollected.setOnClickListener { v ->
            request {
                if (v.isSelected) {
                    mCollected?.apply {
                        CollectedDataManager.delete(this)?.apply {
                            mCollected = null
                            binding.btnCollected.isSelected = false
                        }
                    }
                } else {
                    CollectedDataManager.insert(Collected().apply {
                        outId = mData.id
                        userId = UserDataManager.loginUserId
                        dataType = mData.dataType()
                    })?.apply {
                        mCollected = this
                        binding.btnCollected.isSelected = true
                    }
                }
            }
        }
        binding.ivShare.setOnClickListener {
            ShareUtil.shareText(mData.let {
                if (RegexUtils.checkURL(mData.content)) {
                    mData.title + "\n" + mData.content
                } else {
                    it.title
                }
            })
        }
    }
}
