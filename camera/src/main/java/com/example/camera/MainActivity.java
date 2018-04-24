package com.example.camera;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class MainActivity extends Activity {
    private SurfaceView mSurfaceView;
    private Camera mCamera;
    private BroadcastReceiver mReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);
        mSurfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(mCallback);
        IntentFilter filter = new IntentFilter();
        filter.addAction(MyReceiver.ACTION_USB_DEVICE_DETACHED);
        mReceiver  = new MyReceiver();
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    SurfaceHolder.Callback mCallback = new SurfaceHolder.Callback(){

        @Override
        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            Log.d("MainActivity", "surfaceCreated");
            if (mCamera != null) {
                try {
                    mCamera.setPreviewDisplay(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
            Log.d("MainActivity", "surfaceChanged");
            Camera.Parameters parameters = mCamera.getParameters();// 获取mCamera的参数对象
            parameters.setPictureSize(1280, 720);
            parameters.setPreviewSize(1280, 720);
            List<Camera.Size> xx = parameters.getSupportedVideoSizes();
            for(Camera.Size size: xx){
                Log.d("MainActivity", "getSupportedVideoSizes width:" + size.width + "---height:" + size.height);
            }
            mCamera.setParameters(parameters);

            try {
                mCamera.startPreview();
            } catch (Exception e) {
                e.printStackTrace();
                if (mCamera != null) {
                    mCamera.release();
                    mCamera = null;
                }
            }
            Log.d("MainActivity", "width:" + parameters.getPreviewSize().width + "---height:" + parameters.getPreviewSize().height);
            Log.d("MainActivity", "PictureSize width:" + parameters.getPictureSize().width + "---height:" + parameters.getPictureSize().height);
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
            if (mCamera != null) {
                Log.d("MainActivity","surfaceDestroyed   stopPreview");
                mCamera.stopPreview();
            }
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
        if(mCamera == null){
            Log.d("MainActivity","onStart   open");
            mCamera = Camera.open();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume ");

    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("MainActivity", "onPause  ");

    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCamera != null) {
            Log.d("MainActivity","onStop   release");
            mCamera.release();
            mCamera = null;
        }
    }

    public boolean getUSBState() {
        UsbManager usbManager = (UsbManager)getSystemService(Context.USB_SERVICE);
        HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
        if (deviceList.size() == 0) {
            Log.d("MyReceiver","deviceList.size()　== 0");
            return false;
        }
        Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
        while  (deviceIterator.hasNext()) {
            UsbDevice device = (UsbDevice) deviceIterator.next();
            if (device.getProductName().equals("bftv smart mic")||device.getProductName().equals("Astra Pro HD Camera")
                    ||device.getProductName().equals("BFTV Smart Camera")){
                return true;
            }
        }
        return false;
    }

    public class MyReceiver extends BroadcastReceiver {
        public static final String ACTION_USB_DEVICE_DETACHED = "android.hardware.usb.action.USB_DEVICE_DETACHED";
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ACTION_USB_DEVICE_DETACHED)) {
                UsbDevice device = (UsbDevice)intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                Log.d("MyReceiver","remove device name" + device.getDeviceName());
                if (device.getProductName().equals("bftv smart mic")||device.getProductName().equals("Astra Pro HD Camera")
                        ||device.getProductName().equals("BFTV Smart Camera")){
                    finish();
                }
            }
        }
    }
}
