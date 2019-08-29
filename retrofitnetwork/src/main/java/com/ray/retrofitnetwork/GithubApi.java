package com.ray.retrofitnetwork;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Author : hikobe8@github.com
 * Time : 2019-08-29 22:55
 * Description :
 */
public interface GithubApi {

    @GET("users/{user}")
    Call<ResponseBody> requestUserInfo(@Path("user") String user);

}
