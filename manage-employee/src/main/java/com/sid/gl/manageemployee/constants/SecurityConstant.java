package com.sid.gl.manageemployee.constants;

public class SecurityConstant {

    public static final String SECRET = "05367DA0DF421763611B9B58AF7514B93777O094FB882F72A347FEB5D3EBAAFD";
    public static final String HEADER_NAME = "Authorization";
    public static final Long ACCESS_TOKEN_EXPIRATION_TIME = 1000L * 60 * 20160;
    public static final Long REFRESH_TOKEN_EXPIRATION_TIME = 1000L * 60 * 43200;
    public static final int BUFFER_MAX_SIZE = 100 * 1024 * 1024;

}
