package com.cse.hcmut.mobileappdev.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.cse.hcmut.mobileappdev.rest.model.response.CityInfoResponse;
import com.cse.hcmut.mobileappdev.rest.services.ServiceGenerator;
import com.cse.hcmut.mobileappdev.rest.services.signup.CityInfoService;

import java.io.IOException;

import retrofit2.Call;

/**
 * Created by dinhn on 5/3/2016.
 */
public class NetworkUtils {

    public static boolean isNetworkConnected(Context aContext) {
        // This implementation don't run in some emulator when ICMP was blocked
//        try {
//            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1    www.google.com");
//            int returnVal = p1.waitFor();
//            return (returnVal == 0);
//        } catch (Exception e) {
//            return false;
//        }

        // This implementation must be run in background
        // First check if any network available
        if (isNetworkAvailable(aContext)) {
            // Try to ping to google first
            if (canConnectServer()) {
                return true;
            }
            // If fail, try ping 1 more and return result
            else {
                return canConnectServer();
            }
        } else {
            return false;
        }
    }

    private static boolean canConnectServer() {
        final CityInfoService cityInfoService = ServiceGenerator.createService(CityInfoService.class);

        Call<CityInfoResponse> call = cityInfoService.getCityInfo();

        boolean canConnect = false;

        try {
            int code = call.execute().code();
            // code successful
            if (code >= 200 && code < 300) {
                canConnect = true;
            }
        } catch (IOException e) {
            canConnect = false;
        }

        return canConnect;
    }

    private static boolean isNetworkAvailable(Context ct) {
        ConnectivityManager connectivityManager = (ConnectivityManager) ct.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }

}
