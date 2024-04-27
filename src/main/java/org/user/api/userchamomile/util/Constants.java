package org.user.api.userchamomile.util;

import io.jsonwebtoken.Jwts;

import javax.crypto.SecretKey;

public class Constants {

    public static final SecretKey SECRET_KEY =  Jwts.SIG.HS256.key().build();
    public static final String PREFIX_TOKEN = "Bearer ";
    public static final String HEADER_AUTHORIZATION = "Authorization";
}
