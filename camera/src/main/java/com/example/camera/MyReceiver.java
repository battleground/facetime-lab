package com.example.camera;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.CodingErrorAction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by liaolaibin on 2017/4/9.
 */
public class MyReceiver extends BroadcastReceiver {
    public static final String ACTION_USB_DEVICE_ATTACHED = "android.hardware.usb.action.USB_DEVICE_ATTACHED";
    public static final String ACTION_USB_DEVICE_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(ACTION_USB_DEVICE_ATTACHED)) {
            UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
            if (device.getProductName().equals("bftv smart mic")||device.getProductName().equals("Astra Pro HD Camera")
                    ||device.getProductName().equals("BFTV Smart Camera")){
                Intent intent1 = new Intent(context,MainActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent1);
            }
        }
    }


    private boolean isGBKString(String str){
        boolean isGBK =false;
        for(int i=0;i<str.length();i++){
            Log.d("","str:---" + str.charAt(i));
            if((int)str.charAt(i) == 65533){
                isGBK = true;
                break;
            }
        }
        return isGBK;
    }

    public static String toHexString(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return str;
    }
}
