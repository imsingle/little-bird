package com.xsg.config;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xue shengguo
 * @datetime 17/8/10 下午5:29
 */
public class BirdConfig {
    private int HTTP_CONN_TIMEOUT;
    private ConcurrentHashMap<String, String> properties = new ConcurrentHashMap();

    private BirdConfig () {
        HttpConfig httpConfig = HttpConfig.of();
        httpConfig.fill(properties);

        System.setProperty("hystrix.command.default.execution.isolation.thread.timeoutInMilliseconds", "5000");
    }

    public static BirdConfig of() {
        return new BirdConfig();
    }

    public String get(String key) {
        return properties.get(key);
    }
}
