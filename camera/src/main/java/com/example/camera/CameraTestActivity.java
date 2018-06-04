package com.example.camera;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.Camera;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.abooc.util.Debug;

import java.io.IOException;

public class CameraTestActivity extends Activity implements SurfaceHolder.Callback {
    private SurfaceView mSurfaceView;
    private Camera mCamera;
    private BroadcastReceiver mReceiver;
    private TextView mTextView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_test);
        mTextView = findViewById(R.id.agora);

        mSurfaceView = findViewById(R.id.surfaceView);
        SurfaceHolder holder = mSurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this);

        IntentFilter filter = new IntentFilter();
        filter.addAction(MyReceiver.ACTION_USB_DEVICE_ATTACHED);
        filter.addAction(MyReceiver.ACTION_USB_DEVICE_DETACHED);
        mReceiver = new TempReceiver();
        registerReceiver(mReceiver, filter);

        UsbDevice device = Developer.getUSBDevice(this);
        if (device == null) {
            mTextView.setText("未找到【外脑】设备");
        } else {
            mTextView.setText("已接入【外脑】设备：" + device.getProductName());
        }

    }

    void parameters() {
//        Camera.Parameters parameters = mCamera.getParameters();// 获取mCamera的参数对象
//            parameters.setPictureSize(1920, 1280);
//            parameters.setPreviewSize(1920, 1280);
//            parameters.setPictureSize(1280, 720);
//            parameters.setPreviewSize(1280, 720);

//        parameters.setPictureSize(320, 240);
//        parameters.setPreviewSize(320, 240);

//            parameters.setPictureSize(640, 480);
//            parameters.setPreviewSize(640, 480);

//        mCamera.setParameters(parameters);


//        List<Camera.Size> xx = parameters.getSupportedVideoSizes();
//        for (Camera.Size size : xx) {
//            Debug.anchor("SupportedVideoSizes :" + size.width + "x" + size.height);
//        }
//        List<Size> pictureSizes = parameters.getSupportedPictureSizes();
//        int length = pictureSizes.size();
//        for (int i = 0; i < length; i++) {
//            Debug.anchor("SupportedPictureSizes : " + pictureSizes.get(i).width + "x" + pictureSizes.get(i).height);
//        }
//
//        List<Size> previewSizes = parameters.getSupportedPreviewSizes();
//        length = previewSizes.size();
//        for (int i = 0; i < length; i++) {
//            Debug.anchor("SupportedPreviewSizes : " + previewSizes.get(i).width + "x" + previewSizes.get(i).height);
//        }
    }


    void setupCameraView(ViewGroup container) {
        container.removeAllViews();

        SurfaceView iSurfaceView = new SurfaceView(this);
        SurfaceHolder holder = iSurfaceView.getHolder();
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        holder.addCallback(this);
        container.addView(iSurfaceView, new FrameLayout.LayoutParams(-1, -1));
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Debug.anchor("surfaceCreated");
        if (mCamera != null) {
            try {
                Debug.anchor();
                mCamera.setPreviewDisplay(surfaceHolder);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        Debug.anchor("surfaceChanged");

        if (mCamera != null) {
            Debug.anchor();
//            try {
//                parameters();
//            } catch (Exception e) {
//                Debug.error(e);
//            }
            startPreview();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (mCamera != null) {
            Debug.anchor("surfaceDestroyed   stopPreview");
            mCamera.stopPreview();
        }
    }

    void startPreview() {
        try {
            mCamera.startPreview();
        } catch (Exception e) {
            e.printStackTrace();
            if (mCamera != null) {
                mCamera.release();
                mCamera = null;
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mCamera == null) {
            Debug.anchor();
            mCamera = Camera.open();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mCamera != null) {
            Debug.anchor();
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            Debug.anchor();

            ViewGroup container = findViewById(R.id.cameraLayout);
            if (container.getChildCount() == 0) {
                setupCameraView(container);
                try {
                    if (mCamera == null) {
                        mCamera = Camera.open();
                    } else {
                        mCamera.reconnect();
                    }
                } catch (IOException e) {
                    Debug.error(e.getMessage());
                }
            } else {
                container.removeAllViews();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public class TempReceiver extends BroadcastReceiver {

        @SuppressLint("SetTextI18n")
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(MyReceiver.ACTION_USB_DEVICE_DETACHED)) {
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                Debug.anchor("remove device name" + device.getDeviceName());
                if (Developer.isSupport(device)) {

                    mTextView.setText("未找到【外脑】设备");
                    ((ViewGroup) findViewById(R.id.cameraLayout)).removeAllViews();
                }
            } else if (action.equals(MyReceiver.ACTION_USB_DEVICE_ATTACHED)) {
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                if (Developer.isSupport(device)) {

                    mTextView.setText("已接入【外脑】设备：" + device.getProductName());
                    ViewGroup container = findViewById(R.id.cameraLayout);
                    setupCameraView(container);
                    if (mCamera != null) {
                        Debug.anchor();
                        try {
                            mCamera.reconnect();
                            Debug.anchor();
                        } catch (IOException e) {
                            Debug.error(e.getMessage());
                        }
                    } else {
                        mCamera = Camera.open();
                    }
                }
            }

        }
    }

}
