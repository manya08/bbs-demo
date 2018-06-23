package com.wzbc.myapplication.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wzbc.myapplication.MainActivity;
import com.wzbc.myapplication.R;
import com.wzbc.myapplication.login.LoginActivity;
import com.wzbc.myapplication.volley.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;



/**
 * Created by wu on 2018/5/22.
 */

public class MyNoteFragment extends Fragment {
    private long id;
    private View view;
    private TextView title;
    private TextView author;
    private TextView date;
    private TextView text;
    private ImageView imageView2;
    private Bitmap bt;
    private String image ;
    static int postid=0;
    private String phoneNumber;
    private SharedPreferences sharedPreferences;

    public static MyNoteFragment NewInstance(long id){
        MyNoteFragment fragment = new MyNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("id",id);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getLong("id");

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.post_detail, container, false);
//        imageView2.findViewById(R.id.image_detail);
        title = (TextView)view.findViewById(R.id.title_detail);
        author = (TextView)view.findViewById(R.id.author);
        date = (TextView)view.findViewById(R.id.date);
        imageView2 = (ImageView)view.findViewById(R.id.image_detail);
        text = (TextView)view.findViewById(R.id.text_detail);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        GetData getData = new GetData(getActivity());
        final List<Map<String, Object>> list = getData.list;

        if (list.size() != 0) {
            postid = (int) list.get((int) id).get("PostID");
            title.setText((String) list.get((int) id).get("Title"));
            author.setText((String) list.get((int) id).get("PostUserName"));
            date.setText((String) list.get((int) id).get("PostTime"));
            if ((String) list.get((int) id).get("PostImage") != null) {
                image = (String) list.get((int) id).get("PostImage");
                bt = base64ToBitmap(image);
                imageView2.setImageBitmap(bt);
            }
            text.setText((String) list.get((int) id).get("PostContent"));

        }
        view.findViewById(R.id.look_reply).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetData.getDatareply(postid);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), Reply.class);
                        startActivity(intent);
                    }
                }, 1000);

            }
        });
    }

    public static JSONObject getJSON(Map<String,Object> maps){
        JSONArray a =new JSONArray();
        JSONObject u = new JSONObject();
        try {
            while (true){
                JSONObject j = new JSONObject();
                a.put(j.put("PostID",maps.get("PostId"))
                      .put("phone",maps.get("PhoneNumber"))
                );
                break;
            }
            Log.d("sendmsg", "getInsertJSON: "+a);
            u.put("ReqCode","1").put("info",a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }

    public static Bitmap base64ToBitmap(String base64Data) {
        byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    public long getCurrentItemId(){
        return id;
    }



}
