/*
 * Copyright (C) 2015 Bilibili
 * Copyright (C) 2015 Zhang Rui <bbcallen@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.weefree.m3u8cache.activities;

import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.danikula.videocache.utils.UrlUtil;

import com.weefree.m3u8cache.HttpProxyManager;
import tv.danmaku.ijk.media.example.R;

public class M3u8TestActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = M3u8TestActivity.class.getName();
    private final static String M3U8_TEST_URL = "http://devimages.apple.com/iphone/samples/bipbop/gear1/prog_index.m3u8";

    private Button testBtn;
    private TextView logTv;

    private String proxyUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_m3u8_test);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        testBtn = (Button) findViewById(R.id.test_btn);
        logTv = (TextView) findViewById(R.id.log_tv);

        String m3u8Url = UrlUtil.addM3U8CacheSwitchParam(M3U8_TEST_URL);

        Log.d(TAG,"m3u8 url : "+m3u8Url);

        proxyUrl = HttpProxyManager.get().getProxyUrl(m3u8Url);

        Log.d(TAG,"proxy url : "+proxyUrl);

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Original URL：\n")
                .append(M3U8_TEST_URL).append("\n").append("\n")
                .append("Proxy URL：\n")
                .append(proxyUrl).append("\n");
        logTv.setText(stringBuilder.toString());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_m3u8_test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            SettingsActivity.intentTo(this);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.test_btn:
                VideoActivity.intentTo(this, proxyUrl, "Cache Test");
                break;
        }
    }
}
