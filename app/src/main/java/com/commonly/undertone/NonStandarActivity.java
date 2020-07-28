package com.commonly.undertone;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import com.lechuan.midunovel.base.util.FoxBaseCommonUtils;
import com.lechuan.midunovel.base.util.FoxBaseToastUtils;
import com.lechuan.midunovel.view.FoxCustomerTm;
import com.lechuan.midunovel.view.FoxNsTmListener;
import com.lechuan.midunovel.view.video.bean.FoxResponseBean;
import com.lechuan.midunovel.view.video.utils.FoxGsonUtil;


public class NonStandarActivity extends BaseActivity implements FoxNsTmListener {
    private FoxCustomerTm mOxCustomerTm;

    private FoxResponseBean.DataBean dataBean;
    private TextView textView;
    private String userId;
    private int slotId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_non_standar);
        textView = (TextView) findViewById(R.id.content_text);
        final EditText editAppkey = (EditText) findViewById(R.id.editAppkey);
        final EditText editAppSecret = (EditText) findViewById(R.id.editAppSecret);
        final EditText editAdSlotid = (EditText) findViewById(R.id.editAdSlotid);
        final Switch SwitchAdPic = (Switch) findViewById(R.id.SwitchAdPic);
        if (getIntent() != null) {
            userId = getIntent().getStringExtra("userId");
            slotId = getIntent().getIntExtra("slotId", 0);
        }
        mOxCustomerTm = new FoxCustomerTm(this);
        mOxCustomerTm.setAdListener(this);
        Button btnRequest = (Button) findViewById(R.id.btnRequest);
        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mOxCustomerTm.setConfigInfo(editAppkey.getText().toString().trim(), editAppSecret.getText().toString().trim());
                    final String mSlotId = editAdSlotid.getText().toString().trim();
                    if (!FoxBaseCommonUtils.isEmpty(mSlotId)) {
                        if (SwitchAdPic.isChecked()){
                            mOxCustomerTm.loadAd(Integer.parseInt(mSlotId), userId,1);
                        }else {
                            mOxCustomerTm.loadAd(Integer.parseInt(mSlotId), userId,0);
                        }
                    } else {
                        if (SwitchAdPic.isChecked()) {
                            mOxCustomerTm.loadAd(slotId, userId,1);
                        }else {
                            mOxCustomerTm.loadAd(slotId, userId,0);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOxCustomerTm != null && dataBean != null && !FoxBaseCommonUtils.isEmpty(dataBean.getActivityUrl())) {
                    mOxCustomerTm.adClicked();
                    mOxCustomerTm.openFoxActivity(dataBean.getActivityUrl());
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        if (mOxCustomerTm != null) {
            mOxCustomerTm.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onReceiveAd(String result) {
        FoxBaseToastUtils.showShort("广告请求成功");
        if (!FoxBaseCommonUtils.isEmpty(result)) {
            dataBean = FoxGsonUtil.GsonToBean(result, FoxResponseBean.DataBean.class);
        }
        Log.d("========", "onReceiveAd:" + result);
        textView.setText(result);
        if (mOxCustomerTm != null) {
            mOxCustomerTm.adExposed();
        }
    }

    @Override
    public void onFailedToReceiveAd() {
        Log.d("========", "onFailedToReceiveAd");
        FoxBaseToastUtils.showShort("广告请求失败");
    }

    @Override
    public void onAdActivityClose(String data) {
        Log.d("========", "onAdActivityClose" + data);
        FoxBaseToastUtils.showShort("活动页面关闭 发奖信息："+data);
    }
}
