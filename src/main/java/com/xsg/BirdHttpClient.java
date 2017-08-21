package com.xsg;

import com.xsg.config.BirdConfig;
import com.xsg.config.HttpConfig;
import com.xsg.http.RequestProcessor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

import java.util.concurrent.TimeUnit;

/**
 * @author xue shengguo
 * @datetime 17/8/10 下午5:34
 */
public class BirdHttpClient {

    public static OkHttpClient of(BirdConfig birdConfig) {
        Builder builder = new Builder();
        builder.connectTimeout(Long.valueOf(birdConfig.get(HttpConfig.CONFIG_KEY_CONN_TIMEOUT)), TimeUnit.MILLISECONDS)
                .readTimeout(Long.valueOf(birdConfig.get(HttpConfig.CONFIG_KEY_READ_TIMEOUT)), TimeUnit.MILLISECONDS)
                .writeTimeout(Long.valueOf(birdConfig.get(HttpConfig.CONFIG_KEY_WRITE_TIMEOUT)), TimeUnit.MILLISECONDS)
                .addInterceptor(new RequestProcessor());
        return builder.build();
    }

    public static OkHttpClient of(OkHttpClient.Builder builder) {
        return builder.build();
    }
}
