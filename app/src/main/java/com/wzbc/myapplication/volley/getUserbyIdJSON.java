package com.wzbc.myapplication.volley;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wzbc.myapplication.MainActivity;
import com.wzbc.myapplication.R;
import com.wzbc.myapplication.home.GetData;
import com.wzbc.myapplication.login.WelcomeActivity;
import com.wzbc.myapplication.personal.PersonActivity;
import com.wzbc.myapplication.login.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.Map;


//1.检查用户名邮箱手机是否重复，2.用邮箱进行注册，3.用手机进行注册，.4发送手机验证码,.5邮箱打开邮箱验证码进行邮箱激活6.登录
public class getUserbyIdJSON {
    public LoginActivity loginActivity;
//    http://39.108.237.12:8080
    //192.168.43.103
    public static String url="http://192.168.43.103:8080";
    Context context;
    public getUserbyIdJSON(Context context){
        this.context=context;
    }
    private static final String TAG = "InsertJSON";
    public static JSONObject getUserbyIdJSON(Map<String,Object> maps){
        JSONArray a =new JSONArray();
        JSONObject u = new JSONObject();
        try {
            while (true){
                JSONObject j = new JSONObject();
                a.put(j.put("phone",maps.get("PhoneNumber"))
                        .put("Password",maps.get("Password"))
                        .put("verification",maps.get("NewPassword")));
                break;
            }
            Log.d(TAG, "getInsertJSON: "+a);
            u.put("ReqCode","1").put("info",a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
    public static JSONObject getreplyJSON(Map<String,Object> maps){
        JSONArray a =new JSONArray();
        JSONObject u = new JSONObject();
        try {
            while (true){
                JSONObject j = new JSONObject();
                a.put(j.put("PostID",maps.get("PostID"))
                        .put("ReplyContent",maps.get("ReplyContent"))
                        .put("ReplyTime",maps.get("Replytime")));
                break;
            }
            Log.d(TAG, "getInsertJSON: "+a);
            u.put("ReqCode","1").put("info",a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }
    public void login(Map<String, Object> maps){
        JSONObject paramObject = getUserbyIdJSON.getUserbyIdJSON(maps);
        JsonObjectRequest jsonObjectbyid = new JsonObjectRequest(Request.Method.POST, url+"/user?method=4",
                paramObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("ret").equals(0)){
                                GetData.getData();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        context.startActivity(new Intent(context,MainActivity.class));
                                    }
                                },500);
                            }else {

                                Log.d("shibai", response.toString());
                                Toast.makeText(context, "登陆失败", Toast.LENGTH_SHORT).show();
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
    public void imageload(){
        ImageRequest imageRequest = new ImageRequest(
                "https://www.baidu.com/img/baidu_jgylogo3.gif",
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                        PersonActivity.image1.setImageBitmap(response);
                        Log.d("chengong", "123");
                    }
                }, 0, 0 ,Bitmap.Config.RGB_565, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                    PersonActivity.image1.setImageResource(R.drawable.add_post);
                Log.d("shibai", "error"+error);
            }
        });
        MyApplication.getRequestQueue().add(imageRequest);
    }

}
