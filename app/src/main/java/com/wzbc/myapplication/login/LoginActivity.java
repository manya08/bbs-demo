package com.wzbc.myapplication.login;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wzbc.myapplication.MainActivity;
import com.wzbc.myapplication.R;
import com.wzbc.myapplication.volley.Insertphone;
import com.wzbc.myapplication.volley.getUserbyIdJSON;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor automaticLoginEditor;//可以向SharedPreferences.Editor对象中添加数据。
    private CheckBox rememberPassword;
    private CheckBox automaticLogin;
    private EditText phoneNumber;
    private EditText password;
    public static Boolean isLogin;
    public static String isLoginstr;
    public static String phoneNumber2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);//使用这个方法会自动使用当前程序的包名作为前缀来命名SharedPreferences文件
        automaticLoginEditor = sharedPreferences.edit();//调用SharedPreferences对象的edit()方法来获取一个SharedPreferences.Editor对象

        if(sharedPreferences.getBoolean("automatic_login",false)){
            Intent intent=new Intent(LoginActivity.this,MainActivity.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }//.getBoolean("automatic",false)；当找不到"automatic"所对应的键值是默认返回false

        setContentView(R.layout.login);
        phoneNumber = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);

        rememberPassword = (CheckBox) findViewById(R.id.remember_password);
        automaticLogin = (CheckBox) findViewById(R.id.automatic_login);
        findViewById(R.id.login).setOnClickListener(this);
        findViewById(R.id.sign).setOnClickListener(this);
        findViewById(R.id.forget_password).setOnClickListener(this);
        sharedPreferences = getSharedPreferences("user_info", 0);//读取数据，第二个参数为默认值
        String name = sharedPreferences.getString("phoneNumber", "");
        String pass = sharedPreferences.getString("password", "");
        boolean choseRemember = sharedPreferences.getBoolean("remember_info", false);
        boolean choseAutomatic = sharedPreferences.getBoolean("automatic_login", false);

        if(choseRemember){
            phoneNumber.setText(name);
            password.setText(pass);
            rememberPassword.setChecked(true);//在Editor上显示帐号密码及记住密码被勾选
        }

        if(choseAutomatic){
            automaticLogin.setChecked(true);
        }


    }

    //点击注册按钮进入注册页面
    public void signClicked(View view) {
        Intent intent = new Intent(LoginActivity.this, SignActivity.class);
        startActivity(intent);
    }

    //修改密码页面
    public void changePassword(View view){
        Intent intent = new Intent(LoginActivity.this, NewPassword.class);
        startActivity(intent);
    }

    //点击登录按钮
    public void loginClicked(View view) {
        getUserbyIdJSON getUserbyId =new getUserbyIdJSON(this);

        String phoneNumberValue = phoneNumber.getText().toString();
        String passwordValue = password.getText().toString();
        SharedPreferences.Editor rememberPasswordEditor = sharedPreferences.edit();

        Map<String,Object> maps = new HashMap<String,Object>();
        maps.put("PhoneNumber",phoneNumberValue);
        maps.put("Password",passwordValue);
        getUserbyId.login(maps);
        phoneNumber2 =phoneNumberValue;
        rememberPasswordEditor.putString("phoneNumber", phoneNumberValue);
        rememberPasswordEditor.putString("password", passwordValue);//rememberPasswordEditor存入username，password信息

        if(rememberPassword.isChecked())//记住密码是否打勾
        {
            rememberPasswordEditor.putBoolean("remember_info", true);
        }else{
            rememberPasswordEditor.putBoolean("remember_info", false);//rememberPasswordEditor存入remember_info信息
        }

        rememberPasswordEditor.commit();

        if(automaticLogin.isChecked()){
            automaticLoginEditor.putBoolean("automatic_login",true);
        }else {
            automaticLoginEditor.putBoolean("automatic_login",false);
        }

        automaticLoginEditor.commit();//调用commit方法将添加的数据提交

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                loginClicked(v);
                break;
            case R.id.sign:
                signClicked(v);
                break;
            case R.id.forget_password:
                changePassword(v);
                break;
        }
    }
}