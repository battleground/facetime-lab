package com.bftv.facetime


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

import java.util.ArrayList

class FragmentAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val mTabs = ArrayList<Tab>()

    class Tab @JvmOverloads constructor(var name: String, var fragment: Class<out Fragment>, var args: Bundle? = null)

    fun addTab(tab: Tab): FragmentAdapter {
        mTabs.add(tab)
        return this
    }

    override fun getItem(position: Int): Fragment {
        val tab = getTab(position)
        return Fragment.instantiate(mContext, tab.fragment.name, mTabs[position].args)
    }

    fun getTab(position: Int): Tab {
        return mTabs[position]
    }

    override fun getCount(): Int {
        return mTabs.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return mTabs[position].name
    }
}