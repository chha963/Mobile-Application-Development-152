package com.cse.hcmut.mobileappdev.rest.model.response;

import com.cse.hcmut.mobileappdev.rest.model.resultset.SignUpResultSet;

import java.util.List;

/**
 * Created by dinhn on 5/8/2016.
 */
public class SignUpResponse {

    int responseCd;
    String responseMsg;
    List<SignUpResultSet> resultSet;

    public int getResponseCd() {
        return responseCd;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public List<SignUpResultSet> getResultSet() {
        return resultSet;
    }
}
