package com.example.mod_paper.fragment

import android.app.Activity.RESULT_OK
import androidx.activity.result.ActivityResult
import androidx.core.view.isGone
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.utils.ToastUtils
import com.example.mod_paper.dao.Paper
import com.example.mod_paper.dao.PaperDataManager
import com.example.mod_paper.dao.PaperTypeDataManager
import com.example.mod_paper.databinding.PaperEditFragmentBinding
import com.example.mod_social.rich.RichEditFragment
import com.example.mod_user.dao.UserDataManager

class PaperEditFragment :
    BaseFragment<PaperEditFragmentBinding>(PaperEditFragmentBinding::inflate) {

    private lateinit var paper: Paper

    override fun initView() {
        mToolbarController.initTitle("编辑")
        binding.apply {
            mulPicSelectView.setMaxImgNum(3)
            btnSubmit.setOnClickListener {
                paper.title = binding.etTitle.text.toString()
                paper.word = binding.etWord.text.toString()
                mulPicSelectView.getData().onEachIndexed { index, s ->
                    when (index) {
                        0 -> paper.imgUrl = s
                        1 -> paper.imgUrl2 = s
                        2 -> paper.imgUrl3 = s
                    }
                }
                if (paper.title.isEmpty() || paper.word.isEmpty()) {
                    ToastUtils.info()
                } else {
                    goto(RichEditFragment::class.java)
                }
            }
        }
    }

    override suspend fun initData() {
        mBundle.apply {
            getSerializable("bean").let {
                paper = if (it is Paper) {
                    it
                } else {
                    Paper()
                }
            }
        }
        binding.apply {
            paper.apply {
                etTitle.setText(title)
                etWord.setText(word)
                val paths = mutableListOf<String>()
                if (imgUrl.isNotEmpty()) {
                    paths.add(imgUrl)
                }
                if (imgUrl2.isNotEmpty()) {
                    paths.add(imgUrl2)
                }
                if (imgUrl3.isNotEmpty()) {
                    paths.add(imgUrl3)
                }
                binding.mulPicSelectView.updateData(paths)
            }
        }

        val typeId = mBundle.getLong("typeId")
        val dataType = mBundle.getString("dataType")

        if (typeId == 0L) {
            request {
                PaperTypeDataManager.getAll(mutableMapOf<String, Any>().apply {
                    dataType?.let { put("dataType", it) }
                })
            }
        } else {
            paper.paperTypeId = typeId
            binding.llType.isGone = true
        }
    }

    override fun onActivityResult(it: ActivityResult?) {
        super.onActivityResult(it)
        it?.apply {
            if (resultCode == RESULT_OK) {
                paper.content = data?.getBundleExtra("bundle")?.getString("content") ?: ""
                request {
                    paper.userId = UserDataManager.loginUserId
                    PaperDataManager.insert(paper)?.apply {
                        ToastUtils.success()
                        finish()
                    }
                }
            }
        }
    }
}
