package com.commonly.undertone;


import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lechuan.midunovel.base.util.FoxBaseCommonUtils;
import com.lechuan.midunovel.base.util.FoxBaseToastUtils;
import com.lechuan.midunovel.view.FoxListener;
import com.lechuan.midunovel.view.FoxStreamerView;

/**
 * 横幅
 */
public class SbannerActivity extends BaseActivity {

    private FoxStreamerView mTMBrAdView;
    private String userId;
    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sbanner);
        mTMBrAdView = (FoxStreamerView) findViewById(R.id.TMBrView);

        final EditText editAppkey = (EditText) findViewById(R.id.editAppkey);
        final EditText editAppSecret = (EditText) findViewById(R.id.editAppSecret);
        final EditText editAdSlotid = (EditText) findViewById(R.id.editAdSlotid);
        Button btnRequest = (Button) findViewById(R.id.btnRequest);

        if (getIntent()!=null){
            userId = getIntent().getStringExtra("userId");
           slotId = getIntent().getIntExtra("slotId", 0);
        }
        mTMBrAdView.setAdListener(new FoxListener() {

            @Override
            public void onReceiveAd() {
                Log.d("========", "onReceiveAd");
            }

            @Override
            public void onFailedToReceiveAd() {
                Log.d("========", "onFailedToReceiveAd");
            }

            @Override
            public void onLoadFailed() {
                Log.d("========", "onLoadFailed");
            }

            @Override
            public void onCloseClick() {
                Log.d("========", "onCloseClick");
            }

            @Override
            public void onAdClick() {
                Log.d("========", "onClick");
            }

            @Override
            public void onAdExposure() {
                Log.d("========", "onAdExposure");
            }

            @Override
            public void onAdActivityClose(String data) {
                Log.d("========", "onAdActivityClose"+data);
                if (!TextUtils.isEmpty(data)){
                    FoxBaseToastUtils.showShort("发奖回调："+data);
                }
            }
        });
//        mTMBrAdView.loadAd(slotId,userId);//加载对应GGid
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mTMBrAdView.setConfigInfo(editAppkey.getText().toString().trim(),editAppSecret.getText().toString().trim());
                    final String mSlotId = editAdSlotid.getText().toString().trim();
                    if(!FoxBaseCommonUtils.isEmpty(mSlotId)){
                        mTMBrAdView.loadAd(Integer.parseInt(mSlotId), userId);
                    }else {
                        mTMBrAdView.loadAd(slotId, userId);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        if (mTMBrAdView != null) {
            mTMBrAdView.destroy();
        }
        super.onDestroy();
    }

}
