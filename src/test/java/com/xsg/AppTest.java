package com.xsg;


import com.xsg.config.BirdConfig;
import com.xsg.dto.User;
import org.testng.annotations.Test;
import retrofit2.Call;
import retrofit2.http.GET;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest {

    /**
     * Rigourous Test :-)
     */
    @Test(enabled = false)
    public void testApp() throws IOException {
        String serviceName = "demo";
        BirdConfig config = BirdConfig.of().addInterceptor(chain -> chain.proceed(chain.request()));
        BirdClient appTest = BirdClient.of(serviceName, "local", "http://localhost", config);
        DemoApi demoApi = appTest.create(DemoApi.class);

        User call = demoApi.baidu();
        System.out.println(call);
    }
}

interface DemoApi {
    @GET("user")
    User baidu();

    @GET("user/1index1/a")
    Call<User> birdbaidu1();

    @GET("user2")
    Call<User> baidu2();
}
