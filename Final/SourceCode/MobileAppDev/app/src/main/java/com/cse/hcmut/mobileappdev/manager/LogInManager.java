package com.cse.hcmut.mobileappdev.manager;

import android.content.Context;

import com.cse.hcmut.mobileappdev.base.list.MyProductAccountArrayList;
import com.cse.hcmut.mobileappdev.models.account.Account;
import com.cse.hcmut.mobileappdev.models.tinydb.TinyDB;

/**
 * Created by dinhn on 4/28/2016.
 */
public class LogInManager {

    // ---------------------------------------------------------------------------------------------
    // TYPES
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // ABSTRACT METHODS
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // STATIC FIELDS
    // ---------------------------------------------------------------------------------------------

    private static LogInManager sLoginManager = null;

    public static final String KEY_LOCAL_TOKEN = "key_local_token";

    public static final String KEY_LOCAL_USERNAME = "key_local_username";

    // ---------------------------------------------------------------------------------------------
    // STATIC METHODS
    // ---------------------------------------------------------------------------------------------

    public static LogInManager get(Context context) {

        if (sLoginManager == null) {
            sLoginManager = new LogInManager(context);
        } else { // Update current context
            sLoginManager.mContext = context;
        }
        return sLoginManager;
    }

    public static void clear() {
        sLoginManager = null;
    }

    // ---------------------------------------------------------------------------------------------
    // FIELDS
    // ---------------------------------------------------------------------------------------------

    private Context mContext = null;

    private Account mAccount = null;

    private String mToken = "";

    private String mUsername = "";

    // ---------------------------------------------------------------------------------------------
    // CONSTRUCTORS
    // ---------------------------------------------------------------------------------------------

    public LogInManager(Context context) {
        // required empty constructor
        mContext = context;
        mToken = getTokenLocal();
        mUsername = getUsernameLocal();
        initAccount();
    }

    // ---------------------------------------------------------------------------------------------
    // IMPLEMENT METHODS (METHODS OVERRIDE FROM ABSTRACT PARENT CLASS + FROM IMPLEMENTS)
    // ---------------------------------------------------------------------------------------------

    // ---------------------------------------------------------------------------------------------
    // METHODS
    // ---------------------------------------------------------------------------------------------
    //int type, String id, String bannerImageId, String avatarImageId, String username, String password, String email, String phoneNumber, String address, MyProductAccountArrayList personalProductList
    private void initAccount() {
        mAccount = new Account(
                Account.TYPE_NORMAL,
                "1",
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoviApp-PersonalInfo/header-personal-info_zpswgfonsjc.png",
                "http://i1380.photobucket.com/albums/ah166/nhudinh2103/GoViApp-Navigation/miss_fortune_icon_zps4hswrc3v.png",
                mUsername,
                "dinh123456",
                "nhudinh2103@gmail.com",
                "0903759354",
                "Duong ABC, TP.DEF",
                new MyProductAccountArrayList()
        );
    }

    // ---------------------------------------------------------------------------------------------
    // GETTERS / SETTERS
    // ---------------------------------------------------------------------------------------------

    public Account getAccount() {
        return mAccount;
    }

    public String getToken() {
        return mToken;
    }

    public String getTokenLocal() {
        TinyDB tinyDB = new TinyDB(mContext);
        String token = tinyDB.getString(KEY_LOCAL_TOKEN);
        return token;
    }

    public String getUsernameLocal() {
        TinyDB tinyDB = new TinyDB(mContext);
        String username = tinyDB.getString(KEY_LOCAL_USERNAME);
        return username;
    }

    public void setToken(String token) {
        mToken = token;
    }

    public void setUsername(String username) {
        mUsername = username;
    }

    public void updateAndStoreTokenLocal(String token) {
        setToken(token);
        TinyDB tinyDB = new TinyDB(mContext);
        tinyDB.putString(KEY_LOCAL_TOKEN, mToken);
    }

    public void updateAndStoreUsernameLocal(String username) {
        setUsername(username);
        mAccount.setUsername(username);
        TinyDB tinyDB = new TinyDB(mContext);
        tinyDB.putString(KEY_LOCAL_USERNAME, mUsername);
    }

    public void removeLoginInfoLocal() {
        TinyDB tinyDB = new TinyDB(mContext);
        tinyDB.putString(KEY_LOCAL_TOKEN, "");
        tinyDB.putString(KEY_LOCAL_USERNAME, "");
    }
}
