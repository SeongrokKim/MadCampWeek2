package com.example.madcampweek2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RankRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = urlLink.url+"/rank";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    //서버에서 게시글 가져오기
    public RankRequest(Response.Listener<String> listener) {
        super(Method.GET, URL, listener, null);
    }

}