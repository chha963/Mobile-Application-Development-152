package com.cse.hcmut.mobileappdev.rest.model.response;

import com.cse.hcmut.mobileappdev.rest.model.resultset.LoginResultSet;

/**
 * Created by dinhn on 5/3/2016.
 */
public class LoginResponse {

    int responseCd;
    String responseMsg;
    LoginResultSet resultSet;

    public int getResponseCd() {
        return responseCd;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public LoginResultSet getResultSet() {
        return resultSet;
    }
}
