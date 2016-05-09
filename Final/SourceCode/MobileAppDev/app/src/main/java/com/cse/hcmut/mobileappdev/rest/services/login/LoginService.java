package com.cse.hcmut.mobileappdev.rest.services.login;

import com.cse.hcmut.mobileappdev.rest.model.response.LoginResponse;
import com.cse.hcmut.mobileappdev.rest.model.UserLogin;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by dinhn on 5/3/2016.
 */
public interface LoginService {
    @POST("login")
    Call<LoginResponse> basicLogin(@Body UserLogin user);
}
