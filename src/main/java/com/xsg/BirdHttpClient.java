package com.xsg;

import com.xsg.config.BirdConfig;
import com.xsg.config.HttpConfig;
import com.xsg.http.RequestProcessor;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.OkHttpClient.Builder;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;

/**
 * @author xue shengguo
 * @datetime 17/8/10 下午5:34
 */
public class BirdHttpClient {

    public static OkHttpClient of(OkHttpClient.Builder builder) {
        return builder.build();
    }

    public static OkHttpClient of(BirdConfig birdConfig) {
        return of(birdConfig, null);
    }

    public static OkHttpClient of(BirdConfig birdConfig, LinkedList<Interceptor> interceptors) {
        Builder builder = new Builder();
        builder.connectTimeout(Long.valueOf(birdConfig.get(HttpConfig.CONFIG_KEY_CONN_TIMEOUT)), TimeUnit.MILLISECONDS)
                .readTimeout(Long.valueOf(birdConfig.get(HttpConfig.CONFIG_KEY_READ_TIMEOUT)), TimeUnit.MILLISECONDS)
                .writeTimeout(Long.valueOf(birdConfig.get(HttpConfig.CONFIG_KEY_WRITE_TIMEOUT)), TimeUnit.MILLISECONDS)
                //拦截请求，对请求结果做类型转换
                .addInterceptor(new RequestProcessor(birdConfig));

        //添加用户注册的拦截器
        if (interceptors != null) {
            interceptors.forEach(interceptor -> builder.addInterceptor(interceptor));
        }
        return builder.build();
    }
}
