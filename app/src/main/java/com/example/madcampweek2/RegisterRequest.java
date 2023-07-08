package com.example.madcampweek2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RegisterRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = "https://0e6c-192-249-19-234.ngrok-free.app/register";
    private Map<String, String> map;
    //private Map<String, String>parameters;

    public RegisterRequest(String UserId, String UserPwd, String UserName,Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        String UID = UUID.randomUUID().toString();
        map = new HashMap<>();
        map.put("uid", UID);
        map.put("id", UserId);
        map.put("pw", UserPwd);
        map.put("name", UserName);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}