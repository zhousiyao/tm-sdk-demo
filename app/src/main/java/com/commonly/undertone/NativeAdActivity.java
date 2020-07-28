package com.commonly.undertone;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.lechuan.midunovel.base.util.FoxBaseCommonUtils;
import com.lechuan.midunovel.view.FoxInfoStreamView;
import com.lechuan.midunovel.view.holder.FoxInfoAd;
import com.lechuan.midunovel.view.holder.FoxNativeAdHelper;
import com.lechuan.midunovel.view.holder.FoxNativeInfoHolder;


public class NativeAdActivity extends BaseActivity implements FoxNativeInfoHolder.LoadInfoAdListener, View.OnClickListener {

    private FoxInfoStreamView mTMNaAdView;
    private FoxInfoStreamView mTMNaAdView2;
    private LinearLayout mContainer;
    private String userId;
    private int slotId;
    private int slotId2;
    private FoxNativeInfoHolder nativeInfoHolder;
    private FoxNativeInfoHolder nativeInfoHolder2;
    private EditText editAppkey;
    private EditText editAppSecret;
    private EditText editAdSlotid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_ad);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
            slotId2 = getIntent().getIntExtra("slotId2", 0);
        }
        editAppkey = (EditText) findViewById(R.id.editAppkey);
        editAppSecret = (EditText) findViewById(R.id.editAppSecret);
        editAdSlotid = (EditText) findViewById(R.id.editAdSlotid);
        mContainer = findViewById(R.id.container);
        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        nativeInfoHolder = FoxNativeAdHelper.getNativeInfoHolder();
        nativeInfoHolder2 = FoxNativeAdHelper.getNativeInfoHolder();
        btnRequest.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        if (mTMNaAdView != null) {
            mTMNaAdView.destroy();
        }
        if (mTMNaAdView2 != null) {
            mTMNaAdView2.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onError(String errorBody) {

    }

    @Override
    public void infoAdSuccess(FoxInfoAd foxInfoAd) {
        View view = null;
        if (foxInfoAd != null) {
            view = foxInfoAd.getView();
        }
        if (view != null && mContainer != null && !NativeAdActivity.this.isFinishing()) {
            mContainer.removeAllViews();
            mContainer.addView(view);
        }
    }

    @Override
    public void onReceiveAd() {

    }

    @Override
    public void onFailedToReceiveAd() {

    }

    @Override
    public void onLoadFailed() {

    }

    @Override
    public void onCloseClick() {

    }

    @Override
    public void onAdClick() {

    }

    @Override
    public void onAdExposure() {

    }

    @Override
    public void onAdActivityClose(String data) {

    }

    @Override
    public void onClick(View v) {
        nativeInfoHolder.setConfigInfo(editAppkey.getText().toString().trim(), editAppSecret.getText().toString().trim());
        final String mSlotId = editAdSlotid.getText().toString().trim();
        if (!FoxBaseCommonUtils.isEmpty(mSlotId)) {
            nativeInfoHolder.loadInfoAd(Integer.parseInt(mSlotId), userId, this);
        } else {
            nativeInfoHolder.loadInfoAd(slotId, userId, this);
        }

        if (slotId2!=0){
            nativeInfoHolder.loadInfoAd(slotId2, userId, this);
        }
    }
}
