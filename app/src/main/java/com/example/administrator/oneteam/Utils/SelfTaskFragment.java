package com.example.administrator.oneteam.Utils;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.oneteam.Factory.ServiceFactory;
import com.example.administrator.oneteam.LoginActivity;
import com.example.administrator.oneteam.Main;
import com.example.administrator.oneteam.R;
import com.example.administrator.oneteam.Service.BrunoService;
import com.example.administrator.oneteam.add_task;
import com.example.administrator.oneteam.expense_detail;
import com.example.administrator.oneteam.model.Outcome;
import com.example.administrator.oneteam.model.Task;
import com.example.administrator.oneteam.task_detail;
import com.example.administrator.oneteam.tools.CommonAdapter;
import com.example.administrator.oneteam.tools.OneTeamCalendar;
import com.example.administrator.oneteam.tools.OneTeamCalendarAdapter;
import com.example.administrator.oneteam.tools.ViewHolder;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.FunGameBattleCityHeader;
import com.scwang.smartrefresh.header.WaterDropHeader;
import com.scwang.smartrefresh.header.WaveSwipeHeader;
import com.scwang.smartrefresh.header.fungame.FunGameHeader;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.footer.FalsifyFooter;
import com.scwang.smartrefresh.layout.impl.RefreshFooterWrapper;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;


import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import java.util.logging.LogRecord;
import com.example.administrator.oneteam.tools.GreetingText;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by D105-01 on 2017/12/24.
 */

public class SelfTaskFragment extends Fragment {

    private RecyclerView self_task_rv,expense_rv;
    private View view;
    private int position;
    private CommonAdapter<Task> commonAdapter;
    private List<Task>[] array;
    private List<Task> datalist,datalist1,datalist2;
    private SmartRefreshLayout refresh;
    private int [] all_star = new int[]{R.id.item_star1,R.id.item_star2,R.id.item_star3,R.id.item_star4,R.id.item_star5};
    private static final int UPDATE_GREETING_TEXT = 1;
    private GreetingText gt_greetingText;
    private LinearLayout window;
    private TextView choose,choose1,choose2,choose3;
    private String colorbule = "#3060f0";
    private OneTeamCalendar self_calender;

    int offset[]={0,0,0};
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
        view= inflater.inflate(R.layout.self_task_fragment, null);
//        gt_greetingText =  view.findViewById(R.id.gt_self_task_fragment);
//        setUpGreetingText();
        position=0;
        datalist = new ArrayList<>();
        datalist1 = new ArrayList<>();datalist2 = new ArrayList<>();
        array = new List[]{new ArrayList<>(),new ArrayList<>(),new ArrayList<>()};
        init_recyclerview();
        init_reflashview();
        init_window();
        self_calender.setDateClickListener(new OneTeamCalendarAdapter.OnDateClickListener() {
            @Override
            public void onClick(View v) {
                array[1].remove(array[1].size()-1);
                change_datalist();
                window.setVisibility(View.INVISIBLE);
                choose.setTextColor(Color.parseColor("#000000"));
                choose.setTag(0);
                self_calender.setVisibility(View.GONE);
            }
        });
        return view;
    }

    private void init_window() {
        self_calender = view.findViewById(R.id.self_calender);
        choose =view.findViewById(R.id.self_choose);
        choose1 = view.findViewById(R.id.choose1);
        choose2 = view.findViewById(R.id.choose2);
        choose3 = view.findViewById(R.id.choose3);
        window =  view.findViewById(R.id.window);

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
                choose3.setTextColor(Color.parseColor("#000000"));
                choose.setTag(0);
                choose.setTextColor(Color.parseColor("#000000"));
                position=0;
                change_datalist();
                commonAdapter.notifyDataSetChanged();
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
                choose3.setTextColor(Color.parseColor("#000000"));
                choose.setTag(0);
                choose.setTextColor(Color.parseColor("#000000"));
                position=1;
                change_datalist();
                commonAdapter.notifyDataSetChanged();
            }
        });
        choose3.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                self_calender.setVisibility(View.GONE);
                window.setVisibility(View.INVISIBLE);
                choose3.setTextColor(Color.parseColor(colorbule));
                choose.setText(choose3.getText().toString());
                choose1.setTextColor(Color.parseColor("#000000"));
                choose2.setTextColor(Color.parseColor("#000000"));
                choose.setTag(0);
                choose.setTextColor(Color.parseColor("#000000"));
                position=2;
                change_datalist();
                commonAdapter.notifyDataSetChanged();
            }
        });
    }

    private void init_reflashview() {
        refresh = view.findViewById(R.id.reflash);
        refresh.setRefreshHeader(new WaveSwipeHeader(getActivity()));//WaveSwipeHeader
        refresh.setEnableLoadmoreWhenContentNotFull(true);
        refresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                if(position==0){
                    Task task = new Task();
                    task.task_state = "done";
                    task.task_mark=4;
                    task.task_name="无人机姿态调整";
                    task.task_deadline="2017/01/25";
                    array[position].add(task);
                    task = new Task();
                    task.task_state = "done";
                    task.task_mark=5;
                    task.task_name="6050的使用";
                    task.task_deadline="2017/01/30";
                    array[position].add(task);
                    task = new Task();
                    task.task_state = "done";
                    task.task_mark=3;
                    task.task_name="无人机云台控制";
                    task.task_deadline="2017/01/28";
                    array[position].add(task);
                    change_datalist();
                }
                else if(position==1){
                    Task task = new Task();
                    task.task_state = "undone";
                    task.task_mark=4;
                    task.task_name="无人机实现翻滚";
                    task.task_deadline="2017/01/25";
                    array[position].add(task);
                    task = new Task();
                    task.task_state = "undone";
                    task.task_mark=3;
                    task.task_name="无人机实现定点移动";
                    task.task_deadline="2017/01/16";
                    array[position].add(task);
                    task = new Task();
                    task.task_state = "undone";
                    task.task_mark=5;
                    task.task_name="CNN人脸识别";
                    task.task_deadline="2017/01/31";
                    array[position].add(task);
                    change_datalist();
                }
                else if(position==2){
                    Task task = new Task();
                    task.task_state = "undone";
                    task.task_mark=4;
                    task.task_name="无人机实现翻滚";
                    task.task_deadline="2017/01/25";
                    array[position].add(task);
                    change_datalist();
                }
                refreshlayout.finishRefresh();
            }
        });
        refresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                offset[position]+=3;
                if(position==0){
                }
                else if(position==1){
                    Task task = new Task();
                    task.task_state = "undone";
                    task.task_mark=5;
                    task.task_name="安卓";
                    task.task_deadline="2017/01/25";
                    array[position].add(task);
                    change_datalist();
                }
               else if(position==2){
                }
                refresh.finishLoadmore();
            }
        });

    }

    private void change_datalist() {
        while(datalist.size()!=0){
            datalist.remove(0);
        }
        for(int i=0;i<array[position].size();++i){
            datalist.add(array[position].get(i));
        }
        commonAdapter.notifyDataSetChanged();
    }

    private void init_recyclerview() {
        self_task_rv = view.findViewById(R.id.self_task_recyclerview);
        commonAdapter = new CommonAdapter<Task>(getActivity(),R.layout.task_recyclerview_item,datalist) {
            @Override
            public void convert(final ViewHolder holder, Task task) {
                final TextView title=holder.getView(R.id.item_title);
                final TextView ddl = holder.getView(R.id.itme_ddl);
                final Button  checkbox = holder.getView(R.id.item_checkbox);
                ImageView star ;
                ddl.setText("DDL:"+ task.task_deadline);
                for (int i=task.task_mark;i<5;++i){
                    star=holder.getView(all_star[i]);
                    star.setVisibility(View.INVISIBLE);
                }
                checkbox.setTag(0);
                if(task.task_state.equals("undone")){
                    checkbox.setBackgroundResource(R.drawable.uncheck);
                    checkbox.setTag(0);
                }
                else{
                    checkbox.setBackgroundResource(R.drawable.check);
                    checkbox.setTag(1);
                }
                checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkbox.getTag().equals(1)){
                            title.setText(title.getText());
                            checkbox.setBackgroundResource(R.drawable.uncheck);
                            checkbox.setTag(0);
                        }else{
                            title.setText(title.getText());
                            checkbox.setTag(1);
                            checkbox.setBackgroundResource(R.drawable.check);
                        }
                    }
                });
                title.setText(task.task_name);
            }
        };
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener(){
            @Override
            public void onClick(int position) {
                Intent intent = new Intent(getContext(), task_detail.class);
                intent.putExtra("id",String.valueOf(datalist.get(position).task_id));
                startActivity(intent);
            }
            @Override
            public void onLongClick(int position) {
            }
        });
        self_task_rv.setAdapter(commonAdapter);
        self_task_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
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
