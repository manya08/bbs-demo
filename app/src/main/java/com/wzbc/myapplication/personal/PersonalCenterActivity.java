package com.wzbc.myapplication.personal;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wzbc.myapplication.MainActivity;
import com.wzbc.myapplication.R;
import com.wzbc.myapplication.home.AddArticleActivity;
import com.wzbc.myapplication.home.GetData;


/**
 * Created by wu on 2018/5/19.
 */

public class PersonalCenterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_me);
//        findViewById(R.id.me_btn_tuichu).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                resetSharedPreferences();
//                Intent intent = new Intent(PersonalCenterActivity.this,LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        findViewById(R.id.collect_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalCenterActivity.this,MyCollection.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.head_portrait).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalCenterActivity.this,PersonActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.me_guanzhu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalCenterActivity.this,MyAttentionActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.me_fatie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GetData.getDatamy();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(PersonalCenterActivity.this,MyArticleActivity.class);
                        startActivity(intent);
                    }
                },500);
            }
        });


        findViewById(R.id.me_pinglun).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PersonalCenterActivity.this,MyReplyActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.my_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalCenterActivity.this,SettingActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalCenterActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });


        findViewById(R.id.add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalCenterActivity.this,AddArticleActivity.class);
                startActivity(intent);
            }
        });

    }




}
