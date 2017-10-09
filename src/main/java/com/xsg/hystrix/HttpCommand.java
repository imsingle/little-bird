package com.xsg.hystrix;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * @author xue shengguo
 * @datetime 17/8/14 下午5:35
 */
@Slf4j
public class HttpCommand extends HystrixCommand<Response> {
    private Interceptor.Chain chain;
    private Request request;
    public HttpCommand(String group, Interceptor.Chain chain, Request request) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey(group)));
        this.chain = chain;
        this.request = request;
    }

    @Override
    protected Response run() throws Exception {
        log.info("hystrix thread...{}", Thread.currentThread().getName());
        Response response = chain.proceed(request);
        return response;
    }

    @Override
    protected Response getFallback() {
        log.info("hystrix fallback ...");

        throw new RuntimeException("fallback");
    }

}
