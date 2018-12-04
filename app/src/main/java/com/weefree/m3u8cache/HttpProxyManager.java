package com.weefree.m3u8cache;

import android.content.Context;

import com.danikula.videocache.HttpProxyCacheServer;


/**
 * Created by weefree on 2018/3/30.
 */

public class HttpProxyManager {

    public HttpProxyCacheServer httpProxyCacheServer;
    private Context context;

    public static class SingletonHolder{
        public static HttpProxyManager instance = new HttpProxyManager();
    }

    public static HttpProxyManager get(){
        return SingletonHolder.instance;
    }

    public void init(Context context){
        this.context = context;
    }

    public HttpProxyCacheServer getProxy() {
        if(httpProxyCacheServer == null){
           httpProxyCacheServer = new HttpProxyCacheServer(context);
        }

        return httpProxyCacheServer;
    }

    public String getProxyUrl(String originUrl){
        return getProxy().getProxyUrl(originUrl,false);
    }

}
