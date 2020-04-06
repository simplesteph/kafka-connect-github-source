package com.simplesteph.kafka.utils;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;

import java.nio.charset.StandardCharsets;

public class SetBasicAuthUtil {
    public static HttpRequest SetBasicAuth(HttpRequest request,String username,String password ){
        String auth = username + ":" + password;
        byte[] encodedAuth = Base64.encodeBase64(
                auth.getBytes(StandardCharsets.ISO_8859_1));
        String authHeader = "Basic " + new String(encodedAuth);
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        return request;
    }

}
