package com.example.administrator.oneteam.Utils;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.oneteam.R;
import com.example.administrator.oneteam.expense_detail;
import com.example.administrator.oneteam.model.Task;
import com.example.administrator.oneteam.tools.CommonAdapter;
import com.example.administrator.oneteam.tools.GreetingText;
import com.example.administrator.oneteam.tools.OneTeamCalendar;
import com.example.administrator.oneteam.tools.OneTeamCalendarAdapter;
import com.example.administrator.oneteam.tools.ViewHolder;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by D105-01 on 2017/12/24.
 */

public class TaskPoolFragment  extends Fragment{
    private static final String TAG = "TaskPoolFragment";
    private RecyclerView expense_rv;
    private View view;
    private CommonAdapter<Map<String,String>> commonAdapter;
    private List<Map<String,String>> datalist;
    private SmartRefreshLayout refresh;
    private static final int UPDATE_GREETING_TEXT = 1;
    private LinearLayout window;
    private TextView choose,choose1,choose2;
    private String colorbule = "#3060f0";
    private OneTeamCalendar self_calender;
    @SuppressLint("HandlerLeak")


    public static TaskPoolFragment newInstance(){
        return new TaskPoolFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.task_pool_fragment, null);
//        gt_greetingText =  view.findViewById(R.id.gt_self_task_fragment);
//        setUpGreetingText();
        init_recyclerview();
        init_reflashview();
        init_window();
        final OneTeamCalendar oneTeamCalendar = view.findViewById(R.id.one_team_calendar);
        oneTeamCalendar.setDateClickListener(new OneTeamCalendarAdapter.OnDateClickListener() {
            @Override
            public void onClick(View v) {
                //这里传入的v是一个TextView，不要将其强制转化为其他类型
                //sdf用于将返回的Date转化为“日：月：年”的形式
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
                Date date = oneTeamCalendar.getSelectedDate();
                Log.i(TAG, "onClick: " + sdf.format(date));
            }
        });
        return view;
    }

    private void init_window() {
        self_calender = view.findViewById(R.id.one_team_calendar);
        choose =view.findViewById(R.id.expense_choose);
        choose1 = view.findViewById(R.id.choose1);
        choose2 = view.findViewById(R.id.choose2);
        window =  view.findViewById(R.id.window1);

        choose.setTag(0);
        choose.setTextColor(Color.parseColor("#000000"));

        window.setVisibility(View.INVISIBLE);
        self_calender.setVisibility(View.GONE);
        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(choose.getTag().equals(1)){
                    window.setVisibility(View.INVISIBLE);
                    choose.setTag(0);
                    choose.setTextColor(Color.parseColor("#000000"));
                    self_calender.setVisibility(View.GONE);
                }
                else{
                    choose.setTag(1);
                    window.setVisibility(View.VISIBLE);
                    choose.setTextColor(Color.parseColor(colorbule));
                    self_calender.setVisibility(View.VISIBLE);
                }
            }
        });
        window.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                self_calender.setVisibility(View.GONE);
                window.setVisibility(View.INVISIBLE);
                choose.setTag(0);
                choose.setTextColor(Color.parseColor("#000000"));
            }
        });
        choose1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                self_calender.setVisibility(View.GONE);
                window.setVisibility(View.INVISIBLE);
                choose1.setTextColor(Color.parseColor(colorbule));
                choose.setText(choose1.getText().toString());
                choose2.setTextColor(Color.parseColor("#000000"));
                choose.setTag(0);
                choose.setTextColor(Color.parseColor("#000000"));
            }
        });
        choose2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                self_calender.setVisibility(View.GONE);
                window.setVisibility(View.INVISIBLE);
                choose2.setTextColor(Color.parseColor(colorbule));
                choose.setText(choose2.getText().toString());
                choose1.setTextColor(Color.parseColor("#000000"));
                choose.setTag(0);
                choose.setTextColor(Color.parseColor("#000000"));
            }
        });
    }

    private void init_reflashview() {
        refresh = view.findViewById(R.id.reflash1);
        refresh.setRefreshHeader(new WaveSwipeHeader(getActivity()));//WaveSwipeHeader

        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000);
            }
        });
        refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refresh.finishLoadmore(1000);
            }
        });

    }

    private void init_recyclerview() {
        datalist = new ArrayList<>();
        commonAdapter = new CommonAdapter<Map<String,String>>(getActivity(),R.layout.expense_recyclerview_item,datalist) {
            @Override
            public void convert(ViewHolder holder, Map<String,String> task){
                final TextView title=holder.getView(R.id.rv_name);
                final TextView expense = holder.getView(R.id.rv_expense);
                final ImageView done = holder.getView(R.id.rv_image);
                title.setText(task.get("name"));
                expense.setText(task.get("expense"));
                Glide.with(getActivity()).load("http://172.18.92.176:3333/"+task.get("url")).into(done);
            }
        };
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener(){
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), expense_detail.class);
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position){
            }
        });
        expense_rv = view.findViewById(R.id.self_expense_recyclerview);

        Map<String,String> tmp = new HashMap<>();
        tmp.put("name","test");
        tmp.put("expense","33.33");
        tmp.put("url","my.PNG");
        datalist.add(tmp);
        for(int i=0;i<5;++i){
            tmp = new HashMap<>();
            tmp.put("name","test");
            tmp.put("expense","33.33"+String.valueOf(i));
            tmp.put("url","my.PNG");
            datalist.add(tmp);
        }
        expense_rv.setAdapter(commonAdapter);
        expense_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        commonAdapter.notifyDataSetChanged();

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


}

