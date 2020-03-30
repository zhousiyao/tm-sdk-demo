package com.commonly.undertone;

import android.app.Application;
import android.content.Context;
import com.lechuan.midunovel.base.okgo.OkGo;
import com.lechuan.midunovel.base.okgo.cookie.CookieJarImpl;
import com.lechuan.midunovel.base.okgo.cookie.store.SPCookieStore;
import com.lechuan.midunovel.base.util.FoxBaseCommonUtils;
import com.lechuan.midunovel.base.util.FoxBaseSPUtils;
import com.lechuan.midunovel.view.FoxSDK;

import androidx.multidex.MultiDex;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class SdkApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        ProSDK.init(this,"4UycwwZv41rwzne1ZXgtQBgDSnPH","3WpyTLfifQyGhvgivxtUjvzXxtkzdceETBU2n5g");
        try {
            final String cookie1 = FoxBaseSPUtils.getInstance().getString("cookie", "");
            if (!FoxBaseCommonUtils.isEmpty(cookie1)){
                //使用sp保持cookie，如果cookie不过期，则一直有效
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                final SPCookieStore spCookieStore = new SPCookieStore(FoxSDK.getContext());
                HttpUrl httpUrl = HttpUrl.parse("http://engine.tuiatest.cn");
                Cookie.Builder builder1 = new Cookie.Builder();
                Cookie cookie = builder1.name("_duibaServiceGroupKey").value("miria-"+cookie1).domain(httpUrl.host()).build();
                spCookieStore.saveCookie(httpUrl, cookie);
                builder.cookieJar(new CookieJarImpl(spCookieStore));
                OkGo.getInstance().setOkHttpClient(builder.build());
            }else {
                OkHttpClient.Builder builder = new OkHttpClient.Builder();
                final SPCookieStore spCookieStore = new SPCookieStore(FoxSDK.getContext());
                spCookieStore.removeAllCookie();
                builder.cookieJar(new CookieJarImpl(spCookieStore));
                OkGo.getInstance().setOkHttpClient(builder.build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }
}