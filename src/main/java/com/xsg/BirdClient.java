package com.xsg;

import com.xsg.calladapter.BirdExecuteCallAdapterFactory;
import com.xsg.config.BirdConfig;
import lombok.Data;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author xue shengguo
 * @datetime 17/8/10 下午5:29
 */
@Data
public class BirdClient {
    private String serviceName;
    private BirdConfig config;

    private BirdClient(BirdConfig config) {
        this.config = config;
    }

    public static BirdClient of(String serviceName) {
        return new Builder().withConfig(BirdConfig.of()).build();
    }

    public <T> T create(Class<T> name) {

        Retrofit retrofit = new Retrofit.Builder()
                                        .client(BirdHttpClient.of(config))
                                        .baseUrl("http://localhost")
                                        .validateEagerly(true)
                                        .addConverterFactory(GsonConverterFactory.create())
                                        .addCallAdapterFactory(new BirdExecuteCallAdapterFactory())
                                        .build();
        T t = retrofit.create(name);
        return t;
    }

    public static class Builder {
        private BirdConfig config;

        public Builder withConfig(BirdConfig config) {
            this.config = config;
            return this;
        }

        public BirdClient build() {
            return new BirdClient(config);
        }
    }
}
