package com.commonly.undertone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lechuan.midunovel.base.util.FoxBaseCommonUtils;
import com.lechuan.midunovel.base.util.FoxBaseToastUtils;
import com.lechuan.midunovel.view.FoxListener;
import com.lechuan.midunovel.view.FoxWallView;

public class DobberActivity extends BaseActivity implements FoxListener {

    private FoxWallView mOxWallView1;
    private FoxWallView mOxWallView2;
    private String userId;
    private int slotId;
    private int slotId2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dobber);
        final EditText editAppkey = (EditText) findViewById(R.id.editAppkey);
        final EditText editAppSecret = (EditText) findViewById(R.id.editAppSecret);
        final EditText editAdSlotid = (EditText) findViewById(R.id.editAdSlotid);

        if (getIntent()!=null){
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
            slotId2 = getIntent().getIntExtra("slotId2", 0);
        }
        mOxWallView1 = (FoxWallView) findViewById(R.id.TMAw1);
        mOxWallView2 = (FoxWallView) findViewById(R.id.TMAw2);
        mOxWallView1.setAdListener(this);
        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mOxWallView1.setConfigInfo(editAppkey.getText().toString().trim(),editAppSecret.getText().toString().trim());
                    final String mSlotId = editAdSlotid.getText().toString().trim();
                    if(!FoxBaseCommonUtils.isEmpty(mSlotId)){
                        mOxWallView1.loadAd(Integer.parseInt(mSlotId), userId);
                    }else {
                        mOxWallView1.loadAd(slotId, userId);
                    }
                    if (slotId2!=0){
                        mOxWallView2.loadAd(slotId2, userId);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        if (mOxWallView1 != null) {
            mOxWallView1.destroy();
        }
        if (mOxWallView1 != null) {
            mOxWallView2.destroy();
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
