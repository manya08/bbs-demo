package com.wzbc.myapplication.personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;

import com.wzbc.myapplication.R;
import com.wzbc.myapplication.SelectPicPopupWindow;
import com.wzbc.myapplication.home.AddArticleActivity;
import com.wzbc.myapplication.volley.getUserbyIdJSON;


/**
 * Created by WZBC on 2018/6/5.
 */

public class PersonActivity extends AppCompatActivity implements View.OnClickListener{
    public static ImageView image1;
    SelectPicPopupWindow menuWindow;
    private static int REQUEST_CAMERA_1 = 1;
    private static int REQUEST_CAMERA_2 = 2;
    private ImageView myHeaderImage;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        findViewById(R.id.my_header).setOnClickListener(this);
        findViewById(R.id.write_individual).setOnClickListener(this);
        myHeaderImage = (ImageView)findViewById(R.id.my_header);

    }


    public void goWrite(){
        Intent intent = new Intent(PersonActivity.this,ChangeIndividualActivity.class);
        startActivity(intent);
    }

//    public void getHeader(){
//        getUserbyIdJSON getUserby =new getUserbyIdJSON(PersonActivity.this);
//        getUserby.imageload();
//    }

    public void bottomWindow(){
        //实例化SelectPicPopupWindow
        menuWindow = new SelectPicPopupWindow(PersonActivity.this, itemsOnClick);
        //显示窗口
        menuWindow.showAtLocation(PersonActivity.this.findViewById(R.id.my_header), Gravity.BOTTOM|Gravity.CENTER_HORIZONTAL, 0, 0); //设置layout在PopupWindow中显示的位置
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.my_header:
                bottomWindow();
                break;
            case R.id.write_individual:
                goWrite();
                break;
        }
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

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) { // 如果返回数据
            if (requestCode == REQUEST_CAMERA_1) { // 判断请求码是否为REQUEST_CAMERA,如果是代表是这个页面传过去的，需要进行获取
                Bundle bundle = data.getExtras(); // 从data中取出传递回来缩略图的信息，图片质量差，适合传递小图片
                Bitmap bitmap = (Bitmap) bundle.get("data"); // 将data中的信息流解析为Bitmap类型
                myHeaderImage.setImageBitmap(bitmap);// 显示图片
            } else if (requestCode == REQUEST_CAMERA_2) {
                try {
                    Uri uri = data.getData();
                    Bitmap bit = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));
                    myHeaderImage.setImageBitmap(bit);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
