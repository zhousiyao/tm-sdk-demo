package com.commonly.undertone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lechuan.midunovel.base.okgo.OkGo;
import com.lechuan.midunovel.base.okgo.cookie.CookieJarImpl;
import com.lechuan.midunovel.base.okgo.cookie.store.SPCookieStore;
import com.lechuan.midunovel.base.util.FoxBaseCommonUtils;
import com.lechuan.midunovel.base.util.FoxBaseSPUtils;
import com.lechuan.midunovel.view.FoxSDK;

import androidx.appcompat.app.AppCompatActivity;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

public class TestToolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tool);

        final EditText editCookie = findViewById(R.id.editCookie);
        final Button nsButtonCookie = findViewById(R.id.nsButtonCookie);
        nsButtonCookie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String cookies = editCookie.getText().toString().trim();
                if (!FoxBaseCommonUtils.isEmpty(cookies)) {
                    //使用sp保持cookie，如果cookie不过期，则一直有效
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    final SPCookieStore spCookieStore = new SPCookieStore(FoxSDK.getContext());
                    HttpUrl httpUrl = HttpUrl.parse("http://engine.tuiatest.cn");
                    Cookie.Builder builder1 = new Cookie.Builder();
                    Cookie cookie = builder1.name("_duibaServiceGroupKey").value("miria-"+cookies).domain(httpUrl.host()).build();
                    spCookieStore.saveCookie(httpUrl, cookie);
                    builder.cookieJar(new CookieJarImpl(spCookieStore));
                    OkGo.getInstance().setOkHttpClient(builder.build());
                    FoxBaseSPUtils.getInstance().setString("cookie",cookies);
                } else {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder();
                    final SPCookieStore spCookieStore = new SPCookieStore(FoxSDK.getContext());
                    spCookieStore.removeAllCookie();
                    builder.cookieJar(new CookieJarImpl(spCookieStore));
                    OkGo.getInstance().setOkHttpClient(builder.build());
                }
            }
        });

        findViewById(R.id.nsButtonDemo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TestToolActivity.this, DemoActivity.class));
            }
        });
        findViewById(R.id.nsButtonDemoTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FoxSDK.testCrash();
            }
        });

        findViewById(R.id.nsButtonDemoAppTest).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                throw new IllegalStateException("this is a test");
            }
        });


    }
}
