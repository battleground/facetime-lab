package com.bftv.facetime.lab

import android.view.KeyEvent

/**
 * Created by dayu on 2017/12/1.
 */
interface OnKeyEventEnable {

    fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean

    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean

    fun onBackPressed(): Boolean

}