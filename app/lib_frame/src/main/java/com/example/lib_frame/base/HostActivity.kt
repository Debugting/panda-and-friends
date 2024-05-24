package com.example.lib_frame.base

import androidx.activity.result.ActivityResult
import androidx.viewbinding.ViewBinding
import com.example.lib_frame.R
import com.example.lib_frame.databinding.ActivityHostBinding

class HostActivity : BaseActivity<ActivityHostBinding>(ActivityHostBinding::inflate) {

    private lateinit var mFragment: BaseFragment<out ViewBinding>

    override fun initView() {
        try {
            val clsName: String = mBundle.getString("clsName") ?: ""
            val cls = Class.forName(clsName)
            mFragment = cls.newInstance() as BaseFragment<out ViewBinding>
            mFragment.mBundle.putAll(mBundle)
            supportFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit()
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        }
    }

    override suspend fun initData() {
    }

    override fun onBackPressed() {
        mFragment.onBackPressed()
    }

    override fun onActivityResult(it: ActivityResult?) {
        super.onActivityResult(it)
        mFragment.onActivityResult(it)
    }
}