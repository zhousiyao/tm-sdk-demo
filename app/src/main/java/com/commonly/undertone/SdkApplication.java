package com.commonly.undertone;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

public class SdkApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ProSDK.init(this,"4UycwwZv41rwzne1ZXgtQBgDSnPH","3WpyTLfifQyGhvgivxtUjvzXxtkzdceETBU2n5g");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}