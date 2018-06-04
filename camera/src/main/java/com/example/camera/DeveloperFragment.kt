package com.example.camera


import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Camera
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.*
import android.widget.FrameLayout
import com.abooc.util.Debug
import kotlinx.android.synthetic.main.fragment_developer.*


private const val ARG_PARAM1 = "param1"

class DeveloperFragment : Fragment() {

    private var mReceiver = TempReceiver()
    private var mCamera: Camera? = null

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_developer, container, false)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val filter = IntentFilter()
        filter.addAction(Developer.ACTION_USB_DEVICE_ATTACHED)
        filter.addAction(Developer.ACTION_USB_DEVICE_DETACHED)
        activity?.registerReceiver(mReceiver, filter)

        val device = Developer.getUSBDevice(context!!)
        if (device == null) {
            usbDevicesName.text = ("未找到【外脑】设备")
            usbCameraParameters.text = null
        } else {
            usbDevicesName.text = "已接入【外脑】设备：${device.productName}"
        }


        setupCameraView(cameraFrame)
    }


    val uiHandler = Handler()

    internal fun setupCameraView(container: ViewGroup) {
        container.removeAllViews()

        val iSurfaceView = SurfaceView(activity)
        val holder = iSurfaceView.holder
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
        holder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                if (mCamera == null) {
                    mCamera = Camera.open()
                    mCamera?.setPreviewDisplay(holder)
                    Debug.anchor("初始化Camera引擎：$mCamera")
                }
            }

            override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
                Debug.error(mCamera)
                setupParameters()
                mCamera?.parameters?.setPreviewSize(320, 240)
                mCamera?.startPreview()
            }

            override fun surfaceDestroyed(holder: SurfaceHolder) {
                Debug.anchor("销毁Camera引擎")
                mCamera?.stopPreview()
                mCamera?.release()
                mCamera = null
            }

        })
        container.addView(iSurfaceView, FrameLayout.LayoutParams(-1, -1))
    }

    override fun onStart() {
        super.onStart()
        Debug.anchor()
    }

    override fun onResume() {
        super.onResume()
        Debug.anchor()
    }

    override fun onPause() {
        super.onPause()
        Debug.anchor()
    }

    override fun onStop() {
        super.onStop()
        Debug.anchor()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Debug.anchor()
        if (mCamera != null) {
            Debug.anchor()
            mCamera?.release()
            mCamera = null
        }
        activity?.unregisterReceiver(mReceiver)
    }


    override fun onDestroy() {
        super.onDestroy()
        Debug.anchor()
    }

    fun setupParameters() {
        try {
            var size = mCamera?.parameters?.supportedVideoSizes
            var str = sizesToString(size)
            usbCameraParameters.text = "支持分辨率：$str"
        } catch (e: Exception) {
            Debug.error()
        }
    }

    private fun sizesToString(sizes: List<Camera.Size>?): String? {
        if (sizes != null) {
            var buffer = StringBuffer()
            for (size in sizes) {
                buffer.append("${size.width}x${size.height}, ")
            }
            buffer.delete(buffer.lastIndexOf(","), buffer.length)
            return buffer.toString()
        }
        return null
    }

    fun clearCanvas(container: ViewGroup) {
        container.removeAllViews()
    }

    inner class TempReceiver : BroadcastReceiver() {

        @SuppressLint("SetTextI18n")
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            if (action == Developer.ACTION_USB_DEVICE_DETACHED) {
                val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
                if (Developer.isSupport(device)) {
                    Debug.anchor("已移除【外脑】设备：" + device.deviceName)
                    usbDevicesName.text = "未找到【外脑】设备"
                    usbCameraParameters.text = null
                    clearCanvas(cameraFrame)
                }
            } else if (action == Developer.ACTION_USB_DEVICE_ATTACHED) {
                val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
                if (Developer.isSupport(device)) {
                    Debug.anchor("已接入【外脑】设备：${device.productName}")
                    usbDevicesName.text = "已接入【外脑】设备：${device.productName}"

                    uiHandler.postDelayed({
                        Debug.anchor("由于摄像头接入耗时，延时读取摄像头...")
                        setupCameraView(cameraFrame)
                    }, 3000)
                }
            }

        }
    }
}
