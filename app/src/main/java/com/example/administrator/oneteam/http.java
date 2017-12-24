package com.example.administrator.oneteam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Administrator on 2017/12/23 0023.
 */

public class http {
    public static InputStream  conect(String _url){
        InputStream  br = null;
        try{
            URL url = new URL(_url);
            URLConnection rulConnection = url.openConnection();
            // 此处的urlConnection对象实际上是根据URL的
            // 请求协议(此处是http)生成的URLConnection类
            // 的子类HttpURLConnection,故此处最好将其转化
            // 为HttpURLConnection类型的对象,以便用到
            // HttpURLConnection更多的API.如下:
            HttpURLConnection httpUrlConnection = (HttpURLConnection) rulConnection;
            httpUrlConnection.connect();
            br = httpUrlConnection.getInputStream(); // <===注意，实际发送请求的代码段就在这里

        }catch (Exception e){
            Log.e("3", e.toString());
        }
        return br;
    }
    public static Bitmap downloadphoto(String _url){
        BufferedInputStream bis;
        InputStream get = conect(_url);
        if(get==null) return null;
        bis=new BufferedInputStream(get);
        return BitmapFactory.decodeStream(bis);
    }

}
