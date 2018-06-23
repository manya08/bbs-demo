package com.wzbc.myapplication.home;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wzbc.myapplication.MainActivity;
import com.wzbc.myapplication.R;
import com.wzbc.myapplication.SelectPicPopupWindow;
import com.wzbc.myapplication.login.LoginActivity;
import com.wzbc.myapplication.login.SignSucceed;
import com.wzbc.myapplication.login.WelcomeActivity;
import com.wzbc.myapplication.volley.MyApplication;
import com.wzbc.myapplication.volley.getUserbyIdJSON;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.wzbc.myapplication.volley.getUserbyIdJSON.url;


/**
 * Created by wu on 2018/5/21.
 */

public class AddArticleActivity extends AppCompatActivity implements View.OnClickListener,AdapterView.OnItemSelectedListener {
    private TextView tvShow;
    private Spinner spDown;
    private List<String> list;
    private ArrayAdapter<String> adapter;
    private static int REQUEST_CAMERA_1 = 1;
    private static int REQUEST_CAMERA_2 = 2;
    private ImageView ivShowPicture;
    private Bitmap bm;

    private TextView type;
    private TextView title;
    private TextView content;
    private SharedPreferences sharedPreferences;
    private String phoneNumber;
    SelectPicPopupWindow menuWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_post);
        findViewById(R.id.cancel).setOnClickListener(this);
        findViewById(R.id.add_image).setOnClickListener(this);
        findViewById(R.id.btn_txtsend).setOnClickListener(this);
//        gridView = (GridView)findViewById(R.id.gridView);
        tvShow=(TextView) findViewById(R.id.select_type);
        title=(TextView) findViewById(R.id.post_title);
        content=(TextView) findViewById(R.id.post_context);
        spDown=(Spinner) findViewById(R.id.spinner);
        ivShowPicture = (ImageView)findViewById(R.id.current_photo);
        sharedPreferences = getSharedPreferences("user_info", 0);//读取数据，第二个参数为默认值
        phoneNumber = sharedPreferences.getString("phoneNumber", "");

        tvShow.setText("");
         /*设置数据源*/
        list=new ArrayList<String>();
        list.add("java");
        list.add("php");
        list.add("C");
        list.add("C++");
          /*新建适配器*/
        adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,list);
        /*adapter设置一个下拉列表样式，参数为系统子布局*/
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        /*spDown加载适配器*/
        spDown.setAdapter(adapter);
        /*soDown的监听器*/
        spDown.setOnItemSelectedListener(this);


    }

    private Bitmap getBitmap(ImageView imageView){
        Bitmap bm =((BitmapDrawable) ((ImageView) imageView).getDrawable()).getBitmap();
        return bm;
    }

    public static String BitmapToByte(Bitmap bitmap) {//将bitmap转化位base64编码

        String result = null;
        ByteArrayOutputStream baos = null;
        try {
            if (bitmap != null) {
                baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);

                baos.flush();
                baos.close();

                byte[] bitmapBytes = baos.toByteArray();
                result = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (baos != null) {
                    baos.flush();
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void back(){
        GetData.getData();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(AddArticleActivity.this,MainActivity.class);
                startActivity(intent);
            }
        },500);
    }


    public void send(){

        Map<String,Object> maps = new HashMap<String,Object>();
        maps.put("Title",title.getText().toString());
        maps.put("PostType",tvShow.getText().toString());
        maps.put("PostContent",content.getText().toString());
        maps.put("phoneNumber",phoneNumber);
        maps.put("PostImage",BitmapToByte(bm));
        sendmsg(maps);

    }
    public  void sendmsg(Map<String, Object> maps){
        JSONObject paramObject = getJSON(maps);

        JsonObjectRequest jsonObjectbyid = new JsonObjectRequest(Request.Method.POST, url+"/bbstext?method=2&phone="+maps.get("phoneNumber"),
                paramObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if(response.get("ret").equals(0)){
                                Log.d("chenggong", response.toString());
                                Toast.makeText(AddArticleActivity.this, "发表成功", Toast.LENGTH_SHORT).show();
                                GetData.getData();
                                new Handler().postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(AddArticleActivity.this,MainActivity.class);
                                        startActivity(intent);
                                    }
                                },1000);

                            }else {
                                Log.d("shibai", response.toString());
                                Toast.makeText(AddArticleActivity.this,"发表失败",Toast.LENGTH_SHORT).show();
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
    public static JSONObject getJSON(Map<String,Object> maps){
        JSONArray a =new JSONArray();
        JSONObject u = new JSONObject();
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String createdate = df.format(date);
        try {
            while (true){
                JSONObject j = new JSONObject();
                a.put(j.put("Title",maps.get("Title"))
                        .put("PostType",maps.get("PostType"))
                        .put("PostContent",maps.get("PostContent"))
                        .put("PostTime",createdate)
                        .put("PostImage",maps.get("PostImage")));
                break;
            }
            Log.d("sendmsg", "getInsertJSON: "+a);
            u.put("ReqCode","1").put("info",a);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return u;
    }


    public void bottomWindow(){
        //实例化SelectPicPopupWindow
        menuWindow = new SelectPicPopupWindow(AddArticleActivity.this, itemsOnClick);
        //显示窗口
        menuWindow.showAtLocation(AddArticleActivity.this.findViewById(R.id.add_image), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    private View.OnClickListener itemsOnClick = new View.OnClickListener(){

        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.takePhoto:
                    openCamera_1();
                    break;
                case R.id.choosePhoto:
                    openAlbum();
                        break;
                default:
                    break;
            }
        }
    };

    // 拍照并显示图片
    private void openCamera_1() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// 启动系统相机
        startActivityForResult(intent, REQUEST_CAMERA_1);
    }

    private void openAlbum(){
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");//相片类型
        startActivityForResult(intent, REQUEST_CAMERA_2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == REQUEST_CAMERA_1) { // 判断请求码是否为REQUEST_CAMERA,如果是代表是这个页面传过去的，需要进行获取
                Bundle bundle = data.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类型
                ivShowPicture.setImageBitmap(bitmap);// 显示图片
                bm = getBitmap(ivShowPicture);
            } else if (requestCode == REQUEST_CAMERA_2) {
                try {
                    Uri uri = data.getData();
                    Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    ivShowPicture.setImageBitmap(bit);
                    bm = getBitmap(ivShowPicture);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
                               long id) {
        String typeName=adapter.getItem(position);   //获取选中的那一项
        tvShow.setText("" + typeName);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {

    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.cancel:
                back();
                break;
            case R.id.add_image:
                bottomWindow();
                break;
            case R.id.btn_txtsend:
                send();
                break;
        }
    }
}
