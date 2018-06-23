package com.wzbc.myapplication.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.TextView;
import com.wzbc.myapplication.R;


public class SignSucceed extends Activity {
    private int time = 3;
    private TextView textView;
    private boolean isRun = true;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        setContentView(R.layout.sign_succeed);
        textView = (TextView)findViewById(R.id.count_down);
        handler.postDelayed(runnable, 1000);
    }

    Handler handler = new Handler();
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            textView.setText(time+"秒后返回登录界面");
            handler.postDelayed(this, 1000);//延迟1000毫秒
            if(time==0){
                Intent intent=new Intent(SignSucceed.this,LoginActivity.class);
                startActivity(intent);
                SignSucceed.this.finish();
                isRun = false;
                time--;
            }
            if(isRun){
                time--;
            }
        }
    };
}
