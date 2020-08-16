package com.kks.photo_app.api;

import com.kks.photo_app.model.UserReq;
import com.kks.photo_app.model.UserRes;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface UserApi {

    @POST("/api/v1/user")
    Call<UserRes> createUser(@Body UserReq userReq);

    @POST("/api/v1/user/login")
    Call<UserRes> loginUser(@Body UserReq userReq);

    @DELETE("/api/v1/user/logout")
    Call<UserRes> logoutUser(@Header("Authorization") String token);

}
