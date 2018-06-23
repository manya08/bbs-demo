package com.wzbc.myapplication.personal;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wzbc.myapplication.R;

/**
 * Created by Administrator on 2018/6/15/015.
 */

public class ChangeIndividualActivity extends AppCompatActivity implements View.OnClickListener{

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_individual);
        findViewById(R.id.ensure).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ensure:
                break;
        }
    }
}
