package com.example.administrator.oneteam.tools;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.oneteam.R;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by D105-01 on 2017/12/31.
 */

public class GreetingText extends LinearLayout {
    private static final String TAG = "GreetingText";
    private TextView tv_content;
    private SimpleDateFormat getHour = new SimpleDateFormat("HH");
    private SimpleDateFormat getMinute = new SimpleDateFormat("m");

    public GreetingText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.greeting_text, this);

        tv_content = findViewById(R.id.content_greeting_text);

        update();

    }

    public void update(){
        Date date = new Date(System.currentTimeMillis());
        int hour = Integer.parseInt(getHour.format(date));
//        Log.i(TAG, "updateInfo: " + hour);
        if(0 <= hour && hour < 6){
            tv_content.setText("已经是凌晨了，赶紧休息吧");
        }
        else if(hour < 12){
            tv_content.setText("早上好，今天请继续努力吧");
        }
        else if(hour < 14){
            tv_content.setText("短暂的午休可以提高工作效率哦");
        }
        else if(hour < 18){
            tv_content.setText("下午好，今天请继续努力吧");
        }
        else if(hour < 21){
            tv_content.setText("晚上好，今天你的工作很出色");
        }
        else {
            tv_content.setText("入夜了，请尽早休息");
        }
    }
}
