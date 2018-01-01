package com.example.administrator.oneteam.Utils;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.oneteam.R;
import com.example.administrator.oneteam.tools.GreetingText;

/**
 * Created by D105-01 on 2017/12/24.
 */

public class SelfTaskFragment extends Fragment {

    private static final int UPDATE_GREETING_TEXT = 1;

    private GreetingText gt_greetingText;


    @SuppressLint("HandlerLeak")
    //region 线程处理函数 Handler
    final Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case UPDATE_GREETING_TEXT:
                    gt_greetingText.update();
                    break;
                default:
                    break;
            }
        }
    };
    //endregion

    /**
     * 用于在main中实例化SelfTaskFragment
     * @return
     */
    public static SelfTaskFragment newInstance(){
        return new SelfTaskFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.self_task_fragment, null);

        gt_greetingText = (GreetingText) view.findViewById(R.id.gt_self_task_fragment);
        setUpGreetingText();

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    /**
     * 子线程更新GreetingText
     * 回调函数是Handler
     */
    private void setUpGreetingText(){
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                Message msg = new Message();
                msg.what = UPDATE_GREETING_TEXT;
                handler.sendMessage(msg);
                //每隔十分钟执行一次GreetingText的更新
                handler.postDelayed(this, 1000*60*10);
            }
        };
        runnable.run();
    }
}
