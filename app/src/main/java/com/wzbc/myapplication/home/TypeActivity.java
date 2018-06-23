package com.wzbc.myapplication.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wzbc.myapplication.MainActivity;
import com.wzbc.myapplication.R;

/**
 * Created by Administrator on 2018/6/12/012.
 */

public class TypeActivity extends AppCompatActivity implements View.OnClickListener{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.style);
        findViewById(R.id.java_btn).setOnClickListener(this);
    }

    public void select_java(){
        Intent intent = new Intent(TypeActivity.this, MainActivity.class);
        intent.putExtra("javaValue","java");//将账号值存入intent
        startActivity(intent);
        TypeActivity.this.finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.java_btn:
                select_java();
                break;
        }
    }
}
