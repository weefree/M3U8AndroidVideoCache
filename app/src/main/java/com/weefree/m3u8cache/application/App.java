package com.weefree.m3u8cache.application;

import android.app.Application;

import com.weefree.m3u8cache.HttpProxyManager;

/**
 * Created by weefree on 2018/12/4.
 */
public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        HttpProxyManager.get().init(this);

    }
}
