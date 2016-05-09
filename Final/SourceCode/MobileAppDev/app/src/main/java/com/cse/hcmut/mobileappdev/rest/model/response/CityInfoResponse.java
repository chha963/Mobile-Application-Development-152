package com.cse.hcmut.mobileappdev.rest.model.response;

import com.cse.hcmut.mobileappdev.rest.model.resultset.CityInfoResultSet;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dinhn on 5/8/2016.
 */
public class CityInfoResponse {

    @SerializedName("responseCd")
    int responseCd;

    @SerializedName("responseMsg")
    String responseMsg;

    @SerializedName("resultSet")
    CityInfoResultSet cityInfoResultSet;

    public int getResponseCd() {
        return responseCd;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public CityInfoResultSet getCityInfoResultSet() {
        return cityInfoResultSet;
    }
}
