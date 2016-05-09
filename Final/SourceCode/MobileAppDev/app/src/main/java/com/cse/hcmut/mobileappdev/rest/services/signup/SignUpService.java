package com.cse.hcmut.mobileappdev.rest.services.signup;

import com.cse.hcmut.mobileappdev.rest.model.response.SignUpResponse;
import com.cse.hcmut.mobileappdev.rest.model.UserSignUp;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by dinhn on 5/8/2016.
 */
public interface SignUpService {
    @POST("register")
    Call<SignUpResponse> register(@Body UserSignUp userRegister);
}
