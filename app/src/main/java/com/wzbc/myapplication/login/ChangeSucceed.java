package com.wzbc.myapplication.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.wzbc.myapplication.R;

/**
 * Created by Administrator on 2018/6/11/011.
 */

public class ChangeSucceed extends Activity implements View.OnClickListener  {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_succeed);
        findViewById(R.id.go_login).setOnClickListener(this);
    }

    public void goLogin(){
        Intent intent = new Intent(ChangeSucceed.this,LoginActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.go_login:
                goLogin();
                break;
        }
    }
}
