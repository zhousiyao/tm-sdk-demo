package com.commonly.undertone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lechuan.android.base.util.FoxBaseCommonUtils;
import com.lechuan.android.base.util.FoxBaseToastUtils;
import com.lechuan.android.view.FoxListener;
import com.lechuan.android.view.FoxTbScreen;


public class InterstitialActivity extends BaseActivity implements FoxListener {

    private FoxTbScreen mTMItAd;
    private String userId;
    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interstitial);
        if (getIntent()!=null){
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        final EditText editAppkey = (EditText) findViewById(R.id.editAppkey);
        final EditText editAppSecret = (EditText) findViewById(R.id.editAppSecret);
        final EditText editAdSlotid = (EditText) findViewById(R.id.editAdSlotid);
        mTMItAd = new FoxTbScreen(this);
        mTMItAd.setAdListener(this);
        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mTMItAd.setConfigInfo(editAppkey.getText().toString().trim(),editAppSecret.getText().toString().trim());
                    final String mSlotId = editAdSlotid.getText().toString().trim();
                    if(!FoxBaseCommonUtils.isEmpty(mSlotId)){
                        mTMItAd.loadAd(Integer.parseInt(mSlotId), userId);
                    }else {
                        mTMItAd.loadAd(slotId, userId);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }
    @Override
    protected void onDestroy() {
        if (mTMItAd != null) {
            mTMItAd.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onReceiveAd() {
        Log.d("========", "onReceiveAd");
        FoxBaseToastUtils.showShort("请求广告成功");
    }

    @Override
    public void onFailedToReceiveAd() {
        Log.d("========", "onFailedToReceiveAd");
        FoxBaseToastUtils.showShort("请求广告失败");
    }

    @Override
    public void onLoadFailed() {
        Log.d("========", "onLoadFailed");
        FoxBaseToastUtils.showShort("素材加载失败");
    }

    @Override
    public void onCloseClick() {
        Log.d("========", "onCloseClick");
        FoxBaseToastUtils.showShort("点击关闭");
    }

    @Override
    public void onAdClick() {
        Log.d("========", "onAdClick");
        FoxBaseToastUtils.showShort("点击素材");
    }

    @Override
    public void onAdExposure() {
        Log.d("========", "onAdExposure");
        FoxBaseToastUtils.showShort("素材曝光成功");
    }

    @Override
    public void onAdActivityClose(String data) {
        Log.d("========", "onAdActivityClose="+data);
        FoxBaseToastUtils.showShort("活动页面关闭 发奖信息："+data);
    }
}
