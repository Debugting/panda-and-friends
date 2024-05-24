package com.example.mod_paper.fragment

import android.view.View
import com.chad.library.adapter4.BaseQuickAdapter
import com.example.lib_frame.base.BaseListFragment
import com.example.lib_frame.base.BaseRcvAdapter
import com.example.mod_paper.adapter.PaperRcvAdapter
import com.example.mod_paper.dao.Paper
import com.example.mod_paper.dao.PaperDataManager
import com.example.mod_social.social.SocialDetailsFragment
import com.example.mod_user.dao.UserDataManager

open class PaperListFragment : BaseListFragment<Paper>() {

    private var isCollected = false
    private var isMine = false

    override fun getAdapter(): BaseRcvAdapter<Paper> {
        return PaperRcvAdapter()
    }

    override suspend fun initData() {
        isCollected = mBundle.getBoolean("isCollected")
        isMine = mBundle.getBoolean("isMine")
        val typeId = mBundle.getLong("typeId")
        PaperDataManager.getPapers(
            mutableMapOf(
                "paperTypeId" to typeId,
            ).apply {
                if (isMine) {
                    put("userId", UserDataManager.loginUserId)
                }
            }, isCollected
        ).apply {
            updateData(this)
        }
    }

    override fun onClick(adapter: BaseQuickAdapter<Paper, *>, view: View, position: Int) {
        adapter.getItem(position)?.let {
            goto(SocialDetailsFragment::class.java, it)
        }
    }
}
