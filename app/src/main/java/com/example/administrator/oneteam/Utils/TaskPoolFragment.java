package com.example.administrator.oneteam.Utils;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.oneteam.R;
import com.example.administrator.oneteam.tools.OneTeamCalendar;
import com.example.administrator.oneteam.tools.OneTeamCalendarAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by D105-01 on 2017/12/24.
 */

public class TaskPoolFragment  extends Fragment{
    private static final String TAG = "TaskPoolFragment";
    public static TaskPoolFragment newInstance(){
        return new TaskPoolFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.task_pool_fragment, null);

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
