package com.wzbc.myapplication;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wzbc.myapplication.home.AddArticleActivity;
import com.wzbc.myapplication.personal.PersonalCenterActivity;
import com.wzbc.myapplication.home.TypeActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor automaticLoginEditor;
    private String type=null;
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.individual).setOnClickListener(this);
        findViewById(R.id.post_style).setOnClickListener(this);
        findViewById(R.id.add).setOnClickListener(this);
        sharedPreferences = getSharedPreferences("user_info", 0);//读取数据，第二个参数为默认值
        phoneNumber = sharedPreferences.getString("phoneNumber", "");
        Intent intent = getIntent();
        intent.putExtra("phoneNumber",phoneNumber);
        Bundle bundle = intent.getExtras();
        if(bundle!=null){
            type = bundle.getString("javaValue");
        }
    }

    public void select_type(){
        Intent intent=new Intent(MainActivity.this,TypeActivity.class);
        startActivity(intent);
    }

    public void addPost(){
        Intent intent=new Intent(MainActivity.this,AddArticleActivity.class);
        startActivity(intent);
    }

    public void individual(){
        Intent intent=new Intent(MainActivity.this,PersonalCenterActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.individual:
                individual();
                break;
            case R.id.post_style:
                select_type();
                break;
            case R.id.add:
                addPost();
                break;
        }
    }
}