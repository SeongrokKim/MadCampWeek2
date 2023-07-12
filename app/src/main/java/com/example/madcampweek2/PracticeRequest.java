package com.example.madcampweek2;


import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PracticeRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = urlLink.url+"/practice";
    private Map<String, String> map;

    public PracticeRequest(String uid, String time, String count, String journal, String category, String title, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        String date = LocalDate.now().toString();
//        if(time == null){
//            time = "NULL";
//        }


        map = new HashMap<>();
        map.put("uid", uid);
        map.put("date", date);
        map.put("time", time);
        map.put("count", count);
        map.put("journal", journal);
        map.put("category", category);
        map.put("title", title);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}

