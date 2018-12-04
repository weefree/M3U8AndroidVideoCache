package com.danikula.videocache.utils;

import android.text.TextUtils;

/**
 * Created by weefree on 2018/4/25.
 */
public class UrlUtil {

    public final static String M3U8_CACHE_PARAM_KEY = "with_m3u8_cache";
    public final static String M3U8_CACHE_PARAM_VALUE = "cache.m3u8";


    public static String addM3U8CacheSwitchParam(String url){
        if(TextUtils.isEmpty(url))return url;
        if(url.contains("?")){
            if(url.lastIndexOf("?") == (url.length()-1)){
                url = url+M3U8_CACHE_PARAM_KEY+"="+M3U8_CACHE_PARAM_VALUE;
            }else {
                url = url+"&"+M3U8_CACHE_PARAM_KEY+"="+M3U8_CACHE_PARAM_VALUE;
            }
        }else {
            url = url+"?"+M3U8_CACHE_PARAM_KEY+"="+M3U8_CACHE_PARAM_VALUE;
        }

        return url;
    }


}
