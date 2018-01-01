package com.example.administrator.oneteam.Utils;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.oneteam.R;
import com.example.administrator.oneteam.model.Task;
import com.example.administrator.oneteam.tools.CommonAdapter;
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


import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by D105-01 on 2017/12/24.
 */

public class SelfTaskFragment extends Fragment {

    private RecyclerView self_task_rv;
    private View view;
    private CommonAdapter<Task> commonAdapter;
    private List<Task> datalist;
    private SmartRefreshLayout refresh;
    private Handler handler;
    private int [] all_star = new int[]{R.id.item_star1,R.id.item_star2,R.id.item_star3,R.id.item_star4,R.id.item_star5};
    public static SelfTaskFragment newInstance(){
        return new SelfTaskFragment();
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view= inflater.inflate(R.layout.self_task_fragment, null);
        init_recyclerview();
        init_reflashview();
        return view;
    }

    private void init_reflashview() {
        handler = new Handler() {
            @Override
            public void publish(LogRecord logRecord) {

            }

            @Override
            public void flush() {

            }

            @Override
            public void close() throws SecurityException {

            }
        };
        refresh = view.findViewById(R.id.reflash);
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
        self_task_rv = view.findViewById(R.id.self_task_recyclerview);
        datalist = new ArrayList<>();
        commonAdapter = new CommonAdapter<Task>(getActivity(),R.layout.task_recyclerview_item,datalist) {
            @Override
            public void convert(ViewHolder holder, Task task) {
                final TextView title=holder.getView(R.id.item_title);
                final String font = title.getFontFeatureSettings();
                final Button  checkbox = holder.getView(R.id.item_checkbox);
                final ImageView done = holder.getView(R.id.item_state);
                ImageView star ;
                for (int i=Integer.parseInt(task.task_mark);i<5;++i){
                    star=holder.getView(all_star[i]);
                    star.setVisibility(View.INVISIBLE);
                }
                checkbox.setTag(0);
                done.setImageResource(R.drawable.undone);
                checkbox.setBackgroundResource(R.drawable.uncheck);
                checkbox.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(checkbox.getTag().equals(1)){
                            title.getPaint().setFlags(0);
                            title.setText(title.getText());
                            title.setFontFeatureSettings(font);
                            checkbox.setBackgroundResource(R.drawable.uncheck);
                            checkbox.setTag(0);
                            done.setImageResource(R.drawable.undone);
                        }else{
                            title.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                            title.setText(title.getText());
                            checkbox.setTag(1);
                            checkbox.setBackgroundResource(R.drawable.check);
                            done.setImageResource(R.drawable.done);
                        }
                    }
                });
                title.setText(task.task_name);
            }
        };
        commonAdapter.setOnItemClickListener(new CommonAdapter.OnItemClickListener(){
            @Override
            public void onClick(int position) {
                Toast.makeText(getActivity(),"onCompleted()",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onLongClick(int position) {
            }
        });
        Task test = new Task();
        test.task_name="内部测试";
        test.task_mark="4";
        datalist.add(test);
        test.task_mark="3";
        datalist.add(test);
        test.task_mark="2";
        datalist.add(test);
        for(int i=0;i<5;++i){
            test.task_mark="5";
            datalist.add(test);
            datalist.add(test);
            datalist.add(test);
        }
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
