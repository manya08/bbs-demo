package com.wzbc.myapplication.home;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wzbc.myapplication.volley.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wzbc.myapplication.login.LoginActivity.phoneNumber2;
import static com.wzbc.myapplication.volley.getUserbyIdJSON.url;

/**
 * Created by WZBC on 2018/4/11.
 */

public class GetData {
    Context context;
    public GetData(Context context){this.context =context;}
    static List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
    static int cont =0;
    public static List<Map<String,Object>> getData(){


        JsonObjectRequest jsonObjectbyid = new JsonObjectRequest(Request.Method.GET, url+"/bbstext?method=1",
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            list= new ArrayList<Map<String,Object>>();
                            JSONArray userArray = new JSONArray();
                            userArray=response.getJSONArray("info");
                            for(int i = 0; i <(int)response.get("num"); i++){
                                Map<String,Object> maps =new HashMap<String,Object>();
                                JSONObject userObject =new JSONObject();
                                userObject = userArray.getJSONObject(i);
                                maps.put("Title",userObject.getString("Title"));
                                maps.put("PostID",userObject.getInt("PostID"));
                                if(!userObject.isNull("PostImage")){
                                maps.put("PostImage",userObject.getString("PostImage"));}
                                maps.put("PostUserName",userObject.getString("PostUserName"));
                                maps.put("PostTime",userObject.getString("PostTime"));
                                maps.put("PostReadNumber",userObject.getString("PostReadNumber"));
                                maps.put("PostType",userObject.getString("PostType"));
                                maps.put("ReplyNumber",userObject.getString("ReplyNumber"));
                                maps.put("PostContent",userObject.getString("PostContent"));
                                list.add(maps);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("contentshibai", "onResponse: "+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hlc", "onErrorResponse: "+error);
            }
        });
        MyApplication.getRequestQueue().add(jsonObjectbyid);
        return  list;
    }

    static List<Map<String,Object>> listmy =new ArrayList<Map<String,Object>>();
    public static List<Map<String,Object>> getDatamy(){


        JsonObjectRequest jsonObjectbyid = new JsonObjectRequest(Request.Method.GET, url+"/bbstext?method=5&phone="+phoneNumber2,
            null,
            new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        listmy= new ArrayList<Map<String,Object>>();
                        JSONArray userArray = new JSONArray();
                        userArray=response.getJSONArray("info");
                        for(int i = 0; i <(int)response.get("num"); i++){
                            Map<String,Object> maps =new HashMap<String,Object>();
                            JSONObject userObject =new JSONObject();
                            userObject = userArray.getJSONObject(i);
                            maps.put("Title",userObject.getString("Title"));
                            maps.put("PostID",userObject.getInt("PostID"));
                            if(!userObject.isNull("PostImage")){
                                maps.put("PostImage",userObject.getString("PostImage"));}
                            maps.put("PostUserName",userObject.getString("PostUserName"));
                            maps.put("PostTime",userObject.getString("PostTime"));
                            maps.put("PostReadNumber",userObject.getString("PostReadNumber"));
                            maps.put("PostType",userObject.getString("PostType"));
                            maps.put("ReplyNumber",userObject.getString("ReplyNumber"));
                            maps.put("PostContent",userObject.getString("PostContent"));
                            listmy.add(maps);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("contentshibai", "onResponse: "+e.getMessage());
                    }
                }
            }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Log.d("hlc", "onErrorResponse: "+error);
        }
    });
        MyApplication.getRequestQueue().add(jsonObjectbyid);
        return  listmy;
}
    static List<Map<String,Object>> listreply =new ArrayList<>();
    public static List<Map<String,Object>> getDatareply(int postid){
//        List<Map<String,Object>> list =getData.list;
        cont+=1;
        Map<String,Object> map = new HashMap<>();

        JsonObjectRequest jsonObjectbyid = new JsonObjectRequest(Request.Method.GET, url+"/bbstext?method=3&PostID="+postid+"&cont="+cont,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("hlc", "onResponse: "+response);
                        try {
                            listreply= new ArrayList<Map<String,Object>>();
                            JSONArray userArray = new JSONArray();
                            GetData.getData();
                            userArray=response.getJSONArray("info");
                            for(int i = 0; i <(int)response.get("num"); i++){
                                Map<String,Object> maps =new HashMap<String,Object>();
                                JSONObject userObject =new JSONObject();
                                userObject = userArray.getJSONObject(i);
                                maps.put("ID",userObject.getInt("ID"));
                                maps.put("PostID",userObject.getInt("PostID"));
                                maps.put("ReplyID",userObject.getString("ReplyID"));
                                maps.put("ReplyAccount",userObject.getString("ReplyAccount"));
                                maps.put("ReplyContent",userObject.getString("ReplyContent"));
                                maps.put("Replytime",userObject.getString("Replytime"));
                                listreply.add(maps);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("contentshibai", "onResponse: "+e.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("hlc", "onErrorResponse: "+error);
            }
        });
        MyApplication.getRequestQueue().add(jsonObjectbyid);

        return  listreply;
    }
}
