package com.example.mod_social.banner

import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.extensions.load
import com.example.mod_social.R
import com.example.mod_social.databinding.BannerImageBrowserFragmentBinding
import com.luck.picture.lib.photoview.PhotoView

class BrowserImageFragment :
    BaseFragment<BannerImageBrowserFragmentBinding>(BannerImageBrowserFragmentBinding::inflate) {

    override fun initView() {
        mToolbarController.initTitle("图片查看")
        val imgPath: String = mBundle.getString("imgPath") ?: ""
        val imagePaths: List<String> = mBundle.getStringArrayList("imgPaths") ?: listOf()
        val vpAdapter = BrowserImageVpAdapter(imagePaths)
        binding.viewPager.setAdapter(vpAdapter)
        imagePaths.onEachIndexed { index, s ->
            if (TextUtils.equals(s, imgPath)) {
                binding.viewPager.setCurrentItem(index, false)
            }
        }
    }

    override suspend fun initData() {

    }

    class BrowserImageVpAdapter(private val imgPaths: List<String>) :
        RecyclerView.Adapter<BrowserImageVpAdapter.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(
                R.layout.banner_browser_image_vp_item, parent, false
            )
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.photoView.load(imgPaths[position])
        }

        override fun getItemCount(): Int {
            return imgPaths.size
        }

        class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            var photoView: PhotoView

            init {
                photoView = itemView.findViewById(R.id.mPhotoView)
            }
        }
    }
}
