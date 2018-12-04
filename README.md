# M3U8AndroidVideoCache

在 [AndroidVideoCache](https://github.com/danikula/AndroidVideoCache) 做的修改，支持 M3U8 点播模式缓存，分片地址需为相对路径

## 原理

在请求 M3U8 文件时记录 M3U8 真实地址，使用此地址将后面的 ts 分片转换为真实地址

只简单修改了几行代码，在 GetRequest(https://github.com/weefree/M3U8AndroidVideoCache/blob/master/android_video_cache/src/main/java/com/danikula/videocache/GetRequest.java) 类的 findUri 方法

## 问题

不支持多个 M3U8 地址同时播放