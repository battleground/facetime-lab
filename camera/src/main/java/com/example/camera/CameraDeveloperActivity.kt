package com.example.camera

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class CameraDeveloperActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(android.R.id.content, DeveloperFragment()).commitNowAllowingStateLoss()


    }
}
