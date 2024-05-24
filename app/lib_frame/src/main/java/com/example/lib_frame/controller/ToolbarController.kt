package com.example.lib_frame.controller

import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.viewbinding.ViewBinding
import com.example.lib_frame.R
import com.example.lib_frame.base.BaseFragment
import com.example.lib_frame.extensions.expandClickArea
import com.example.lib_frame.extensions.getString
import com.example.lib_frame.widgets.IconFontImageView
import com.example.lib_frame.widgets.search.MaterialSearchView
import com.example.lib_frame.widgets.search.MaterialSearchView.OnQueryTextListener

class ToolbarController(val fragment: BaseFragment<out ViewBinding>) {

    fun initTitle(
        title: String,
        btnLeft: Pair<String, () -> Unit>? = Pair(fragment.getString(R.string.ic_arrow_right)) {
            fragment.onBackPressed()
        }
    ) {
        fragment.binding.root.apply {
            findViewById<ConstraintLayout>(R.id.toolbar).isVisible = true
            findViewById<TextView>(R.id.tvTitle).text = title
            btnLeft.apply {
                findViewById<TextView>(R.id.btnLeft).let {
                    if (this == null) {
                        it.isVisible = false
                    } else {
                        it.text = first
                        it.expandClickArea(10)
                        it.setOnClickListener { second.invoke() }
                    }
                }
            }

        }
    }

    fun initRight(btnRight: Pair<String, () -> Unit>) {
        fragment.binding.root.apply {
            findViewById<IconFontImageView>(R.id.btnRight).let {
                it.isVisible = true
                it.text = btnRight.first
                it.expandClickArea(10)
                it.setOnClickListener { btnRight.second.invoke() }
            }
        }
    }

    fun initSearch(updateData: (String) -> Unit) {
        fragment.binding.root.apply {
            fragment.binding.root.apply {
                val searchView = findViewById<MaterialSearchView>(R.id.searchView).apply {
                    isVisible = true
                    setOnQueryTextListener(object : OnQueryTextListener {
                        override fun onQueryTextSubmit(query: String?): Boolean {
                            return false
                        }

                        override fun onQueryTextChange(newText: String): Boolean {
                            updateData(newText)
                            return false
                        }
                    })
                }
                findViewById<IconFontImageView>(R.id.btnRight).apply {
                    isVisible = true
                    text = getString(R.string.ic_search)
                    expandClickArea(10)
                    setOnClickListener {
                        searchView.showSearch()
                    }
                }
            }

        }
    }
}
