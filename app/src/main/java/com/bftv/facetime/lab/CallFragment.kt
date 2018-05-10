package com.bftv.facetime.lab

import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.*
import com.abooc.util.Debug
import kotlinx.android.synthetic.main.facetime_calling_menu.*
import kotlinx.android.synthetic.main.facetime_calling_user.*

/**
 * Created by dayu on 2017/9/1.
 */
class CallFragment : Fragment(), OnKeyEventEnable {

    var run1: () -> Unit = { imageV1.startAnimation(Anim.createAnimationSet()) }
    var run2: () -> Unit = { imageV2.startAnimation(Anim.createAnimationSet()) }
    var run3: () -> Unit = { imageV3.startAnimation(Anim.createAnimationSet()) }

    val animHandler = Handler()
    private val timerDelayer = Handler()
    private lateinit var aRing: Ring

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_call, container, false)
    }

    var start = 0L
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        aRing = Ring(activity)

        aRing.run()
        startAnim()

        holdOnV1.setOnClickListener {
            fragmentManager
                    .beginTransaction()
                    .replace(R.id.contentPanel, ConnectingFragment())
                    .commit()
        }
        hungOffV1.setOnClickListener { activity.finish() }
    }


    fun error() {
        timerDelayer.postDelayed({
            aRing.error()
        }, 800)
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return false
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        stopAnim()
        aRing.stop()

    }


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
}

class Anim {
    companion object {

        fun createAnimationSet(): AnimationSet {
            val spaceTime = 3000

            val set = AnimationSet(true)
            val rangeSize = 2.0f
            val sa = ScaleAnimation(
                    1f, rangeSize,
                    1f, rangeSize,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f,
                    ScaleAnimation.RELATIVE_TO_SELF, 0.5f)

            sa.duration = spaceTime.toLong()
            sa.repeatCount = Animation.INFINITE // 设置循环

            val aa = AlphaAnimation(1f, 0.0f)
            aa.duration = spaceTime.toLong()
            aa.interpolator = DecelerateInterpolator(4.0f)
            aa.repeatCount = Animation.INFINITE //设置循环

            set.addAnimation(sa)
            set.addAnimation(aa)
            return set
        }

    }
}