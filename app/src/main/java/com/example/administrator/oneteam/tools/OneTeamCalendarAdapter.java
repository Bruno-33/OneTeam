package com.example.administrator.oneteam.tools;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.oneteam.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by D105-01 on 2017/12/24.
 */

public class OneTeamCalendarAdapter extends RecyclerView.Adapter<OneTeamCalendarAdapter.ViewHolder>{

    private static final String TAG = "OneTeamCalendarAdapter";

    private List<List<Date>> mWeeks = new ArrayList<>();
    private Calendar calendar = Calendar.getInstance();

    private Date earlyDate = calendar.getTime();
    private Date currentDate = calendar.getTime();
    private Date latestDate = calendar.getTime();
    private Date calendarTime = calendar.getTime();

    private SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
    private SimpleDateFormat getDate = new SimpleDateFormat("dd");
    private List<Date> dates = new ArrayList<>();



    static class ViewHolder extends RecyclerView.ViewHolder{

        View itemView;

        TextView tv_sun;
        TextView tv_mon;
        TextView tv_tue;
        TextView tv_wed;
        TextView tv_thu;
        TextView tv_fri;
        TextView tv_sat;

        List<TextView> tv_lists = new ArrayList<>();

        public ViewHolder(View view){

            super(view);

            itemView = view;

            tv_sun = view.findViewById(R.id.sun_calendar_item); tv_lists.add(tv_sun);
            tv_mon = view.findViewById(R.id.mon_calendar_item); tv_lists.add(tv_mon);
            tv_tue = view.findViewById(R.id.tue_calendar_item); tv_lists.add(tv_tue);
            tv_wed = view.findViewById(R.id.wed_calendar_item); tv_lists.add(tv_wed);
            tv_thu = view.findViewById(R.id.thu_calendar_item); tv_lists.add(tv_thu);
            tv_fri = view.findViewById(R.id.fri_calendar_item); tv_lists.add(tv_fri);
            tv_sat = view.findViewById(R.id.sat_calendar_item); tv_lists.add(tv_sat);

        }
    }

    public OneTeamCalendarAdapter(){

//        for (int i = 0; i < 3; i++){
//            List<String> week = new ArrayList<>();
//            for (int j = 0; j < 7; j++){
//                week.add(String.valueOf(j));
//            }
//            mWeeks.add(week);
//        }

        dates.add(calendarTime);

        for (int i = 0; i < 10; i++){
            calendar.add(Calendar.DATE, -1);
            Date date = calendar.getTime();
            dates.add(0, date);
            //dates.add(0, new Calendar(calendarTime.add(Calendar.DATE, -1)));
        }
        earlyDate = calendar.getTime();
        calendar.add(Calendar.DATE, 10);

        for(int i = 0; i < 10; i++){
            calendar.add(Calendar.DATE, 1);
            Date date = calendar.getTime();
            dates.add(date);
        }

        latestDate = calendar.getTime();
        calendar.add(Calendar.DATE, -10);

        for (int i = 0; i < 3; i++){
            List<Date> week = new ArrayList<>();
            week.addAll(dates.subList(7 * i,  7 * i + 7));
            mWeeks.add(week);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.calendar_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.i(TAG, "onBindViewHolder: " + position);
        List<Date> week = mWeeks.get(position);

        for(int i = 0; i < holder.tv_lists.size(); i++){
            holder.tv_lists.get(i).setText(getDate.format(week.get(i)));
            if (calendar.getTime().compareTo(week.get(i)) == 0){
                holder.tv_lists.get(i).setBackgroundResource(R.drawable.cur_date);
                holder.tv_lists.get(i).setTextColor(Color.rgb(255,255,255));
            }
            if (calendar.getTime().compareTo(week.get(i)) > 0){
                holder.tv_lists.get(i).setTextColor(Color.rgb(180,180,180));
            }
        }

        if(position == 0){
            calendar.setTime(earlyDate);
            List<Date> prevWeek = new ArrayList<>();
            for (int i = 0; i < 7; i++){
                calendar.add(Calendar.DATE, -1);
                Date date = calendar.getTime();
                prevWeek.add(0, date);
            }
            earlyDate = calendar.getTime();
            mWeeks.add(prevWeek);

            //notifyDataSetChanged();
            calendar.setTime(currentDate);
        }

        if(position == mWeeks.size()-1){
            calendar.setTime(latestDate);
            List<Date> nextWeek = new ArrayList<>();
            for (int i = 0; i < 7; i++){
                calendar.add(Calendar.DATE, 1);
                Date date = calendar.getTime();
                nextWeek.add(date);
            }
            latestDate = calendar.getTime();
            mWeeks.add(nextWeek);

            //notifyDataSetChanged();
            calendar.setTime(currentDate);
        }



    }

    @Override
    public int getItemCount() {
        return mWeeks.size();
    }
}
