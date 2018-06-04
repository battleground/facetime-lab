//package com.example.camera
//
//
//import android.annotation.SuppressLint
//import android.content.BroadcastReceiver
//import android.content.Context
//import android.content.Intent
//import android.content.IntentFilter
//import android.hardware.camera2.CameraDevice
//import android.hardware.camera2.CameraManager
//import android.hardware.usb.UsbDevice
//import android.hardware.usb.UsbManager
//import android.os.Bundle
//import android.os.Handler
//import android.support.v4.app.Fragment
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.abooc.util.Debug
//import kotlinx.android.synthetic.main.fragment_developer.*
//
//
//private const val ARG_PARAM1 = "param1"
//
//class DeveloperFragment : Fragment() {
//
//    var mReceiver = TempReceiver()
//    private var mCamera: Camera? = null
//    var manager: CameraManager? = null
//
//    @SuppressLint("MissingPermission")
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        manager = activity?.getSystemService(Context.CAMERA_SERVICE) as CameraManager
//        manager?.openCamera("0", object : CameraDevice.StateCallback() {
//            override fun onOpened(camera: CameraDevice) {
//                Debug.error()
//
//                camera.
//            }
//
//            override fun onClosed(camera: CameraDevice) {
//                Debug.error()
//            }
//
//            override fun onDisconnected(camera: CameraDevice) {
//                Debug.error()
//            }
//
//            override fun onError(camera: CameraDevice?, error: Int) {
//                Debug.error()
//            }
//
//        }, Handler())
//    }
//
//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
//                              savedInstanceState: Bundle?): View? {
//        return inflater.inflate(R.layout.fragment_developer, container, false)
//    }
//
//    @SuppressLint("SetTextI18n")
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val filter = IntentFilter()
//        filter.addAction(MyReceiver.ACTION_USB_DEVICE_ATTACHED)
//        filter.addAction(MyReceiver.ACTION_USB_DEVICE_DETACHED)
//        activity?.registerReceiver(mReceiver, filter)
//
//        val device = CameraTestActivity.getUSBDevice(activity)
//        if (device == null) {
//            usbDevicesName.text = ("未找到【外脑】设备")
//        } else {
//            usbDevicesName.text = "已接入【外脑】设备：${device.productName}"
//        }
//    }
//
//
//    override fun onStart() {
//        super.onStart()
//        if (mCamera == null) {
//            Debug.anchor()
//            mCamera = Camera.open()
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        if (mCamera != null) {
//            Debug.anchor()
//            mCamera?.release()
//            mCamera?.reconnect()
//            mCamera = null
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        activity?.unregisterReceiver(mReceiver)
//    }
//
//
//    override fun onDestroy() {
//        super.onDestroy()
//    }
//
//
//    inner class TempReceiver : BroadcastReceiver() {
//
//        @SuppressLint("SetTextI18n")
//        override fun onReceive(context: Context, intent: Intent) {
//            val action = intent.action
//            if (action == MyReceiver.ACTION_USB_DEVICE_DETACHED) {
//                val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
//                Debug.anchor("remove device name" + device.deviceName)
//                if (CameraTestActivity.isSupport(device)) {
//
//                    usbDevicesName.text = "未找到【外脑】设备"
//                }
//            } else if (action == MyReceiver.ACTION_USB_DEVICE_ATTACHED) {
//                val device = intent.getParcelableExtra<UsbDevice>(UsbManager.EXTRA_DEVICE)
//                if (CameraTestActivity.isSupport(device)) {
//
//                    usbDevicesName.text = "已接入【外脑】设备：${device.productName}"
//                }
//            }
//
//        }
//    }
//}
