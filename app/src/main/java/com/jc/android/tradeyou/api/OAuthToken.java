package com.jc.android.tradeyou.api;


import com.google.gson.annotations.SerializedName;

public class OAuthToken {

//    private static final String OAUTH_CONSUMER_KEY = "oauth_consumer_key";
//    private static final String OAUTH_SIGNATURE_METHOD = "oauth_signature_method";
//    private static final String OAUTH_SIGNATURE_METHOD_VALUE = "PLAINTEXT";
//    private static final String OAUTH_SIGNATURE = "oauth_signature";
//
//    private final String consumerKey = "A1AC63F0332A131A78FAC304D007E7D1";
//    private final String consumerSecret = "EC7F18B17A062962C6930A8AE88B16C7";
//
//
//    public String getAuthorization() {
//
//        String authorization = " OAuth "
//                + OAUTH_CONSUMER_KEY + "=\"<" + consumerKey + ">\", "
//                + OAUTH_SIGNATURE_METHOD + "=\"" + OAUTH_SIGNATURE_METHOD_VALUE
//                + "\", " + OAUTH_SIGNATURE + "=\"<" + consumerSecret + ">&\" ";
//
//        return authorization;
//    }

    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public String getAuthorization() {
        return getTokenType() + " " + getAccessToken();
    }
}

