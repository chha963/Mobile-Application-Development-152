package com.cse.hcmut.mobileappdev.rest.model.resultset;

import com.google.gson.annotations.SerializedName;

/**
 * Created by dinhn on 5/3/2016.
 */
public class LoginResultSet {
    @SerializedName("token")
    String token;

    @SerializedName("username")
    String username;

    @SerializedName("avatar")
    String avatar;

    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatar() {
        return avatar;
    }
}
