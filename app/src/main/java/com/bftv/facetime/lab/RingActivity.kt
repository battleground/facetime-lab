package com.bftv.facetime.lab

import android.app.Instrumentation
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.KeyEvent
import com.abooc.util.Debug
import kotlinx.android.synthetic.main.facetime_calling_menu_v.*
import kotlinx.android.synthetic.main.facetime_calling_user.*

class RingActivity : AppCompatActivity() {

    private lateinit var aRing: Ring

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ring)
        aRing = Ring(this)

        fullScreenTextV1.text = "呼叫"
        fullScreenV1?.setOnClickListener { v ->
            v.isSelected = !v.isSelected

            if (v.isSelected) {
                fullScreenTextV1.text = "取消呼叫"
                startAnim()

                aRing.run()
            } else {
                fullScreenTextV1.text = "呼叫"
                stopAnim()

                aRing.stop()
            }
        }

        muteVideoTextV1.text = "播放错误"
        muteVideoV1?.setOnClickListener { v ->
            v.isSelected = !v.isSelected

            if (v.isSelected) {
                muteVideoTextV1.text = "停止播放"
                aRing.error()
            } else {
                muteVideoTextV1.text = "播放错误"
                aRing.stop()
            }

        }

        muteAudioTextV1.text = "发送'MENU'"
        muteAudioV1?.setOnClickListener {
            sendKeyEvent()
            return@setOnClickListener
        }

    }

    private fun sendKeyEvent() {
        Thread(Runnable {
            val inst = Instrumentation()
            inst.sendKeyDownUpSync(KeyEvent.KEYCODE_MENU)

        }).start()

    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            Debug.error()
        }

        return super.onKeyDown(keyCode, event)
    }

    val animHandler = Handler()

    fun startAnim() {
        Debug.anchor("++++++++++++")
        animHandler.postDelayed(run1, 100L)
        animHandler.postDelayed(run2, 900L)
        animHandler.postDelayed(run3, 1800L)
    }

    fun stopAnim() {
        Debug.anchor("------------")

        animHandler.removeCallbacksAndMessages(null)

        imageV1.clearAnimation()
        imageV2.clearAnimation()
        imageV3.clearAnimation()
    }

    var run1: () -> Unit = { imageV1.startAnimation(Anim.createAnimationSet()) }
    var run2: () -> Unit = { imageV2.startAnimation(Anim.createAnimationSet()) }
    var run3: () -> Unit = { imageV3.startAnimation(Anim.createAnimationSet()) }


}
