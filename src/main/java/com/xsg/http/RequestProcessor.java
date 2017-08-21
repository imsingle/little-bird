package com.xsg.http;

import com.xsg.exception.BirdClientApiException;
import com.xsg.hystrix.HttpCommand;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author xue shengguo
 * @datetime 17/8/14 下午2:38
 */
@Slf4j
public class RequestProcessor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request origin = chain.request();
        Request request;
        try {
            request = origin.newBuilder()
                    .addHeader("Content-type", "application/json")
                    .url(getFirstAvailableServer().toString() + new URI(origin.url().toString()).getRawPath())
                    .method(origin.method(), origin.body())
                    .build();
        } catch (URISyntaxException e) {
            log.warn("接口URI格式有误,查看BirdHttpClient配置", e);
            throw new RuntimeException(e);
        }

        //todo 要获取那个服务调用的请求，间接获取serviceName
        HttpCommand httpCommand = new HttpCommand("demo", chain, request);
        Response response = httpCommand.execute();
        if (!response.isSuccessful()) {
            throw new BirdClientApiException(response.code(), response.message());
        }
        return response;
    }

    //loadBalance + consul
    private URI getFirstAvailableServer() throws URISyntaxException {
        return new URI("http://localhost:8801");
    }
}
