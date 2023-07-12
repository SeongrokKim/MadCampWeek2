package com.example.madcampweek2;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class WriteCommentRequest extends StringRequest {
    //서버 url 설정
    final static private String URL=urlLink.url+"/write_comment";
    private Map<String, String> map;

    public WriteCommentRequest(String post_num, String writerUID, String content, Response.Listener<String> listener){
        super(Method.POST, URL, listener,null);

        String datetime = LocalDateTime.now().toString();
        datetime = datetime.replaceAll("[^0-9:-]", " ");
        datetime = datetime.substring(0, datetime.length()-4);

        map = new HashMap<>();
        map.put("post_num", post_num);
        map.put("writerUID", writerUID);
        map.put("datetime", datetime);
        map.put("content", content);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}