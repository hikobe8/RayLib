package com.ray.retrofitnetwork;

import android.util.Log;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Author : hikobe8@github.com
 * Time : 2019-08-29 22:53
 * Description :
 */
public class HttpRequest {

    private Retrofit mRetrofit;

    private HttpRequest() {
        //创建Retrofit对象
        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(ScalarsConverterFactory.create())
                //设置ScalarsConverter
                .build();
    }

    private static class InstanceHolder {
        static HttpRequest instance = new HttpRequest();
    }

    public static HttpRequest getInstance() {
        return InstanceHolder.instance;
    }

    public void requestPlainText(final HttpCallback httpCallback) {
        GithubApi githubApi = mRetrofit.create(GithubApi.class);
        Call<ResponseBody> call = githubApi.requestUserInfo("hikobe8");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    httpCallback.onSuccess(response.body().string());
                } catch (Exception e) {
                    httpCallback.onError(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                httpCallback.onError(t.getMessage());
            }
        });
    }

    public interface HttpCallback{
        void onSuccess(String response);
        void onError(String reason);
    }

}
