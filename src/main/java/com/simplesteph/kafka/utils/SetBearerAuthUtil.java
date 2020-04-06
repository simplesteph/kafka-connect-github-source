package com.simplesteph.kafka.utils;

import org.apache.http.HttpHeaders;
import org.apache.http.HttpRequest;

public class SetBearerAuthUtil {
    public static HttpRequest SetBearerAuthUtil(HttpRequest request, String bearToken){
        String authHeader = "Bearer" + " " + bearToken;
        request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
        return request;
    }
}
