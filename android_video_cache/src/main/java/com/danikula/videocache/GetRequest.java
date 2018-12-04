package com.danikula.videocache;

import android.text.TextUtils;
import android.util.Log;

import com.danikula.videocache.utils.UrlUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.danikula.videocache.Preconditions.checkNotNull;

/**
 * Model for Http GET request.
 *
 * @author Alexey Danilov (danikula@gmail.com).
 */
class GetRequest {
    private static final Logger LOG = LoggerFactory.getLogger("GetRequest");
    private static String m3u8ParentURL;


    private static final Pattern RANGE_HEADER_PATTERN = Pattern.compile("[R,r]ange:[ ]?bytes=(\\d*)-");
    private static final Pattern URL_PATTERN = Pattern.compile("GET /(.*) HTTP");

    public final String uri;
    public final long rangeOffset;
    public final boolean partial;

    public GetRequest(String request) {
        checkNotNull(request);
        long offset = findRangeOffset(request);
        this.rangeOffset = Math.max(0, offset);
        this.partial = offset >= 0;
        this.uri = findUri(request);
    }

    public static GetRequest read(InputStream inputStream) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        StringBuilder stringRequest = new StringBuilder();
        String line;
        while (!TextUtils.isEmpty(line = reader.readLine())) { // until new line (headers ending)
            stringRequest.append(line).append('\n');
        }
        return new GetRequest(stringRequest.toString());
    }

    private long findRangeOffset(String request) {
        Matcher matcher = RANGE_HEADER_PATTERN.matcher(request);
        if (matcher.find()) {
            String rangeValue = matcher.group(1);
            return Long.parseLong(rangeValue);
        }
        return -1;
    }

    private String findUri(String request) {

        Matcher matcher = URL_PATTERN.matcher(request);
        if (matcher.find()) {

            String uri = matcher.group(1);

            LOG.info("uri : "+uri);

            if(!TextUtils.isEmpty(uri)&uri.contains(UrlUtil.M3U8_CACHE_PARAM_KEY)){
                String decodedUri = ProxyCacheUtils.decode(uri);

                //存储 M3U8 地址
                m3u8ParentURL = decodedUri.substring(0,decodedUri.lastIndexOf("/")+1);
                LOG.info("m3u8 parent url : "+m3u8ParentURL);

            }else if(!TextUtils.isEmpty(m3u8ParentURL)&&!TextUtils.isEmpty(uri)&&uri.contains(".ts")
                    &&!uri.toLowerCase().startsWith("http://")
                    &&!uri.toLowerCase().startsWith("https://")
                   ){

                //生成 ts 分片真实地址
                uri = m3u8ParentURL+uri;
                LOG.info("original ts url : "+uri);
            }

            return uri;
        }

        throw new IllegalArgumentException("Invalid request `" + request + "`: url not found!");
    }


    @Override
    public String toString() {
        return "GetRequest{" +
                "rangeOffset=" + rangeOffset +
                ", partial=" + partial +
                ", uri='" + uri + '\'' +
                '}';
    }
}
