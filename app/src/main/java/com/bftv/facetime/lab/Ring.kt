package com.bftv.facetime.lab

import android.content.Context
import android.media.AudioManager
import android.media.SoundPool
import com.abooc.util.Debug

/**
 * 拨打电话铃声
 *
 * @author dayu
 */
class Ring(val context: Context) {

    private val ring_phone = R.raw.phonering
    private val ring_busy = R.raw.ring_busy
    private val ring_connect_error = R.raw.ring_connect_error

    private var mSoundPool: SoundPool = SoundPool(5, AudioManager.STREAM_RING, 5)
    private var loadedId: Int = 0
    private var streamId: Int = 0
    private var priority: Int = 1


    fun run() {
        loadedId = mSoundPool.load(context.applicationContext, ring_phone, priority)
        mSoundPool.setOnLoadCompleteListener { _, _, _ ->
            Debug.error()
            playSound()
        }
    }

    private fun playSound() {
        Debug.error()
        streamId = mSoundPool.play(
                loadedId,
                1.0f, //左耳道音量【0~1】
                1.0f, //右耳道音量【0~1】
                priority, //播放优先级【0表示最低优先级】
                -1, //循环模式【0表示循环一次，-1表示一直循环，其他表示数字+1表示当前数字对应的循环次数】
                1.0f          //播放速度【1.0是正常，范围从0.5~2.0】
        )
    }

    fun stop() {
        val fixedThreadPool = App.Companion.newFixedThreadPool(2)
        fixedThreadPool.execute({
            mSoundPool.stop(streamId)
//            mSoundPool.release()
        })
    }

    fun busy() {
        mSoundPool.stop(streamId)
        loadedId = mSoundPool.load(context.applicationContext, ring_busy, priority)
    }

    fun error() {
        mSoundPool.stop(streamId)
        loadedId = mSoundPool.load(context.applicationContext, ring_connect_error, priority)
    }

}