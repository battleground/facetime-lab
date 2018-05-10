package com.bftv.facetime.lab

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent

class FaceTimeActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_face_time)

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.contentPanel, CallFragment())
                .commit()
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
