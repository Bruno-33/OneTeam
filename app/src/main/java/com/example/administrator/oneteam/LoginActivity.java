package com.example.administrator.oneteam;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.oneteam.Factory.ServiceFactory;
import com.example.administrator.oneteam.Service.BrunoService;
import com.example.administrator.oneteam.model.Outcome;

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

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.administrator.oneteam.R.id.imageView;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private ImageView photo;
    private EditText et_userName;
    private EditText et_userPassword;
    private EditText et_team_id;
    private Button   btn_signIn;
    private Button   btn_signUp;
    private Toast toast;
    private LinearLayout team;
    private Handler mHandler;
    private String stage;
    private ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        bindViews();
        init();
        bindListeners();
        stage = "signin";

    }

    private void bindViews(){
        mHandler = new Handler();
        et_userName = (EditText) findViewById(R.id.user_name_edit);
        et_userPassword = (EditText)findViewById(R.id.user_pwd_edit);
        et_team_id = (EditText)findViewById(R.id.team_id);
        btn_signIn = (Button) findViewById(R.id.sign_in);
        team = (LinearLayout)findViewById(R.id.team);
        team.setVisibility(View.GONE);
        photo = (ImageView)findViewById(R.id.user_image);
        btn_signUp = (Button)findViewById(R.id.sign_up);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.INVISIBLE);
        init_toast();
//        new Thread() {
//            public  void run(){
//                try {
//                    final  Bitmap bm = http.downloadphoto("http://172.18.92.176:3333/my.PNG");
//                    mHandler.post( new Runnable(){
//                        public  void run() {
//                            saveImage(bm,"my1.PNG");
//                            if(loadImage("my1.PNG")!=null)
//                                photo.setImageBitmap(loadImage("my1.PNG"));
//                        }
//                    });
//                    Log.e("get",String.valueOf(33));
//                    http.UploadPicture("http://172.18.92.176:3333/test",Environment.getExternalStorageDirectory().getAbsolutePath() + "/public/my1.PNG" );
//                    Log.e("get",String.valueOf(35));
//                }  catch (Exception e) {//异常处理
//                    e.printStackTrace();
//                    mHandler.post( new Runnable(){
//                        public void run(){
//                            btn_logIn.setText("433");
//                        }
//                    });
//                }
//            }
//        }.start();
//        if(loadImage("my1.PNG")!=null)
//            photo.setImageBitmap(loadImage("my1.PNG"));
//        else
//            Log.e("22222222222","33333333333");

//        btn_logIn = (Button) findViewById(R.id.log_in);

    }
    private void bindListeners(){
        btn_signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if(stage.equals("signin")){
                     btn_signUp.setText("SIGN IN");
                     btn_signIn.setText("SIGN UP");
                     team.setVisibility(View.VISIBLE);
                     stage = "signup";
                 }
                 else{
                     btn_signIn.setText("SIGN IN");
                     btn_signUp.setText("SIGN UP");
                     team.setVisibility(View.GONE);
                     stage = "signin";
                 }
            }
        });
        btn_signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_userName.getText().toString().equals("")){
                    toast("用户名为空");
                }
                else if(et_userPassword.getText().toString().equals("")){
                    toast("密码为空");
                }
                else if(et_team_id.getText().toString().equals("")&&stage.equals("signup")){
                    toast("团队id为空");
                }
                else{
                    progressBar.setVisibility(View.VISIBLE);
                    if(stage.equals("signin")){
                        ServiceFactory.getmRetrofit("http://172.18.92.176:3333")
                                .create(BrunoService.class)
                                .signin(et_userName.getText().toString(),et_userPassword.getText().toString())
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Outcome>(){
                                    @Override
                                    public void onCompleted() {
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                    @Override
                                    public void onError(Throwable e) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onNext(Outcome outcome) {
                                        if(outcome.stage.equals("TRUE")){
                                            SharedPreferences sharedPref = getApplication().getSharedPreferences("MY_PREFERENCE",
                                                    Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("name", et_userName.getText().toString());
                                            editor.apply();
                                            Intent intent = new Intent(LoginActivity.this, Main.class);
                                            startActivity(intent);
                                        }
                                        else{
                                            toast(outcome.stage);
                                        }
                                    }
                                });
                    }
                    else{
                        ServiceFactory.getmRetrofit("http://172.18.92.176:3333")
                                .create(BrunoService.class)
                                .signup(et_userName.getText().toString(),et_userPassword.getText().toString(),et_team_id.getText().toString())
                                .subscribeOn(Schedulers.newThread())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Subscriber<Outcome>(){
                                    @Override
                                    public void onCompleted() {
                                        progressBar.setVisibility(View.INVISIBLE);
                                    }
                                    @Override
                                    public void onError(Throwable e) {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(LoginActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                    }
                                    @Override
                                    public void onNext(Outcome outcome) {
                                        if(outcome.stage.equals("TRUE")){
                                            SharedPreferences sharedPref = getApplication().getSharedPreferences("MY_PREFERENCE",
                                                    Context.MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPref.edit();
                                            editor.putString("name", et_userName.getText().toString());
                                            editor.apply();
                                            Intent intent = new Intent(LoginActivity.this, Main.class);
                                            startActivity(intent);
                                        }
                                        else
                                             toast(outcome.stage);
                                    }
                                });
                    }
                }
//
//                new Thread() {
//                    public  void run() {
//                        try {
//                            final InputStream is = http.conect("http://172.18.92.176:3333/user?name=Bruno");
//                            final BufferedReader br = new BufferedReader(new InputStreamReader(is));
//                            final String in = br.readLine();
//                            mHandler.post( new Runnable(){
//                                public  void run() {
//                                    btn_logIn.setText(in);
//                                }
//                            });
//
//                        }  catch (Exception e) {//异常处理
//                            mHandler.post( new Runnable() {
//                                public void run() {
//                                    btn_logIn.setText("233");
//                                }
//                            });
//                        }
//                    }
//                }.start();
                //Intent intent = new Intent(LoginActivity.this, Main.class);
                //startActivity(intent);
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
        if (mFile.exists()){
            Bitmap bitmap = BitmapFactory.decodeFile(path);
            return bitmap;
        }
        else
            return null;
    }
    public void init_toast(){
        toast = Toast.makeText(getApplicationContext(), "带图片的Toast", Toast.LENGTH_SHORT);// 显示时间也可以是数字
        toast.setGravity(Gravity.CENTER, 0, 0);// 最上方显示
        LinearLayout toastLayout = (LinearLayout) toast.getView();
        ImageView imageView = new ImageView(getApplicationContext());
        toastLayout.addView(imageView, 0);// 0 图片在文字的上方 ， 1 图片在文字的下方
        imageView.setImageResource(R.drawable.error);
    }
    public void toast(String message){
        toast.setText(message);
        toast.show();
    }
}
