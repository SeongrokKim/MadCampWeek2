package com.example.madcampweek2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MyBoardRequest extends StringRequest {
    //서버 url 설정
    final static private String URL=urlLink.url+"/user_post";
    private Map<String, String> map;

    public MyBoardRequest(String uid, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("uid", uid);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}