package com.commonly.undertone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.lechuan.midunovel.base.util.FoxBaseCommonUtils;
import com.lechuan.midunovel.view.holder.FoxNativeAdHelper;
import com.lechuan.midunovel.view.holder.FoxNativeSplashHolder;
import com.lechuan.midunovel.view.holder.FoxSplashAd;


public class SplashActivity extends Activity implements View.OnClickListener, FoxNativeSplashHolder.LoadSplashAdListener {

    private FrameLayout mContainer;
    private FoxNativeSplashHolder foxNativeSplashHolder;
    private int slotId;
    private String userId;
    private EditText editAppkey;
    private EditText editAppSecret;
    private EditText editAdSlotid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (getIntent()!=null){
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        editAppkey = (EditText) findViewById(R.id.editAppkey);
        editAppSecret = (EditText) findViewById(R.id.editAppSecret);
        editAdSlotid = (EditText) findViewById(R.id.editAdSlotid);
        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        mContainer = findViewById(R.id.container);
        foxNativeSplashHolder = FoxNativeAdHelper.getNativeSplashHolder();
        btnRequest.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (foxNativeSplashHolder !=null){
            foxNativeSplashHolder.destroy();
        }
    }

    /**
     * 跳转主页
     */
    private void jumpMain() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    @Override
    public void onClick(View v) {
        if (foxNativeSplashHolder !=null){
            foxNativeSplashHolder.setConfigInfo(editAppkey.getText().toString().trim(),editAppSecret.getText().toString().trim());
            final String mSlotId = editAdSlotid.getText().toString().trim();
            if(!FoxBaseCommonUtils.isEmpty(mSlotId)){
                foxNativeSplashHolder.loadSplashAd(Integer.parseInt(mSlotId), userId,this);
            }else {
                foxNativeSplashHolder.loadSplashAd(slotId, userId,this);
            }
        }
    }

    @Override
    public void onError(String errorBody) {
        Toast.makeText(SplashActivity.this,"Error-"+errorBody, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void splashAdSuccess(FoxSplashAd foxSplashAd) {
        View view = null;
        if(foxSplashAd != null){
            foxSplashAd.setScaleType(ImageView.ScaleType.FIT_XY);
            view = foxSplashAd.getView();
        }
        if (view != null && mContainer != null && !SplashActivity.this.isFinishing()){
            mContainer.removeAllViews();
            mContainer.addView(view);
        }else{
            jumpMain();
        }
    }

    @Override
    public void onTimeOut() {
        Toast.makeText(SplashActivity.this,"倒计时时间到", Toast.LENGTH_SHORT).show();
        jumpMain();
    }

    @Override
    public void onReceiveAd() {
        Toast.makeText(SplashActivity.this,"广告已加载完成", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailedToReceiveAd() {

    }

    @Override
    public void onLoadFailed() {
        Toast.makeText(SplashActivity.this,"广告加载失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCloseClick() {
        Toast.makeText(SplashActivity.this,"点击了跳过按钮", Toast.LENGTH_SHORT).show();
        jumpMain();
    }

    @Override
    public void onAdClick() {
        Toast.makeText(SplashActivity.this,"点击了广告", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdExposure() {
        Toast.makeText(SplashActivity.this,"广告曝光成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onAdActivityClose(String data) {
        jumpMain();
    }
}

