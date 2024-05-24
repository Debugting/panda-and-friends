package com.example.mod_social.rich

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.manager.ConfigManager
import com.example.lib_frame.manager.UploadDataManager
import com.example.lib_frame.utils.DialogUtils
import com.example.lib_frame.utils.KeyBoardUtils
import com.example.lib_frame.utils.MediaUtils
import com.example.lib_frame.utils.RichUtils
import com.example.mod_social.R
import com.example.mod_social.databinding.RichEditFragmentBinding
import com.luck.picture.lib.entity.LocalMedia
import com.luck.picture.lib.interfaces.OnResultCallbackListener

class RichEditFragment : BaseFragment<RichEditFragmentBinding>(RichEditFragmentBinding::inflate),
    View.OnClickListener {

    override fun initView() {
        mToolbarController.apply {
            initTitle("详情")
            initRight(Pair("保存", ::submit))
        }
        var content: String = mBundle.getString("content") ?: ""
        if (ConfigManager.isOpenNet) {
            val baseUrl: String = ConfigManager.baseUrl
            val imageUrls = RichUtils.returnImageUrlsFromHtml(content)
            val audioUrls = RichUtils.returnAudioUrlsFromHtml(content)
            val videoUrls = RichUtils.returnVideoUrlsFromHtml(content)
            for (i in imageUrls.indices) {
                content = content.replace(imageUrls[i], baseUrl + imageUrls[i])
            }
            for (i in audioUrls.indices) {
                content = content.replace(imageUrls[i], baseUrl + audioUrls[i])
            }
            for (i in videoUrls.indices) {
                content = content.replace(videoUrls[i], baseUrl + videoUrls[i])
            }
        }
        content = HTML_STYLE + content
        binding.richEditor.html = content
        initEditor()
    }

    override suspend fun initData() {

    }

    private fun initEditor() {
        binding.apply {
            buttonImage.setOnClickListener(this@RichEditFragment)
            buttonAudio.setOnClickListener(this@RichEditFragment)
            buttonVideo.setOnClickListener(this@RichEditFragment)
            buttonBold.setOnClickListener(this@RichEditFragment)
            buttonUnderline.setOnClickListener(this@RichEditFragment)
            buttonItalic.setOnClickListener(this@RichEditFragment)
            buttonStrike.setOnClickListener(this@RichEditFragment)
            buttonRichUndo.setOnClickListener(this@RichEditFragment)
            buttonRichDo.setOnClickListener(this@RichEditFragment)
            richEditor.apply {
                //输入框显示字体的大小
                setEditorFontSize(18)
                //输入框显示字体的颜色
                setEditorFontColor(Color.parseColor("#333333"))
                //输入框背景设置
                setEditorBackgroundColor(Color.WHITE)
                //输入框文本padding
                setPadding(10, 10, 10, 10)
                //输入提示文本
                setPlaceholder("请填写内容吧！")
                setInputEnabled(true)
                setOnDecorationChangeListener(object : RichEditor.OnDecorationStateListener {
                    override fun onStateChangeListener(
                        text: String?,
                        types: List<RichEditor.Type>?
                    ) {
                        val flagArr = ArrayList<String>()
                        types?.onEach {
                            flagArr.add(it.name)
                        }
                        binding.buttonBold.isSelected = flagArr.contains("BOLD")
                        binding.buttonUnderline.isSelected = flagArr.contains("UNDERLINE")
                        binding.buttonItalic.isSelected = flagArr.contains("ITALIC")
                        binding.buttonStrike.isSelected = flagArr.contains("STRIKETHROUGH")
                    }

                })
            }
        }
    }

    private fun submit() {
        KeyBoardUtils.hideSoftInput()
        DialogUtils.askDialog("是否保存？", {
            var content: String = binding.richEditor.html ?: ""
            content = content.replace(HTML_STYLE, "")
            if (ConfigManager.isOpenNet) {
                val baseUrl: String = ConfigManager.baseUrl
                content = content.replace(baseUrl, "")
            }
            val bundle = Bundle()
            bundle.putString("content", content)
            finish(bundle)
        })
    }

    @SuppressLint("NonConstantResourceId")
    override fun onClick(v: View) {
        when (v.id) {
            R.id.button_image -> {
                MediaUtils.choiceImage(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>) {
                        val path: String = result[0].cutPath
                        request {
                            UploadDataManager.uploadImage(path)?.let {
                                binding.richEditor.insertImage(if (ConfigManager.isOpenNet) "${ConfigManager.baseUrl}/fileImage/watch?path=$it" else it)
                            }
                        }
                        KeyBoardUtils.openKeyBord(binding.richEditor)
                    }

                    override fun onCancel() {}
                })
                binding.richEditor.focusEditor()
                KeyBoardUtils.hideSoftInput()
            }

            R.id.button_audio -> {
                MediaUtils.choiceAudio(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>) {
                        val path: String = result[0].realPath
                        request {
                            UploadDataManager.uploadAudio(path)?.let {
                                binding.richEditor.insertAudio(if (ConfigManager.isOpenNet) "${ConfigManager.baseUrl}/fileAudio/watch?path=$it" else it)
                            }
                        }
                        KeyBoardUtils.openKeyBord(binding.richEditor)
                    }

                    override fun onCancel() {}
                })
                binding.richEditor.focusEditor()
                KeyBoardUtils.hideSoftInput()
            }

            R.id.button_video -> {
                MediaUtils.choiceVideo(object : OnResultCallbackListener<LocalMedia> {
                    override fun onResult(result: ArrayList<LocalMedia>) {
                        val path: String = result[0].realPath
                        request {
                            UploadDataManager.uploadVideo(path)?.let {
                                binding.richEditor.insertVideo(if (ConfigManager.isOpenNet) "${ConfigManager.baseUrl}/fileVideo/watch?path=$it" else it)
                            }
                        }
                        KeyBoardUtils.openKeyBord(binding.richEditor)
                    }

                    override fun onCancel() {}
                })
                binding.richEditor.focusEditor()
                KeyBoardUtils.hideSoftInput()
            }

            R.id.button_bold -> {
                binding.richEditor.setBold()
                binding.richEditor.focusEditor()
                KeyBoardUtils.openKeyBord(binding.richEditor)
                binding.buttonBold.setSelected(!binding.buttonBold.isSelected())
            }

            R.id.button_underline -> {
                binding.richEditor.setUnderline()
                binding.richEditor.focusEditor()
                KeyBoardUtils.openKeyBord(binding.richEditor)
                binding.buttonUnderline.setSelected(!binding.buttonUnderline.isSelected())
            }

            R.id.button_italic -> {
                binding.richEditor.setItalic()
                binding.richEditor.focusEditor()
                KeyBoardUtils.openKeyBord(binding.richEditor)
                binding.buttonItalic.setSelected(!binding.buttonItalic.isSelected())
            }

            R.id.button_strike -> {
                binding.richEditor.setStrikeThrough()
                binding.richEditor.focusEditor()
                KeyBoardUtils.openKeyBord(binding.richEditor)
                binding.buttonStrike.setSelected(!binding.buttonStrike.isSelected())
            }

            R.id.button_rich_undo -> {
                binding.richEditor.undo()
                binding.richEditor.focusEditor()
                KeyBoardUtils.openKeyBord(binding.richEditor)
            }

            R.id.button_rich_do -> {
                binding.richEditor.redo()
                binding.richEditor.focusEditor()
                KeyBoardUtils.openKeyBord(binding.richEditor)
            }
        }
    }

    override fun onBackPressed() {
        DialogUtils.askDialog("是否放弃编辑内容", {
            mActivity.finish()
        })
    }

    companion object {
        private const val HTML_STYLE =
            ("</Div><head><style>body{font-size:16px;max-width: 100%}</style>" + "<style>img{ max-width:100% !important;margin-top:0.2em;margin-bottom:0.2em}</style>" + "<style>audio{ width:100% !important;margin-top:0.2em;margin-bottom:0.2em}</style>" + "<style>video{ width:100% !important;margin-top:0.2em;margin-bottom:0.2em}</style>" + "<style>ul{ padding-left: 1em;margin-top:0em}</style>" + "<style>ol{ padding-left: 1.2em;margin-top:0em}</style>" + "</head>")
    }
}