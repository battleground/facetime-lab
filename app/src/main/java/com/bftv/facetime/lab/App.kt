package com.bftv.facetime.lab

import android.app.Application
import android.content.Context
import com.abooc.util.Debug
import com.abooc.widget.Toast
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedBlockingQueue
import java.util.concurrent.ThreadPoolExecutor
import java.util.concurrent.TimeUnit

/**
 * Created by dayu on 2017/9/1.
 */
class App : Application() {

    companion object {
        @JvmStatic
        private lateinit var mApp: App

        @JvmStatic
        fun getApplication(): App {
            return mApp
        }

        @JvmStatic
        fun newFixedThreadPool(nThreads: Int): ExecutorService {
            return ThreadPoolExecutor(nThreads, nThreads,
                    0L, TimeUnit.MILLISECONDS,
                    LinkedBlockingQueue<Runnable>())
        }

    }

    override fun onCreate() {
        super.onCreate()
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return
//        }
//        LeakCanary.install(this)
        mApp = this
        Debug.enable(BuildConfig.DEBUG)
        Debug.mMessageHeader = " "
        Toast.init(this)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
//        MultiDex.install(this)
    }

}