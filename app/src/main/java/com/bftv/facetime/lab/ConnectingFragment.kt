package com.bftv.facetime.lab

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import kotlinx.android.synthetic.main.activity_face_time.*
import kotlinx.android.synthetic.main.facetime_calling_menu_v.*
import kotlinx.android.synthetic.main.fragment_connecting.*

/**
 * Created by dayu on 2017/9/1.
 */
class ConnectingFragment : Fragment(), OnKeyEventEnable {

    private val fullScreenParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT)
    private var mOriginParams: FrameLayout.LayoutParams? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_connecting, container, false)
    }

    var start = 0L

    var shadowTop = 0
    var shadowBottom = 0
    var shadowLeft = 0
    var shadowRight = 0
    var timeTextBottom = 0
    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity.aiFuncView?.visibility = View.GONE
        mOriginParams = videoBigLayout.layoutParams as FrameLayout.LayoutParams
        var view = videoBigLayout
        shadowTop = view.paddingTop
        shadowBottom = view.paddingBottom
        shadowLeft = view.paddingLeft
        shadowRight = view.paddingRight
        timeTextBottom = (timeText.layoutParams as FrameLayout.LayoutParams).bottomMargin


        fullScreenV1.requestFocus()
        fullScreenV1.setOnClickListener {
            if (!isFullScreen()) {
                setFullScreen()
                hideMenu()
            } else {
                setSmallScreen()
            }
        }
    }

    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return false
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (keyCode == KeyEvent.KEYCODE_MENU && isFullScreen()) {
            if (isMenuShown()) {
                hideMenu()
            } else {
                showMenu()
            }
            true
        } else {
            false
        }
    }


    private fun isFullScreen(): Boolean {
        return videoBigLayout.top == 0 && videoBigLayout.left == 0
    }

    private fun isMenuShown(): Boolean {
        return menuVertical.visibility == View.VISIBLE
    }

    private fun showMenu() {
        menuVertical.visibility = View.VISIBLE
        fullScreenV1.requestFocus()
    }

    private fun hideMenu() {
        menuVertical.visibility = View.GONE
    }

    private fun setFullScreen() {
        videoBigLayout.layoutParams = fullScreenParams
        videoBigLayout.postDelayed({
            var p = timeText.layoutParams as FrameLayout.LayoutParams
            p.bottomMargin = 24
            timeText.layoutParams = p
            videoBigLayout.setPadding(0, 0, 0, 0)
        }, 500)
    }

    private fun setSmallScreen() {
        videoBigLayout.layoutParams = mOriginParams
        videoBigLayout.setPadding(shadowLeft, shadowTop, shadowRight, shadowBottom)
        var p = timeText.layoutParams as FrameLayout.LayoutParams
        p.bottomMargin = timeTextBottom
        timeText.layoutParams = p
    }

    override fun onBackPressed(): Boolean {
        return if (isFullScreen()) {
            setSmallScreen()
            showMenu()
            true
        } else {
            false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

}
