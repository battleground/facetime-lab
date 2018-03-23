package com.bftv.launch_samples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void onStart1(View view) {
        Intent intent = new Intent();
        intent.setClassName("com.bftv.fui.videochat", "com.bftv.facetime.activity.WelcomeActivity");
        startActivity(intent);
    }

    public void onStart2(View view) {
        Intent intent = new Intent("com.bftv.facetime.action.MAIN");
        startActivity(intent);
    }

    public void onStart3(View view) {
        Intent intent = new Intent();
        intent.setClassName("com.bftv.fui.videochat", "com.bftv.fui.videochat.views.actvitity.VideoChatMenuActivity");
        startActivity(intent);
    }

    public void onStart4(View view) {
        Intent intent = new Intent("com.bftv.fui.videochat.VideoChatMenuActivity");
        startActivity(intent);
    }


    public void onStart5(View view) {
        Intent intent = new Intent();
        intent.setClassName("com.bftv.fui.videochat", "com.bftv.facetime.activity.WelcomeActivity");
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        startActivity(intent);
    }

    public void onStartService(View view) {
        Intent intent = new Intent("com.bftv.fui.videochat.Service");
        intent.setPackage("com.bftv.fui.videochat");
        startService(intent);
    }

    public void onCallFromDaerduo(View view) {
        Intent intent = new Intent("com.bftv.fui.videochat.VideoChatCallActivity");
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // 135601920080434998

        Bundle bundle = new Bundle();
        bundle.putString("uid", "135601920080434998");
        bundle.putString("nickname", "噗噗");
        bundle.putString("avatar", "");
//        bundle.putString("callnum", "135601920080434998");
        bundle.putString("remark", "");
        intent.putExtras(bundle);
        startActivity(intent);
    }


}
