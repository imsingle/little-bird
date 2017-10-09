package com.xsg;

import com.xsg.calladapter.BirdExecuteCallAdapterFactory;
import com.xsg.config.BirdConfig;
import lombok.Data;
import okhttp3.Interceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import java.util.LinkedList;

/**
 * @author xue shengguo
 * @datetime 17/8/10 下午5:29
 */
@Data
public class BirdClient {
    private String serviceName;
    private BirdConfig config;
    private LinkedList<Interceptor> interceptors = new LinkedList();

    public BirdClient(String serviceName, BirdConfig config) {
        this.serviceName = serviceName;
        this.config = config;
    }

    public static BirdClient of(String serviceName) {
        return of(serviceName, null, null);
    }

    public static BirdClient of(String serviceName, String tag) {
        return of(serviceName, tag, null);
    }

    public static BirdClient of(String serviceName, String tag, String url) {
        return new Builder().withTag(tag).withUrl(url).build();
    }

    public static BirdClient of(String serviceName, String tag, String url, BirdConfig config) {
        return new Builder().withTag(tag).withUrl(url).withConfig(config).build();
    }

    public <T> T create(Class<T> name) {
        Retrofit retrofit = new Retrofit.Builder()
                                        .client(BirdHttpClient.of(config, interceptors))
                                        .baseUrl("http://localhost")
                                        .validateEagerly(true)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .addCallAdapterFactory(new BirdExecuteCallAdapterFactory())
                                        .build();
        T t = retrofit.create(name);
        return t;
    }

    /**
     * 添加拦截器
     * @param interceptor
     */
    public void addInterceptor(Interceptor interceptor) {
        interceptors.add(interceptor);
    }

    public void setInterceptor(LinkedList<Interceptor> interceptors) {
        this.interceptors.addAll(interceptors);
    }

    public LinkedList<Interceptor> getInterceptors() {
        return interceptors;
    }

    public boolean hasInterceptor() {
        return interceptors.size() > 0;
    }

    public static class Builder {
        private String serviceName;
        private String tag;
        private String url;
        private BirdConfig config;

        public Builder withConfig(BirdConfig config) {
            this.config = config;
            return this;
        }

        public Builder withTag(String tag) {
            this.tag = tag;
            return this;
        }

        public Builder withServiceName(String name) {
            this.serviceName = name;
            return this;
        }

        public Builder withUrl(String url) {
            this.url = url;
            return this;
        }

        public BirdClient build() {
            if (config == null) config = BirdConfig.of();
            if (tag != null) config.setTag(tag);
            if (url != null) config.setUrl(url);
            return new BirdClient(serviceName, config);
        }
    }
}
