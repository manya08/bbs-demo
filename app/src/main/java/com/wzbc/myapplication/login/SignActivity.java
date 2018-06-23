package com.wzbc.myapplication.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wzbc.myapplication.R;
import com.wzbc.myapplication.volley.Insertphone;
import com.wzbc.myapplication.volley.Stringphone;

import java.util.HashMap;
import java.util.Map;


public class SignActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText phoneNumber;
    private EditText password;
    private EditText passwordAgain;
    private EditText verifyCode;
    private TextView passwordTip;
    public static TextView phoneErrorTip;
    public static Boolean isLogin;
    private Button timeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final MyCountDownTimer myCountDownTimer = new MyCountDownTimer(60000,1000);
        setContentView(R.layout.sign);
        findViewById(R.id.ensure).setOnClickListener(this);
        findViewById(R.id.back).setOnClickListener(this);
        findViewById(R.id.get_verify_code).setOnClickListener(this);
        phoneErrorTip = (TextView)findViewById(R.id.phone_number_tip);
        phoneNumber = (EditText)findViewById(R.id.phone_number);
        timeButton = (Button) findViewById(R.id.get_verify_code);
        phoneNumber.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Map<String,Object> maps = new HashMap<String,Object>();
                if (hasFocus) {
                    phoneErrorTip.setVisibility(View.GONE);
                } else {
                    if(Stringphone.isPhoneLegal(phoneNumber.getText().toString())){
                        CheckPhoneNumber(phoneNumber);
                    }else {
                        Toast.makeText(SignActivity.this, "手机格式不正确", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });//手机号已注册时显示提示
        password = (EditText)findViewById(R.id.password);
        passwordAgain = (EditText)findViewById(R.id.password_again);
        passwordTip = (TextView)findViewById(R.id.password_tip);
        verifyCode = (EditText)findViewById(R.id.verify_code);

        passwordAgain.setOnFocusChangeListener(new View.
                OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String passwordValue = password.getText().toString();
                String passwordAgainValue = passwordAgain.getText().toString();
                if (hasFocus) {
                    passwordTip.setVisibility(View.GONE);
                } else {
                    if(passwordErrorTip(passwordValue, passwordAgainValue)){
                        passwordTip.setVisibility(View.GONE);
                    }else {
                        passwordTip.setVisibility(View.VISIBLE);
                    }
                }
            }
        });//第二次输入的密码与第一次输入的密码不同时会提示
        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //判断手机格式 不正确会提示
                    myCountDownTimer.start();
                    Map<String,Object> maps = new HashMap<>();
                    maps.put("PhoneNumber",phoneNumber.getText());
                    Insertphone.sendmsg(maps);

            }
        });
    }

    public void sign(View view){
        Insertphone insertphone = new Insertphone(this);
        String phoneNumberValue = phoneNumber.getText().toString();
        String passwordValue = password.getText().toString();
        String passwordAgainValue = passwordAgain.getText().toString();
        String verifyCodeValue = verifyCode.getText().toString();
        if(passwordErrorTip(passwordValue, passwordAgainValue)&&isNull(phoneNumberValue,passwordValue,passwordAgainValue,verifyCodeValue)){
            Map<String,Object> maps = new HashMap<String,Object>();
            maps.put("PhoneNumber",phoneNumberValue);
            maps.put("Password",passwordValue);
            maps.put("NewPassword",verifyCodeValue);
            insertphone.Insertphone(maps);
        }else {
            Toast.makeText(this, "输入有误", Toast.LENGTH_SHORT).show();
        }
    }

    public void back(View view) {
        Intent intent = new Intent(SignActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    //比较两次密码输入是否一样
    public boolean passwordErrorTip(String password, String passwordAgain){
        return password.equals(passwordAgain);
    }

    public boolean isNull(String phoneNumberValue, String passwordValue, String passwordAgainValue, String verifyCodeValue){
        if(phoneNumberValue.length()<=0||passwordValue.length()<=0||passwordAgainValue.length()<=0||verifyCodeValue.length()<=0){
            return false;
        }else {
            return true;
        }
    }

    //检验手机号是否已存在
    public void CheckPhoneNumber(EditText phoneNumber){
        Map<String,Object> maps = new HashMap<String,Object>();
        maps.put("PhoneNumber",phoneNumber.getText());
        Insertphone.checkphone(maps);
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
            case R.id.ensure:
                sign(v);
                break;
            case R.id.back:
                back(v);
                break;
        }
    }
}

