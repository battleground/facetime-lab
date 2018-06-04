package com.example.camera

import android.content.Context
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.util.Log

object Developer {

    const val ACTION_USB_DEVICE_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED"
    const val ACTION_USB_DEVICE_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED"

    @JvmStatic
    fun isSupport(device: UsbDevice): Boolean {
        return (device.productName == "bftv smart mic" || device.productName == "Astra Pro HD Camera"
                || device.productName == "BFTV Smart Camera")
    }

    @JvmStatic
    fun getUSBState(context: Context): Boolean {
        return getUSBDevice(context) == null
    }

    @JvmStatic
    fun getUSBDevice(context: Context): UsbDevice? {
        val usbManager = context.getSystemService(Context.USB_SERVICE) as UsbManager
        if (usbManager != null) {
            val deviceList = usbManager.deviceList
            if (deviceList.size == 0) {
                Log.d("MyReceiver", "deviceList.size()　== 0")
                return null
            }
            val deviceIterator = deviceList.values.iterator()
            while (deviceIterator.hasNext()) {
                val device = deviceIterator.next()
                if (isSupport(device)) {
                    return device
                }
            }
        }
        return null
    }


}