package com.cse.hcmut.mobileappdev.rest.services.signup;

import com.cse.hcmut.mobileappdev.rest.model.response.CityInfoResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by dinhn on 5/8/2016.
 */
public interface CityInfoService {
    @GET("cityInfo")
    Call<CityInfoResponse> getCityInfo();
}
