package com.commonly.undertone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lechuan.android.base.util.FoxBaseCommonUtils;
import com.lechuan.android.base.util.FoxBaseToastUtils;
import com.lechuan.android.view.FoxListener;
import com.lechuan.android.view.FoxStreamerView;


public class BannerActivity extends BaseActivity implements FoxListener {

    private FoxStreamerView TMBrView_700_280;
    private FoxStreamerView TMBrView_640_150;


    private String userId;
    private int slotId;
    private int slotId2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        final EditText editAppkey = (EditText) findViewById(R.id.editAppkey);
        final EditText editAppSecret = (EditText) findViewById(R.id.editAppSecret);
        final EditText editAdSlotid = (EditText) findViewById(R.id.editAdSlotid);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
            slotId2 = getIntent().getIntExtra("slotId2", 0);
        }
        TMBrView_700_280 = (FoxStreamerView) findViewById(R.id.TMBrView_700_280);
        TMBrView_640_150 = (FoxStreamerView) findViewById(R.id.TMBrView_640_150);
        TMBrView_700_280.setAdListener(this);
        TMBrView_640_150.setAdListener(this);
        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (!FoxBaseCommonUtils.isEmpty(editAppkey.getText().toString().trim()) &&
                            !FoxBaseCommonUtils.isEmpty(editAppSecret.getText().toString().trim()) ){
                        TMBrView_700_280.setConfigInfo(editAppkey.getText().toString().trim(),editAppSecret.getText().toString().trim());
                    }
                    final String mSlotId = editAdSlotid.getText().toString().trim();
                    if(!FoxBaseCommonUtils.isEmpty(mSlotId)){
                        TMBrView_700_280.loadAd(Integer.parseInt(mSlotId), userId);
                    }else {
                        TMBrView_700_280.loadAd(slotId, userId);
                    }

                    if (slotId2!=0){
                        TMBrView_640_150.loadAd(slotId2, userId);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (TMBrView_700_280 != null) {
            TMBrView_700_280.destroy();
        }
        if (TMBrView_640_150 != null) {
            TMBrView_640_150.destroy();
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
