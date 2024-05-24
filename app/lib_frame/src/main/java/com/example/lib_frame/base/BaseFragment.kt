package com.example.lib_frame.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.viewbinding.ViewBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_frame.controller.ToolbarController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.Serializable

abstract class BaseFragment<B : ViewBinding>(private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> ViewBinding) :
    Fragment() {

    lateinit var mActivity: BaseActivity<*>

    val mHandler
        get() = mActivity.mHandler

    val mViewModel: BaseViewModel
        get() = mActivity.mViewModel

    val mBundle: Bundle by lazy {
        Bundle().apply {
            arguments?.let { putAll(it) }
        }
    }

    lateinit var binding: B

    val mToolbarController by lazy(LazyThreadSafetyMode.NONE) {
        ToolbarController(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as BaseActivity<*>
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        setHasOptionsMenu(true)
        binding = inflate.invoke(inflater, container, false) as B
        initView()
        request { initData() }
        return binding.root
    }

    abstract fun initView()

    abstract suspend fun initData()

    open fun onBackPressed() {
        finish()
    }

    fun <VM : ViewModel> viewModels(cls: Class<VM>): VM = mActivity.viewModels(cls)

    fun goto(cls: Class<out BaseFragment<out ViewBinding>>, serializable: Serializable?) {
        mActivity.goto(cls, serializable)
    }

    fun goto(cls: Class<out BaseFragment<out ViewBinding>>, bundle: Bundle = Bundle()) {
        mActivity.goto(cls, bundle)
    }

    fun goto(clsName: String, serializable: Serializable?) {
        mActivity.goto(clsName, serializable)
    }

    fun goto(clsName: String, bundle: Bundle = Bundle()) {
        mActivity.goto(clsName, bundle)
    }

    fun finish(bundle: Bundle? = null) {
        mActivity.finish(bundle)
    }

    fun request(block: suspend CoroutineScope.() -> Unit) {
        mViewModel.viewModelScope.launch {
            block.invoke(this)
        }
    }

    open fun onActivityResult(it: ActivityResult?) {

    }
}
