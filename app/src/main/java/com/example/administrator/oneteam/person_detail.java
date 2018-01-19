package com.example.administrator.oneteam;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.oneteam.Factory.ServiceFactory;
import com.example.administrator.oneteam.Service.BrunoService;
import com.example.administrator.oneteam.model.Outcome;
import com.example.administrator.oneteam.model.Person;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.administrator.oneteam.R.id.imageView;
import static com.example.administrator.oneteam.R.id.person_age;
import static com.example.administrator.oneteam.R.id.person_image;

public class person_detail extends AppCompatActivity {
    TextView name,sex,age,time,email,position,back;
    ImageView photo;
    ConstraintLayout p_lt,n_lt,e_lt,posi_lt,s_lt,j_lt,a_lt;
    AlertDialog.Builder alertDialog;
    public static final int TAKE_PHOTO = 1;//启动相机标识
    public static final int SELECT_PHOTO = 2;//启动相册标识
    private File outputImagepath;//存储拍完照后的图片的路径
    String Picture;
    private Bitmap orc_bitmap;//拍照和相册获取图片的Bitmap
    Context context;
    private String   newImagePath;//新头像的url
    private Person person;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);

        //创建pict文件夹
        Picture = getApplicationContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath();
        context = getApplicationContext();
        id="48";
        init_view();
        init_listener();
        SharedPreferences sharedPref = this.getSharedPreferences("MY_PREFERENCE",
                Context.MODE_PRIVATE);
//        ServiceFactory.getmRetrofit("http://172.18.92.176:3333")
//                .create(BrunoService.class)
//                .getUserByName(sharedPref.getString("name",""))
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Subscriber<Person>(){
//                    @Override
//                    public void onCompleted() {
//
//                    }
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("33",e.getMessage());
//                        Toast.makeText(person_detail.this,e.getMessage(),Toast.LENGTH_SHORT).show();
//                    }
//                    @Override
//                    public void onNext(Person outcome){
//                        person = outcome;
//                        id= String.valueOf(person.person_id);
//                        time.setText(person.join_in_time);
//                        if(person.age!=0)
//                            ini_text();
//                    }
//                });
    }

    private void ini_text() {
        name.setText(person.name);
        sex.setText(person.sex);
        age.setText(String.valueOf(person.age));
        position.setText((person.position.equals("leader"))?"队长":"队员");
        email.setText(person.email);
        time.setText(person.join_in_time);
    }
    private void update(){
        ServiceFactory.getmRetrofit("http://172.18.92.176:3333")
                .create(BrunoService.class)
                .update_user(id,name.getText().toString(),sex.getText().toString(),age.getText().toString(),email.getText().toString())
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Outcome>(){
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(person_detail.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(Outcome outcome) {
                    }
                });
    }
    final String[] way = new String[]{"拍摄","从相册选择"};
    private void init_listener() {
//        n_lt.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                init_alertdialog(name);
//            }
//        });
        s_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_alertdialog(sex);
            }
        });
        p_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                final AlertDialog.Builder dialog = new AlertDialog.Builder(person_detail.this);
                dialog.setTitle("").setItems(way,new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(which==0){
                            xiangjiClick(view);
                        }
                        if(which==1){
                            xiangceClick(view);
                        }
                    }
                }).create().show();
                //todo take or choose photo
            }
        });
        a_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_alertdialog(age);
            }
        });
        e_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_alertdialog(email);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init_view() {
        name = (TextView)findViewById(R.id.person_name);
        n_lt = (ConstraintLayout)findViewById(R.id.n_lt);
        sex = (TextView)findViewById(R.id.person_sex);
        s_lt = (ConstraintLayout)findViewById(R.id.s_lt);
        age = (TextView)findViewById(R.id.person_age);
        a_lt = (ConstraintLayout)findViewById(R.id.a_lt);
        time = (TextView)findViewById(R.id.person_time);
        j_lt = (ConstraintLayout)findViewById(R.id.j_lt);
        email = (TextView)findViewById(R.id.person_email);
        e_lt = (ConstraintLayout)findViewById(R.id.e_lt);
        position = (TextView)findViewById(R.id.person_position);
        posi_lt =(ConstraintLayout)findViewById(R.id.posi_lt);
        back = (TextView)findViewById(R.id.person_back);
        photo = (ImageView)findViewById(R.id.person_image);
        p_lt = (ConstraintLayout)findViewById(R.id.p_lt);
    }
    void init_alertdialog(final TextView view){
        LayoutInflater factor = LayoutInflater.from(person_detail.this);
        final View view_in = factor.inflate(R.layout.inflate, null);
        alertDialog= new AlertDialog.Builder(person_detail.this);
        alertDialog.setTitle("修改信息");
        alertDialog.setPositiveButton("确定修改",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText tmp = view_in.findViewById(R.id.inflate_item);
                view.setText(tmp.getText());
                update();
            }
        });
        alertDialog.setNegativeButton("放弃修改",null);
        alertDialog.setView(view_in);
        EditText tmp = view_in.findViewById(R.id.inflate_item);
        tmp.setText(view.getText().toString());
        alertDialog.show();
    }
    /**
     * 打开相机
     */
    public void xiangjiClick(View view) {
        //checkSelfPermission 检测有没有 权限，PackageManager.PERMISSION_GRANTED 有权限，PackageManager.PERMISSION_DENIED  拒绝权限，一定要先判断权限,再打开相机,否则会报错
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(person_detail.this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        else {
            try {
                take_photo();//已经授权了就调用打开相机的方法
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 拍照获取图片
     **/
    public void take_photo() throws ClassNotFoundException {
        // 激活相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断存储卡是否可以用，可用进行存储
        if ( Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss");
            String filename = timeStampFormat.format(new Date());
            outputImagepath = new File(Picture, id + ".jpg");
            //按时间命名
            // 从文件中创建uri
            Uri uri = Uri.fromFile(outputImagepath);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        }
        // 开启一个带有返回值的Activity，请求码为PHOTO_REQUEST_CAREMA
        startActivityForResult(intent, TAKE_PHOTO);
    }
    /**
     * 打开相册
     */
    public void xiangceClick(View view) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        } else {/*打开相册*/
            Intent intent = new Intent("android.intent.action.GET_CONTENT");
            intent.setType("image/*");
            startActivityForResult(intent, SELECT_PHOTO);
        }
    }


    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void handleImgeOnKitKat(Intent data) {
        String imagePath = null;
        Uri uri = data.getData();
        Log.d("uri=intent.getData :", "" + uri);
        if (DocumentsContract.isDocumentUri(this, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);        //数据表里指定的行
            Log.d("getDocumentId(uri) :", "" + docId);
            Log.d("uri.getAuthority() :", "" + uri.getAuthority());
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        }
        displayImage(imagePath);
    }
    /**
     * 拍完照和从相册获取玩图片都要执行的方法(根据图片路径显示图片)
     */
    private void displayImage(String imagePath) {
        if (!TextUtils.isEmpty(imagePath)) {
            newImagePath = imagePath;
            orc_bitmap = BitmapFactory.decodeFile(imagePath);//获取图片 // orc_bitmap = comp(BitmapFactory.decodeFile(imagePath)); //压缩图
            photo.setImageBitmap(orc_bitmap);
            File ph = new File(imagePath);
            ServiceFactory.getmRetrofit("http://172.18.92.176:3333")
                    .create(BrunoService.class)
                    .upload(MultipartBody.Part.createFormData("file", id+".png", RequestBody.create(MediaType.parse("image/png"), new File(imagePath))) )
                    .subscribeOn(Schedulers.newThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Subscriber<Outcome>(){
                        @Override
                        public void onCompleted() {

                        }
                        @Override
                        public void onError(Throwable e) {
                            Log.e("33",e.getMessage());
                            Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                        @Override
                        public void onNext(Outcome outcome){

                        }
                    });
        } else {
            Toast.makeText(this, "图片获取失败", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * 通过uri和selection来获取真实的图片路径,从相册获取图片时要用
     */
    private String getImagePath(Uri uri, String selection) {
        String path = null;
        Cursor cursor = getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

}
