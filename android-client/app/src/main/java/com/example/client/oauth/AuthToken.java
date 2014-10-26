package com.example.client.oauth;

import com.google.gson.annotations.SerializedName;

public class AuthToken {
    @SerializedName("access_token")
    private String accessToken;

    @SerializedName("token_type")
    private String tokenType;

    @SerializedName("expires_in")
    private Long expiresIn;

    @SerializedName("refresh_token")
    private String refreshToken;

    public String getAccessToken() {
        return "1f8988d941c8a0e48a0a56eca21ad9ba466f5a23e17343e9f8459ec19166a94c";
    }

    public String getTokenType() {
        return tokenType;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
