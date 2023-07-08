package com.example.madcampweek2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SocialRegisterRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = "https://0e6c-192-249-19-234.ngrok-free.app/sregister";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public SocialRegisterRequest(String UserName, String photo,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        String UID = UUID.randomUUID().toString();
        map = new HashMap<>();
        map.put("uid", UID);
        map.put("name", UserName);
        map.put("photo", photo);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}