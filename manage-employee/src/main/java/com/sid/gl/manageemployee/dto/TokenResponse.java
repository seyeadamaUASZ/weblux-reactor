package com.sid.gl.manageemployee.dto;

import com.sid.gl.manageemployee.models.Token;
import lombok.Data;

@Data
public class TokenResponse {
    private String accessToken;
    private String refreshToken;

    public TokenResponse() {

    }

    public TokenResponse(Token token) {
        this.accessToken = token.getAccessToken();
        this.refreshToken = token.getRefreshToken();
    }
}
