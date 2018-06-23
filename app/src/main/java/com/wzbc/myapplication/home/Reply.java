package com.wzbc.myapplication.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wzbc.myapplication.MainActivity;
import com.wzbc.myapplication.R;
import com.wzbc.myapplication.login.LoginActivity;
import com.wzbc.myapplication.login.SignSucceed;
import com.wzbc.myapplication.login.WelcomeActivity;
import com.wzbc.myapplication.volley.MyApplication;
import com.wzbc.myapplication.volley.getUserbyIdJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wzbc.myapplication.home.MyNoteFragment.postid;
import static com.wzbc.myapplication.volley.getUserbyIdJSON.url;

/**
 * Created by Administrator on 2018/6/12/012.
 */

public class Reply extends AppCompatActivity implements View.OnClickListener{
    private SharedPreferences sharedPreferences;
    private String phoneNumber;
    private EditText sendcontent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reply_context);
        findViewById(R.id.btn_send).setOnClickListener(this);
        sendcontent =(EditText) findViewById(R.id.edt_send);
    }

    public void refresh() {

        onCreate(null);

    }
    public void send(){
        sharedPreferences = getSharedPreferences("user_info", 0);//读取数据，第二个参数为默认值
        phoneNumber = sharedPreferences.getString("phoneNumber", "");
        Map<String,Object> maps = new HashMap<String,Object>();
        maps.put("PostID",postid);
        maps.put("ReplyContent",sendcontent.getText().toString());
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createdate = df.format(date);
        maps.put("Replytime",createdate);
        System.out.println(maps+"asd");
        JSONObject paramObject = getUserbyIdJSON.getreplyJSON(maps);
        JsonObjectRequest jsonObjectbyid = new JsonObjectRequest(Request.Method.POST, url+"/bbstext?method=4&phone="+phoneNumber,
                paramObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Log.d("chenggong", response.toString());
                            if(response.get("ret").equals(0)){
                                GetData.getDatareply(postid);
                                Log.d("chenggong", response.toString());
                                Toast.makeText(Reply.this,"发表成功",Toast.LENGTH_SHORT).show();
                                GetData.getData();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent=new Intent(Reply.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                }, 1000);

                            }else {
                                Log.d("shibai", response.toString());
                                Toast.makeText(Reply.this,"发表失败",Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hlc", "onErrorResponse: "+error);
            }
        });
        MyApplication.getRequestQueue().add(jsonObjectbyid);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
//            case R.id.back_detail:
//                back();
            case R.id.btn_send:
                send();
                break;
        }
    }
}
