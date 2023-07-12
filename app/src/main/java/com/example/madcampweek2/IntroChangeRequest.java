package com.example.madcampweek2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class IntroChangeRequest extends StringRequest {
    //서버 url 설정
    final static private String URL=urlLink.url+"/edit_intro";
    private Map<String, String> map;

    public IntroChangeRequest(String uid, String intro, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("uid", uid);
        map.put("intro", intro);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}