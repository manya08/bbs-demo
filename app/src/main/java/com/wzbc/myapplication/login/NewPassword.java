package com.wzbc.myapplication.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wzbc.myapplication.R;
import com.wzbc.myapplication.volley.Insertphone;
import com.wzbc.myapplication.volley.Stringphone;
import com.wzbc.myapplication.volley.getUserbyIdJSON;

import java.util.HashMap;
import java.util.Map;


public class NewPassword extends Activity implements View.OnClickListener {
    private EditText phoneNumber;
    private EditText verifyCode;
    private EditText phonepassword;
    private Button timeButton;
    final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.change_password);
        findViewById(R.id.next).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        timeButton =(Button)findViewById(R.id.get_verify_code);
        phoneNumber = (EditText)findViewById(R.id.phone_number);
        verifyCode = (EditText)findViewById(R.id.verify_code);
        phonepassword = (EditText)findViewById(R.id.phone_password);
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                myCountDownTimer.start();
                Map<String,Object> maps = new HashMap<>();
                maps.put("PhoneNumber",phoneNumber.getText());
                Insertphone.sendmsgps(maps);
            }
        });
    }

    public void changeNext(View view){
        Insertphone insertphone =new Insertphone(this);
        String phoneNumberValue = phoneNumber.getText().toString();
        String verifyCodeValue = verifyCode.getText().toString();
        String phonepass = phonepassword.getText().toString();
        Map<String,Object> maps = new HashMap<String,Object>();
        maps.put("PhoneNumber",phoneNumberValue);
        maps.put("NewPassword",verifyCodeValue);
        maps.put("Password",phonepass);
        insertphone.newpassword(maps);
    }

    public void back(View view) {
        Intent intent = new Intent(NewPassword.this, LoginActivity.class);
        startActivity(intent);
    }

    public boolean isNull(String phoneNumberValue, String verifyCodeValue){
        if(phoneNumberValue.length()<=0||verifyCodeValue.length()<=0){
            return false;
        }else {
            return true;
        }
    }
    private class MyCountDownTimer extends CountDownTimer {

        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        //计时过程
        @Override
        public void onTick(long l) {
            //防止计时过程中重复点击
            timeButton.setClickable(false);
            timeButton.setText(l/1000+"秒");

        }

        //计时完毕的方法
        @Override
        public void onFinish() {
            //重新给Button设置文字
            timeButton.setText("重新获取");
            //设置可点击
            timeButton.setClickable(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.next:
                changeNext(v);
                break;
            case R.id.back:
                back(v);
                break;
        }
    }
}
