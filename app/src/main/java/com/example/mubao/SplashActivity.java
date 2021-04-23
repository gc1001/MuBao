package com.example.mubao;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.quicksdk.QuickSdkSplashActivity;

public class SplashActivity extends QuickSdkSplashActivity {


    @Override
    public int getBackgroundColor() {
        return Color.WHITE;
    }

    @Override
    public void onSplashStop() {
        Log.d(MainActivity.tag,"闪屏！！！");
        //闪屏结束后，跳转到游戏界面
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        this.finish();
    }
}