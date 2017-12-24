package com.example.administrator.oneteam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private ImageView photo;
    private EditText et_userName;
    private EditText et_userPassword;
    private Button   btn_signIn;
    private Button   btn_logIn;
    private Handler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        init();
        bindListeners();

    }

    private void bindViews(){
        mHandler = new Handler();
        et_userName = (EditText) findViewById(R.id.user_name_edit);
        et_userPassword = (EditText)findViewById(R.id.user_pwd_edit);
        btn_signIn = (Button) findViewById(R.id.sign_in);
        btn_logIn = (Button) findViewById(R.id.log_in);
        photo = (ImageView)findViewById(R.id.user_image);
        new Thread() {
            public  void run(){
                try {
                    final  Bitmap bm = http.downloadphoto("http://172.18.92.176:3333/my.PNG");
                    mHandler.post( new Runnable(){
                        public  void run() {
                            saveImage(bm,"my1.PNG");
                            if(loadImage("my1.PNG")!=null)
                                photo.setImageBitmap(loadImage("my1.PNG"));
                        }
                    });
                }  catch (Exception e) {//异常处理
                    e.printStackTrace();
                    mHandler.post( new Runnable(){
                        public void run(){
                            btn_logIn.setText("433");
                        }
                    });
                }
            }
        }.start();
//        if(loadImage("my1.PNG")!=null)
//            photo.setImageBitmap(loadImage("my1.PNG"));
//        else
//            Log.e("22222222222","33333333333");
    }
    private void bindListeners(){
        btn_logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread() {
                    public  void run() {
                        try {
                            final InputStream is = http.conect("http://172.18.92.176:3333/user?name=Bruno");
                            final BufferedReader br = new BufferedReader(new InputStreamReader(is));
                            final String in = br.readLine();
                            mHandler.post( new Runnable(){
                                public  void run() {
                                    btn_logIn.setText(in);
                                }
                            });
                        }  catch (Exception e) {//异常处理
                            mHandler.post( new Runnable() {
                                public void run() {
                                    btn_logIn.setText("233");
                                }
                            });
                        }
                    }
                }.start();
            }
        });

    }

    private void init(){
        //region 沉浸式
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        //endregion

    }
    public static void saveImage(Bitmap bmp,String name) {
        File appDir = new File(Environment.getExternalStorageDirectory(), "public");
        if (!appDir.exists()) {
            appDir.mkdir();
        }
        File file = new File(appDir,name);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap loadImage(String name) {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/public/" + name;
        File mFile = new File(path);
        //若该文件存在
        if (mFile.exists()) {
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            return bitmap;
        }
        else
            return null;
    }
}
