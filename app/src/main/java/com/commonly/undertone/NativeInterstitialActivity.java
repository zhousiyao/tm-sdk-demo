package com.commonly.undertone;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lechuan.midunovel.base.util.FoxBaseCommonUtils;
import com.lechuan.midunovel.nativead.Ad;
import com.lechuan.midunovel.nativead.DefaultAdCallBack;
import com.lechuan.midunovel.nativead.bean.AdResponseBean;

import static com.lechuan.midunovel.nativead.Ad.AD_NEW_LOADING_HIDE;

public class NativeInterstitialActivity extends BaseActivity{

    private Ad ad;
    private String userId;
    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_interstitial);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        final EditText editAppkey = (EditText) findViewById(R.id.editAppkey);
        final EditText editAppSecret = (EditText) findViewById(R.id.editAppSecret);
        final EditText editAdSlotid = (EditText) findViewById(R.id.editAdSlotid);
        try {
            final String mSlotId = editAdSlotid.getText().toString().trim();
            if (!FoxBaseCommonUtils.isEmpty(mSlotId)) {
                slotId =  Integer.parseInt(mSlotId);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        ad = new Ad("", "" + slotId, "" + userId, "",AD_NEW_LOADING_HIDE);
        ad.init(NativeInterstitialActivity.this, null, Ad.AD_URL_NEW, new DefaultAdCallBack() {

            @Override
            public void onReceiveAd(AdResponseBean.DataBean dataBean) {
                super.onReceiveAd(dataBean);
            }
        });
        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!FoxBaseCommonUtils.isEmpty(editAppkey.getText().toString().trim())
                        &&!FoxBaseCommonUtils.isEmpty(editAppSecret.getText().toString().trim())){
                    ad.setConfigInfo(editAppkey.getText().toString().trim(), editAppSecret.getText().toString().trim());
                }
                if (!FoxBaseCommonUtils.isEmpty(editAdSlotid.getText().toString().trim())){
                    ad.resetSlotId(editAdSlotid.getText().toString().trim()+"");
                }
                ad.loadAd(NativeInterstitialActivity.this,false);
            }
        });
        ad.loadAd(NativeInterstitialActivity.this,false);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (ad != null) {
            ad.resetAdSize(newConfig.orientation);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        boolean isConsume = false;
        if (ad != null) {
            isConsume = ad.onKeyBack(keyCode, event);
        }
        if (!isConsume) {
            return super.onKeyDown(keyCode, event);
        }
        return isConsume;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ad != null) {
            ad.destroy();
        }
    }
}
