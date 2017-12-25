package com.example.administrator.oneteam.tools;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.example.administrator.oneteam.R;

/**
 * Created by D105-01 on 2017/12/24.
 */

public class OneTeamCalendar extends LinearLayout {

    private RecyclerView rv_calendar;
    private OneTeamCalendarAdapter calendarAdapter;

    public OneTeamCalendar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.calendar, this);

        rv_calendar = findViewById(R.id.rv_calendar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rv_calendar.setLayoutManager(linearLayoutManager);

        new LinearSnapHelper().attachToRecyclerView(rv_calendar);

        calendarAdapter = new OneTeamCalendarAdapter();
        rv_calendar.setAdapter(calendarAdapter);

        rv_calendar.scrollToPosition(1);

    }


}
