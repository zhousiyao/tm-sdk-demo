package com.commonly.undertone;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import com.lechuan.android.base.util.FoxBaseCommonUtils;
import com.lechuan.android.nativead.Ad;
import com.lechuan.android.nativead.AdCallBack;

import androidx.appcompat.app.AppCompatActivity;

import static com.lechuan.android.nativead.Ad.AD_NEW_LOADING_HIDE;

public class NativeInterstitial3Activity extends AppCompatActivity {

    private Ad ad;
    private String userId;
    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_interstitial3);
        final FrameLayout frameLayout = findViewById(R.id.coordinator);
        //插屏：325021  嵌入式：325613
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        final EditText editAppkey = (EditText) findViewById(R.id.editAppkey);
        final EditText editAppSecret = (EditText) findViewById(R.id.editAppSecret);
        final EditText editAdSlotid = (EditText) findViewById(R.id.editAdSlotid);

        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String mSlotId = editAdSlotid.getText().toString().trim();
                    if (!FoxBaseCommonUtils.isEmpty(mSlotId)) {
                        slotId = Integer.parseInt(mSlotId);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                ad = new Ad("", "" + slotId, "" + userId, "",AD_NEW_LOADING_HIDE);
                if(!FoxBaseCommonUtils.isEmpty(editAppkey.getText().toString().trim())
                        &&!FoxBaseCommonUtils.isEmpty(editAppSecret.getText().toString().trim())){
                    ad.setConfigInfo(editAppkey.getText().toString().trim(), editAppSecret.getText().toString().trim());
                }
                ad.init(NativeInterstitial3Activity.this, null, Ad.AD_URL_OLD, new AdCallBack() {

                    @Override
                    public void onReceiveAd() {
                        if(ad!=null){
                            ad.show();
                        }
                    }

                    @Override
                    public void onFailedToReceiveAd() {
                        Log.d("========", "onFailedToReceiveAd");
                    }

                    @Override
                    public void onActivityClose() {
                        Log.d("========", "onActivityClose");
                    }

                    @Override
                    public void onActivityShow() {
                        Log.d("========", "onActivityShow");
                    }

                    @Override
                    public void onRewardClose() {
                        Log.d("========", "onRewardClose");
                    }

                    @Override
                    public void onRewardShow() {
                        Log.d("========", "onRewardShow");
                    }

                    @Override
                    public void onPrizeClose() {
                        Log.d("========", "onPrizeClose");
                    }

                    @Override
                    public void onPrizeShow() {
                        Log.d("========", "onPrizeShow");
                    }
                });
                ad.loadAd(NativeInterstitial3Activity.this, true);
            }
        });
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
        boolean isConsume = true;
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
