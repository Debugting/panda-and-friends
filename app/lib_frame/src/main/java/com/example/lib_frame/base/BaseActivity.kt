package com.example.lib_frame.base

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.viewbinding.ViewBinding
import com.example.lib_frame.bean.ThemeBean
import com.example.lib_frame.theme.ThemeManager
import kotlinx.coroutines.launch
import java.io.Serializable


abstract class BaseActivity<D : ViewBinding>(private val inflate: (LayoutInflater) -> ViewBinding) :
    AppCompatActivity() {

    val mActivity by lazy(LazyThreadSafetyMode.NONE) {
        this
    }

    val mHandler by lazy(LazyThreadSafetyMode.NONE) {
        Handler(Looper.getMainLooper())
    }

    val mViewModel: BaseViewModel by lazy {
        viewModels(BaseViewModel::class.java)
    }

    val mBundle: Bundle by lazy {
        intent.getBundleExtra("bundle") ?: Bundle()
    }

    val mBinding: D by lazy(LazyThreadSafetyMode.NONE) {
        inflate.invoke(layoutInflater) as D
    }

    lateinit var mLauncher: ActivityResultLauncher<Intent>

    private var mTheme: ThemeBean = ThemeManager.curTheme

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preSetContentView()
        setTheme(mTheme.theme)
        setContentView(mBinding.root)
        initView()
        mViewModel.viewModelScope.launch {
            initData()
        }
        mLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            onActivityResult(it)
        }
    }

    override fun onRestart() {
        super.onRestart()
        val themeBean: ThemeBean = ThemeManager.curTheme
        if (mTheme != themeBean) {
            mTheme = themeBean
            recreate()
        }
    }

    open fun preSetContentView() {
    }

    abstract fun initView()

    abstract suspend fun initData()

    fun <VM : ViewModel> viewModels(cls: Class<VM>): VM = ViewModelProvider(this).get(cls)

    fun goto(cls: Class<out BaseFragment<out ViewBinding>>, serializable: Serializable?) {
        goto(cls, Bundle().apply { serializable?.let { putSerializable("bean", it) } })
    }

    fun goto(cls: Class<out BaseFragment<out ViewBinding>>, bundle: Bundle = Bundle()) {
        mLauncher.launch(if (BaseFragment::class.java.isAssignableFrom(cls)) {
            Intent(this, HostActivity::class.java).apply {
                putExtra("bundle", bundle.apply {
                    putString("clsName", cls.name)
                })
            }
        } else {
            Intent(this, cls).apply {
                putExtra("bundle", bundle)
            }
        })
    }

    fun goto(clsName: String, serializable: Serializable?) {
        goto(clsName, Bundle().apply { serializable?.let { putSerializable("bean", it) } })
    }

    fun goto(clsName: String, bundle: Bundle = Bundle()) {
        mLauncher.launch(Intent(this, HostActivity::class.java).apply {
            putExtra("bundle", bundle.apply {
                putString("clsName", clsName)
            })
        })
    }

    fun finish(bundle: Bundle?) {
        bundle?.apply {
            Intent().apply {
                putExtra("bundle", bundle)
                setResult(RESULT_OK, this)
            }
        }
        finish()
    }

    open fun onActivityResult(it: ActivityResult?) {

    }
}