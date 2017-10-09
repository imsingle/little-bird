package com.xsg.http;

import com.xsg.config.BirdConfig;
import com.xsg.exception.BirdClientApiException;
import com.xsg.hystrix.HttpCommand;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang.StringUtils;

import java.io.IOException;

/**
 * @author xue shengguo
 * @datetime 17/8/14 下午2:38
 */
@Slf4j
public class RequestProcessor implements Interceptor {
    private BirdConfig config;

    public RequestProcessor(BirdConfig config) {
        this.config = config;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request origin = chain.request();
        Request request;
        StringBuilder url = new StringBuilder();
        url.append(getFirstAvailableServer().toString()).append(origin.url().encodedPath());
        String encodedQuery = origin.url().encodedQuery();
        if (StringUtils.isNotEmpty(encodedQuery)) url.append("?").append(encodedQuery);

        request = origin.newBuilder()
                .addHeader("Content-type", "application/json")
                .url(url.toString())
                .method(origin.method(), origin.body())
                .build();

        //todo 要获取那个服务调用的请求，间接获取serviceName
        HttpCommand httpCommand = new HttpCommand("demo", chain, request);
        Response response = httpCommand.execute();
        if (!response.isSuccessful()) {
            throw new BirdClientApiException(response.code(), response.message());
        }
        return response;
    }

    //TODO loadBalance + consul
    private String getFirstAvailableServer() {
        if (config.getTag().equalsIgnoreCase("local")) return config.getUrl();
        return "http://localhost:8080";
    }
}
