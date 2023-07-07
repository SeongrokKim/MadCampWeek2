package com.example.madcampweek2;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("/user_info")
    Call<ResponseBody> getFunc(@Query("data") String data);

//    @FormUrlEncoded
//    @POST("/register")
//    Call<WriteResult> setPost(@Field("title") String title
//            , @Field("content") String content
//            , @Field("writerId") String writerId);
//
//    @GET("/api/v1/user")
//    Call<UserGet> user(@Field("Token") String Token);

    @FormUrlEncoded
    @POST("/register")
    Call<ResponseBody> postFunc(@Field("id") String id,
                                @Field("pw") String pw,
                                @Field("name") String name);

    @FormUrlEncoded
    @PUT("/register/put/{id}")
    Call<ResponseBody> putFunc(@Path("id") String id, @Field("data") String data);

    @DELETE("/register/delete/{id}")
    Call<ResponseBody> deleteFunc(@Path("id") String id);
}
