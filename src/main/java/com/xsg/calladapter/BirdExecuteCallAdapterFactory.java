package com.xsg.calladapter;

import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * 这个callapdater会直接执行call获得结果
 * @author xue shengguo
 * @datetime 17/8/18 下午3:22
 */
public class BirdExecuteCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {

        return new CallAdapter<Object, Object>() {
            @Override public Type responseType() {
                return returnType;
            }

            @Override public Object adapt(Call<Object> call) {
                Response response;
                try {
                    response = call.execute();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                return response.body();
            }
        };
    }
}
