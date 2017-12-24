package com.example.administrator.oneteam.tools;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.oneteam.R;
import com.example.administrator.oneteam.Utils.ContactFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by D105-01 on 2017/12/24.
 */

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.ViewHolder>{

    private List<List<String>> mWeeks = new ArrayList<>();

    static class ViewHolder extends RecyclerView.ViewHolder{

        View itemView;
        TextView tv_sun;
        TextView tv_mon;
        TextView tv_tue;
        TextView tv_wed;
        TextView tv_thu;
        TextView tv_fri;
        TextView tv_sat;

        public ViewHolder(View view){

            super(view);

            itemView = view;

            tv_sun = view.findViewById(R.id.sun_calendar_item);
            tv_mon = view.findViewById(R.id.mon_calendar_item);
            tv_tue = view.findViewById(R.id.tue_calendar_item);
            tv_wed = view.findViewById(R.id.wed_calendar_item);
            tv_thu = view.findViewById(R.id.thu_calendar_item);
            tv_fri = view.findViewById(R.id.fri_calendar_item);
            tv_sat = view.findViewById(R.id.sat_calendar_item);

        }
    }

    public CalendarAdapter(){

        for (int i = 0; i < 3; i++){
            List<String> week = new ArrayList<>();
            for (int j = 0; j < 7; j++){
                week.add(String.valueOf(j));
            }
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

        List<String> week = mWeeks.get(position);

        holder.tv_sun.setText(week.get(0));
        holder.tv_mon.setText(week.get(1));
        holder.tv_tue.setText(week.get(2));
        holder.tv_wed.setText(week.get(3));
        holder.tv_thu.setText(week.get(4));
        holder.tv_fri.setText(week.get(5));
        holder.tv_sat.setText(week.get(6));
    }

    @Override
    public int getItemCount() {
        return mWeeks.size();
    }
}
