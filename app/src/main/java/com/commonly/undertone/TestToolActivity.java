package com.commonly.undertone;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.lechuan.midunovel.view.FoxSDK;

import androidx.appcompat.app.AppCompatActivity;

public class TestToolActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_tool);

        final EditText editCookie = findViewById(R.id.editCookie);
        final Button nsButtonCookie = findViewById(R.id.nsButtonCookie);

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
