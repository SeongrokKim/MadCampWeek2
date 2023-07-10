package com.example.madcampweek2;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DeleteBoardRequest extends StringRequest {

    //서버 URL 설정
    final static private String URL = urlLink.url+"/board_del";
    private Map<String, String> map;

    public DeleteBoardRequest(String NO , Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("no", NO);
    }

    @Override
    protected Map<String, String>getParams() throws AuthFailureError {
        return map;
    }
}
