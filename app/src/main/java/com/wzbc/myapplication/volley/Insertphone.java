package com.wzbc.myapplication.volley;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wzbc.myapplication.login.ChangeSucceed;
import com.wzbc.myapplication.login.SignActivity;
import com.wzbc.myapplication.login.SignSucceed;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

import static com.wzbc.myapplication.volley.getUserbyIdJSON.url;

/**
 * Created by WZBC on 2018/6/3.
 */
//1.检查用户名是否重复，2.用邮箱进行注册，3.用手机进行注册，.4发送手机验证码,.5邮箱打开邮箱验证码进行邮箱激活
public class Insertphone {
    Context context;
    public Insertphone(Context context){
        this.context=context;
    }
    public  void Insertphone(Map<String, Object> maps){
        JSONObject paramObject = getUserbyIdJSON.getUserbyIdJSON(maps);
        JsonObjectRequest jsonObjectbyid = new JsonObjectRequest(Request.Method.POST, url+"/user?method=3",
                paramObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("ret").equals(0)){
                                Log.d("chenggong", response.toString());
                                Toast.makeText(context, "注册成功", Toast.LENGTH_SHORT).show();
                                context.startActivity(new Intent(context,SignSucceed.class));
                            }else {
                                Log.d("shibai", response.toString());
                                Toast.makeText(context,"注册失败",Toast.LENGTH_SHORT).show();
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
    public void newpassword(Map<String, Object> maps){
        JSONObject paramObject = getUserbyIdJSON.getUserbyIdJSON(maps);
        JsonObjectRequest jsonObjectbyid = new JsonObjectRequest(Request.Method.POST, url+"/user?method=6",
                paramObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("ret").equals(0)){
                                Log.d("chenggong", response.toString());
                                context.startActivity(new Intent(context,ChangeSucceed.class));

                            }else {
                                Log.d("shibai", response.toString());
                                Toast.makeText(context,"验证码错误",Toast.LENGTH_SHORT).show();
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
    public static void checkphone(final Map<String, Object> maps){

        JSONObject paramObject = getUserbyIdJSON.getUserbyIdJSON(maps);

        JsonObjectRequest jsonObjectbyid = new JsonObjectRequest(Request.Method.POST, url+"/user?method=1&phone="+maps.get("PhoneNumber"),
                paramObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("checkphone").equals("true")){
                                Log.d("chenggong", response.toString());
                                SignActivity.phoneErrorTip.setVisibility(View.VISIBLE);
                                SignActivity.isLogin =true;
                            }else {
                                Log.d("shibai", response.toString());
                                SignActivity.phoneErrorTip.setVisibility(View.GONE);
                            }
                        } catch (JSONException ex) {
                            ex.printStackTrace();
                            Log.d("shibai", response.toString());
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

    public static void sendmsg(Map<String, Object> maps){
        JSONObject paramObject = getUserbyIdJSON.getUserbyIdJSON(maps);
        JsonObjectRequest jsonObjectbyid = new JsonObjectRequest(Request.Method.POST, url+"/user?method=2",
                paramObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("hlc", "onErrorResponse: "+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hlc", "onErrorResponse: "+error);
            }
        });
        MyApplication.getRequestQueue().add(jsonObjectbyid);
    }
    public static void sendmsgps(Map<String, Object> maps){
        JSONObject paramObject = getUserbyIdJSON.getUserbyIdJSON(maps);
        JsonObjectRequest jsonObjectbyid = new JsonObjectRequest(Request.Method.POST, url+"/user?method=5",
                paramObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("hlc", "onResponse: "+response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hlc", "onErrorResponse: "+error);
            }
        });
        MyApplication.getRequestQueue().add(jsonObjectbyid);
    }
}