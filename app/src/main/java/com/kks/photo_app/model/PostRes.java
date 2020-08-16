package com.kks.photo_app.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PostRes {

    @SerializedName("success")
    @Expose
    private Boolean success;

    @SerializedName("items")
    @Expose
    private List<PhotoItem> items = null;

    @SerializedName("cnt")
    @Expose
    private Integer cnt;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<PhotoItem> getItems() {
        return items;
    }

    public void setItems(List<PhotoItem> items) {
        this.items = items;
    }

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}
