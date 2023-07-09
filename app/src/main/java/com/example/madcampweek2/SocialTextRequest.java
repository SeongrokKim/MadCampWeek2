package com.example.madcampweek2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SocialTextRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = "https://233b-192-249-19-234.ngrok-free.app/get_social";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    //서버에서 게시글 가져오기
    public SocialTextRequest(Response.Listener<String> listener) {
        super(Method.GET, URL, listener, null);
    }

}