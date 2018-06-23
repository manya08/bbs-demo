package com.wzbc.myapplication.personal;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wzbc.myapplication.MainActivity;
import com.wzbc.myapplication.R;
import com.wzbc.myapplication.login.LoginActivity;

/**
 * Created by Administrator on 2018/6/14/014.
 */

public class SettingActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor automaticLoginEditor;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_activity);
        findViewById(R.id.btn_exit).setOnClickListener(this);
    }

    public void resetSharedPreferences(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        automaticLoginEditor = sharedPreferences.edit();
        automaticLoginEditor.putBoolean("automatic_login",false);
        automaticLoginEditor.commit();
    }//重置("automatic",false)

    public void exit(){
        resetSharedPreferences();
        Intent intent=new Intent(SettingActivity.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exit:
                exit();
                break;
        }
    }


}
