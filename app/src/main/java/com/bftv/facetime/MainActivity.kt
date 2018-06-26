package com.bftv.facetime.lab

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import android.view.View
import com.bftv.facetime.AddressFragment
import com.bftv.facetime.FragmentAdapter
import com.bftv.facetime.FragmentAdapter.Tab
import com.bftv.facetime.RecordsFragment
import com.bftv.facetime.SettingsFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var mAdapter: FragmentAdapter = FragmentAdapter(this, supportFragmentManager)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAdapter.addTab(Tab(getString(R.string.mainTab1Text), RecordsFragment::class.java))
        mAdapter.addTab(Tab(getString(R.string.mainTab2Text), AddressFragment::class.java))
        mAdapter.addTab(Tab(getString(R.string.mainTab3Text), SettingsFragment::class.java))
        tab1.onFocusChangeListener = onFocusEvent
        tab2.onFocusChangeListener = onFocusEvent
        tab3.onFocusChangeListener = onFocusEvent
        tab1.setOnClickListener { v -> onFocusEvent.onFocusChange(v, true) }
        tab2.setOnClickListener { v -> onFocusEvent.onFocusChange(v, true) }
        tab3.setOnClickListener { v -> onFocusEvent.onFocusChange(v, true) }
        tab1.performClick()
        tabs.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                if (tab1.isSelected) {
                    tab1.requestFocus()
                } else if (tab2.isSelected) {
                    tab2.requestFocus()
                } else {
                    tab3.requestFocus()
                }
            }
        }

    }

    private var onFocusEvent = View.OnFocusChangeListener { v, hasFocus ->
        if (hasFocus && !v.isSelected) {
            when (v) {
                tab1 -> {
                    tab2.isSelected = false
                    tab3.isSelected = false
                    tab1.isSelected = true
                    show(mAdapter.getTab(0))
                }
                tab2 -> {
                    tab1.isSelected = false
                    tab3.isSelected = false
                    tab2.isSelected = true
                    show(mAdapter.getTab(1))
                }
                tab3 -> {
                    tab1.isSelected = false
                    tab2.isSelected = false
                    tab3.isSelected = true
                    show(mAdapter.getTab(2))
                }
            }
        }
    }

    private var shownFragment: Fragment? = null

    private fun show(tab: Tab) {
        val transaction = supportFragmentManager.beginTransaction()
        if (shownFragment != null) {
            transaction.hide(shownFragment)
        }
        shownFragment = supportFragmentManager.findFragmentByTag(tab.name)
        if (shownFragment == null) {
            shownFragment = Fragment.instantiate(this, tab.fragment.name, tab.args)
            transaction.add(R.id.fragmentPanel, shownFragment, tab.name)
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.show(shownFragment).commitAllowingStateLoss()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        var fragment = supportFragmentManager.findFragmentById(R.id.contentPanel)
        if (fragment is OnKeyEventEnable) {
            val b = (fragment!! as OnKeyEventEnable).onKeyDown(keyCode, event)
            return if (b) true else super.onKeyDown(keyCode, event)
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onBackPressed() {
        var fragment = supportFragmentManager.findFragmentById(R.id.contentPanel)
        if ((fragment is OnKeyEventEnable)
                && (fragment as OnKeyEventEnable).onBackPressed()) {
        } else {
            super.onBackPressed()
        }
    }

}
