package com.commonly.undertone;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lechuan.android.base.util.FoxBaseCommonUtils;
import com.lechuan.android.base.util.FoxBaseGsonUtil;
import com.lechuan.android.base.util.FoxBaseToastUtils;
import com.lechuan.android.nativead.Ad;
import com.lechuan.android.nativead.DefaultAdCallBack;
import com.lechuan.android.nativead.bean.AdResponseBean;

import static com.lechuan.android.nativead.Ad.AD_NEW_LOADING_HIDE;


public class NativeInterstitial2Activity extends BaseActivity {

    private Ad ad;
    private String userId;
    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_native_interstitial2);
        final FrameLayout frameLayout = findViewById(R.id.coordinator);
        //插屏：325021  嵌入式：325613
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        final EditText editAppkey = (EditText) findViewById(R.id.editAppkey);
        final EditText editAppSecret = (EditText) findViewById(R.id.editAppSecret);
        final EditText editAdSlotid = (EditText) findViewById(R.id.editAdSlotid);
        final ImageView img = (ImageView) findViewById(R.id.img);
        Button btnRequest = (Button) findViewById(R.id.btnRequest);

        ad = new Ad("", "" + slotId, "" + userId, "",AD_NEW_LOADING_HIDE);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    final String mSlotId = editAdSlotid.getText().toString().trim();
                    if (!FoxBaseCommonUtils.isEmpty(mSlotId)) {
                        slotId =  Integer.parseInt(mSlotId);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                if(!FoxBaseCommonUtils.isEmpty(editAppkey.getText().toString().trim())
                        &&!FoxBaseCommonUtils.isEmpty(editAppSecret.getText().toString().trim())){
                    ad.setConfigInfo(editAppkey.getText().toString().trim(), editAppSecret.getText().toString().trim());
                }
                ad.init(NativeInterstitial2Activity.this, null, Ad.AD_URL_OLD, new DefaultAdCallBack() {

                    @Override
                    public void onReceiveAd(AdResponseBean.DataBean dataBean) {
                        super.onReceiveAd(dataBean);
                        FoxBaseToastUtils.showShort(FoxBaseGsonUtil.GsonString(dataBean));
                        if (dataBean!=null && !FoxBaseCommonUtils.isEmpty(dataBean.getImageUrl())){
                            if (dataBean.getImageUrl().endsWith(".gif")){
                                Glide.with(NativeInterstitial2Activity.this).load(dataBean.getImageUrl()).asGif().into(img);
                            }else {
                                Glide.with(NativeInterstitial2Activity.this).load(dataBean.getImageUrl()).into(img);
                            }
                        }
                    }

                    @Override
                    public void onFailedToReceiveAd() {
                        super.onFailedToReceiveAd();
                    }
                });
                ad.loadAd(NativeInterstitial2Activity.this, true);
            }
        });
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ad!=null){
                    ad.show();
                }
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
