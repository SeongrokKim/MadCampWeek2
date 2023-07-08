package com.example.madcampweek2;

import android.app.Application;
import android.os.Bundle;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.sdk.common.KakaoSdk;

public class KakaoActivity extends Application {
    @Override
    public void onCreate(){
        super.onCreate();
        KakaoSdk.init(this, "49aff5155e97e9793c5ab414de97a479");
    }

}