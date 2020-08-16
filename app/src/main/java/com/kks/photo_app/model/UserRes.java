package com.kks.photo_app.model;

import com.google.gson.annotations.SerializedName;

public class UserRes {

    @SerializedName("success")

    boolean success;

    @SerializedName("token")
    String token;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
