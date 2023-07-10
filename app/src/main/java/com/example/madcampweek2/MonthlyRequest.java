package com.example.madcampweek2;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MonthlyRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = urlLink.url+"/monthly";
    private Map<String, String> map;

    public MonthlyRequest(String uid, String year, String month, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("uid", uid);
        map.put("year", year);
        map.put("month", month);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
