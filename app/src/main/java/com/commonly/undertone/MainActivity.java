package com.commonly.undertone;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    String userId = "test-12345";


    private static final String[] NEEDED_PERMISSIONS = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.READ_PHONE_STATE,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= 23) {
            if (!lacksPermissions(this, NEEDED_PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, NEEDED_PERMISSIONS, 0);
            }
        }
        //非激励
        findViewById(R.id.sTMSplashButton).setOnClickListener(this);
        findViewById(R.id.sTMSplash2Button).setOnClickListener(this);
        findViewById(R.id.sTMInfoButton).setOnClickListener(this);
        findViewById(R.id.TMItButton).setOnClickListener(this);
        findViewById(R.id.TMBrButton).setOnClickListener(this);
        findViewById(R.id.floatButton).setOnClickListener(this);
        findViewById(R.id.nsButton).setOnClickListener(this);
        findViewById(R.id.nsButtonCP).setOnClickListener(this);
        findViewById(R.id.nsButtonCP2).setOnClickListener(this);
        findViewById(R.id.nsButtonCP3).setOnClickListener(this);
        final Button nsButtonTest = findViewById(R.id.nsButtonTest);
        nsButtonTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.nsButtonTest:
                //test
                intent = new Intent(this, TestToolActivity.class);
                break;
            case R.id.sTMSplashButton:
                //开屏 线上 323775  测试 283556
                intent = new Intent(this, SplashActivity.class);
                if (BuildConfig.BUILD_TYPE.contains("envTest")) {
                    intent.putExtra("slotId", 283674);
                } else {
                    intent.putExtra("slotId", 330487);
                }
                break;
            case R.id.sTMSplash2Button:
                //开屏 线上 323775  测试 283556
                intent = new Intent(this, SplashActivity.class);
                if (BuildConfig.BUILD_TYPE.contains("envTest")) {
                    intent.putExtra("slotId", 283674);
                } else {
                    intent.putExtra("slotId", 330488);
                }
                break;
            case R.id.sTMInfoButton:
                //信息流 线上 330489  测试 283553
                intent = new Intent(this, NativeAdActivity.class);
                if (BuildConfig.BUILD_TYPE.contains("envTest")) {
                    intent.putExtra("slotId", 283693);
                } else {
                    intent.putExtra("slotId", 331034);
                    intent.putExtra("slotId2", 331035);
                }
                break;
            case R.id.TMBrButton:
                //Banner 线上 323778  测试 283554
                intent = new Intent(this, BannerActivity.class);
                if (BuildConfig.BUILD_TYPE.contains("envTest")) {
                    intent.putExtra("slotId", 283679);
                } else {
                    intent.putExtra("slotId", 330360);
                    intent.putExtra("slotId2", 330361);
                }
                break;
            case R.id.TMItButton:
                //插屏  线上 323776    测试 283552
                intent = new Intent(this, InterstitialActivity.class);
                if (BuildConfig.BUILD_TYPE.contains("envTest")) {
                    intent.putExtra("slotId", 283685);
                } else {
                    intent.putExtra("slotId", 330491);
                }
                break;
            case R.id.floatButton:
                //浮标  线上 323779   测试  283555
                intent = new Intent(this, DobberActivity.class);
                if (BuildConfig.BUILD_TYPE.contains("envTest")) {
                    intent.putExtra("slotId", 283686);
                } else {
                    intent.putExtra("slotId", 330492);
                    intent.putExtra("slotId2", 330494);
                }
                break;
            case R.id.nsButton:
                //自定义 323807 正式  测试 283557
                intent = new Intent(this, NonStandarActivity.class);
                if (BuildConfig.BUILD_TYPE.contains("envTest")) {
                    intent.putExtra("slotId", 283557);
                } else {
                    intent.putExtra("slotId", 323807);
                }
                break;
            case R.id.nsButtonCP:
                //原生插屏1081  直接展示
                intent = new Intent(this, NativeInterstitialActivity.class);
                intent.putExtra("slotId", 331946);
                break;
            case R.id.nsButtonCP2:
                //原生插屏1081  素材使用
                intent = new Intent(this, NativeInterstitial2Activity.class);
                intent.putExtra("slotId", 331946);
                break;
            default:
                return;
        }
        intent.putExtra("userId", userId);
        startActivity(intent);
    }

    /**
     * 判断权限集合
     * permissions 权限数组
     * return false-表示没有改权限  true-表示权限已开启
     */
    public boolean lacksPermissions(Context mContexts, String[] permissions) {
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
