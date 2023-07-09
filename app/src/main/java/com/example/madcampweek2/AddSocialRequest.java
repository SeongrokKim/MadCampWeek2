package com.example.madcampweek2;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class AddSocialRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = "https://99f7-192-249-19-234.ngrok-free.app/board";
    private Map<String, String> map;

    public AddSocialRequest(String uid, String title, String content, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        String datetime = LocalDateTime.now().toString();
        datetime = datetime.replaceAll("[^0-9:-]", " ");
        datetime = datetime.substring(0, datetime.length()-4);

        map = new HashMap<>();
        map.put("uid", uid);
        map.put("datetime", datetime);
        map.put("title", title);
        map.put("text", content);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
