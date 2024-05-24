package com.example.lib_frame.base

import androidx.viewbinding.ViewBinding
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class BasePaperAdapter(
    fragment: Fragment,
) : FragmentStateAdapter(fragment) {

    private val mFragments = mutableListOf<BaseFragment<out ViewBinding>>()

    override fun createFragment(position: Int): Fragment {
        return mFragments[position]
    }

    override fun getItemCount(): Int {
        return mFragments.size
    }

    fun update(fragments: List<BaseFragment<out ViewBinding>>) {
        mFragments.clear()
        mFragments.addAll(fragments)
        notifyDataSetChanged()
    }

}
