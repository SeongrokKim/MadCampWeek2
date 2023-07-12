package com.example.madcampweek2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class PwChangeRequest extends StringRequest {
    //서버 url 설정
    final static private String URL=urlLink.url+"/edit_pw";
    private Map<String, String> map;

    public PwChangeRequest(String uid, String pw, String newPw, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        map = new HashMap<>();
        map.put("uid", uid);
        map.put("pw", pw);
        map.put("new_pw", newPw);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}