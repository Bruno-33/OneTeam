package com.example.administrator.oneteam;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

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
    public static  void UploadPicture(String uploadUrl,String name) {
        String serverUrl = uploadUrl;
        HttpURLConnection connection = null;
        DataOutputStream dos = null;

        int bytesAvailable, bufferSize, bytesRead;
        int maxBufferSize = 1 * 1024 * 512;
        byte[] buffer = null;

        String boundary = "-----------------------------1954231646874";
        Map<String, String> formParams = new HashMap<String, String>();
        FileInputStream fin = null;

        try {
            URL url = new URL(serverUrl);
            connection = (HttpURLConnection) url.openConnection();
            // 允许向url流中读写数据
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(true);

            // 启动post方法
            connection.setRequestMethod("POST");

            // 设置请求头内容
            connection.setRequestProperty("connection", "Keep-Alive");
            connection
                    .setRequestProperty("Content-Type", "multipart/form-data; boundary=---------------------------1954231646874");

            dos = new DataOutputStream(connection.getOutputStream());
            fin = new FileInputStream(name);

            File file = new File(name);
            dos.writeBytes(boundary);
            dos.writeBytes("\r\n");
            dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename="+file.getName());
            dos.writeBytes("\r\n");
            dos.writeBytes("Content-Type: image/png");
            dos.writeBytes("\r\n");
            dos.writeBytes("\r\n");

            // 取得本地图片的字节流，向url流中写入图片字节流
            bytesAvailable = fin.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];

            bytesRead = fin.read(buffer, 0, bufferSize);
            while (bytesRead > 0) {
                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fin.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fin.read(buffer, 0, bufferSize);
            }
            dos.writeBytes("\r\n");
            dos.writeBytes("-----------------------------1954231646874--");
            dos.writeBytes("\r\n");
            dos.writeBytes("\r\n");

            // Server端返回的信息
            int code = connection.getResponseCode();
            Log.e("get",String.valueOf(code));
//            if (code == 200) {
//                InputStream inStream = connection.getInputStream();
//                ByteArrayOutputStream outSteam = new ByteArrayOutputStream();
//                byte[] buffer = new byte[1024];
//                int len = -1;
//                while ((len = inStream.read(buffer)) != -1) {
//                    outSteam.write(buffer, 0, len);
//                }
//
//                outSteam.close();
//                inStream.close();
//                return new String(outSteam.toByteArray());
//            }

            if (dos != null) {
                dos.flush();
                dos.close();
            }
        }
         catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
