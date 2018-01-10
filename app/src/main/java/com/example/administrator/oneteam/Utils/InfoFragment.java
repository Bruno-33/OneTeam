package com.example.administrator.oneteam.Utils;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import java.util.Calendar;
import android.net.sip.SipAudioCall;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.oneteam.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.Executors;

import android.os.Message;

import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;



import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by D105-01 on 2017/12/24.
 */

public class InfoFragment extends Fragment  {
    public static InfoFragment newInstance(){
        return new InfoFragment();
    }
    private static final DateFormat FORMATTER = SimpleDateFormat.getDateInstance();
    public Button jump_day;
    public Button jump_today;
    public Button start;
    public int set_year;
    public int set_month;
    public int set_day;
    public TextView day_time;
    public TextView week_time;
    public TextView month_time;
    public TextView toolbar_month;
    public TextView toolbar_year;
    private Handler mHandler;
    public SimpleDateFormat time;
    public int t;
    public boolean status = false;
    public List<CalendarDay> decoratedates;
    public View view;
    public Typeface typeface;
    public OnDateSelectedListener onDateSelectedListener ;
    Context context;
    public  MaterialCalendarView widget;
    //widget绑定calendarView
  //  @BindView(R.id.calendarView)
    //MaterialCalendarView widget;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       // super.onCreate(savedInstanceState);
        view = inflater.inflate(R.layout.info_fragment, null);
        ButterKnife.bind(getActivity());
        widget = view.findViewById(R.id.calendarView);

        typeface = Typeface.createFromAsset(getActivity().getAssets(),"font/digifaw.ttf");
        ID_Init();
        //监听日期被选择
        onDateSelectedListener= new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                View view_in = layoutInflater.inflate(R.layout.info_dialog_layout,null); //点击日期后，跳转出他的打卡时间段
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setView(view_in);
                builder.create().show();
                TextView day_date = (TextView) view_in.findViewById(R.id.day_date);
                day_date.setText(getSelectedDatesString());
            }
        };
        context = getActivity();
        Calendar_Init();
        Listener_Init();
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

    public void ID_Init(){
        toolbar_month = (TextView)view.findViewById(R.id.toolbar_month);
        toolbar_year = (TextView)view.findViewById(R.id.toolbar_year);
        day_time = (TextView)view.findViewById(R.id.day_time);
        week_time = (TextView)view.findViewById(R.id.week_time);
        month_time = (TextView)view.findViewById(R.id.month_time);
        day_time.setTypeface(typeface);
        week_time.setTypeface(typeface);
        month_time.setTypeface(typeface);
        jump_today = (Button)view.findViewById(R.id.jump_today);
        start = (Button)view.findViewById(R.id.start);
        start.setTypeface(typeface);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void Calendar_Init(){
          widget.setTileWidth(LinearLayout.LayoutParams.MATCH_PARENT);//因为在layout设置其match_parent，他会保持原来的宽长比缩小，所以可以再次设置
        widget.setTopbarVisible(false);//设置头不可见
        //设置点击选择日期改变事件
        widget.setOnDateChangedListener(onDateSelectedListener);
        widget.setShowOtherDates(MaterialCalendarView.SHOW_ALL);//SHOW_ALL = SHOW_OTHER_MONTHS | SHOW_OUT_OF_RANGE | SHOW_DECORATED_DISABLED;
        //检测月份是否改变，改变的话，显示的标题年月需要改变
        widget.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                toolbar_month.setText((date.getMonth()+1)+"月");
                toolbar_year.setText(date.getYear()+"年");
            }
        });
        Calendar instance = Calendar.getInstance();
        widget.setSelectedDate(instance.getTime());//设置一开始是今天的时间
        widget.state().edit().
                commit();

        Collection<CalendarDay> dates=new ArrayList<>();
        widget.addDecorators(
                new TodayDecorator(),
                new MySelectorDecorator(context)
        );
        toolbar_year.setOnClickListener(new View.OnClickListener() { //点击头部，跳转到日期
            @Override
            public void onClick(View v) {
                getDate(v);//获取日期，并且确定的话跳转
            }
        });
        toolbar_month.setOnClickListener(new View.OnClickListener() { //点击头部，跳转到日期
            @Override
            public void onClick(View v) {
                getDate(v);//获取日期，并且确定的话跳转
            }
        });
        new ApiSimulator().executeOnExecutor(Executors.newSingleThreadExecutor());
    }

    //得到与当前的page的相差数量，进行for循环的跳转
    public void jump(int i){
        for(int j = 0; j< i && i >0; j++){
            widget.goToNext();//向后跳
        }
        for(int j = 0; j> i && i <0; j--){
            widget.goToPrevious();//向前跳
        }
    }

    //点击时间，获取日期
    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void getDate(View v){
        final Calendar tod = Calendar.getInstance();
        final int[] i = {0};
        final CalendarDay current_month = widget.getCurrentDate();//得到当前页面的月份和年份
        new DatePickerDialog(getActivity(),DatePickerDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                set_year = year;
                set_month = month;
                widget.setSelectedDate( CalendarDay.from(year,month,dayOfMonth));//跳转到那里，并且标记
                i[0] = (set_year - current_month.getYear())*12+(set_month - current_month.getMonth());//算出需要跳转的页数
                jump(i[0]);
            }
        },tod.get(Calendar.YEAR),tod.get(Calendar.MONTH),tod.get(Calendar.DAY_OF_MONTH)).show();//设置一开始是今天的日期
    }
    //获取被选中日期2017年12月11日
    public String getSelectedDatesString() {
        CalendarDay date = widget.getSelectedDate();
        if (date == null) {
            return "No Selection";
        }
        return FORMATTER.format(date.getDate());
    }


    //同步任务 进行红点的标记
    private class ApiSimulator extends AsyncTask<Void, Void, List<CalendarDay>> {
        @Override
        protected List<CalendarDay> doInBackground(@NonNull Void... voids) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //peiwei
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            ArrayList<CalendarDay> dates = new ArrayList<>();
            calendar.set(2018,0,28);//月份从0开始，加入date中就可以有航电标记啦
            dates.add(CalendarDay.from(calendar));
            calendar.set(2018,1,28);
            dates.add(CalendarDay.from(calendar));
            return dates;
        }
        @Override
        protected void onPostExecute(@NonNull List<CalendarDay> calendarDays) {
            super.onPostExecute(calendarDays);
            if (getActivity().isFinishing()) {
                return;
            }
            widget.addDecorator(new EventDecorator(Color.RED, calendarDays));
        }

    }
    //对今天日期的特殊修饰
    private class TodayDecorator implements DayViewDecorator {
        private final CalendarDay today;
        private final Drawable backgroundDrawable;
        public TodayDecorator() {
            today = CalendarDay.today();
            backgroundDrawable = getResources().getDrawable(R.drawable.toady_circle);
        }
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return today.equals(day);
        }
        @Override
        public void decorate(DayViewFacade view) {
            view.setBackgroundDrawable(backgroundDrawable);
            view.addSpan(new RelativeSizeSpan(1.4f));
        }
    }
    //对选中日期的修饰
    public class MySelectorDecorator implements DayViewDecorator {
        private final Drawable drawable;
        public MySelectorDecorator(Context context) {
            drawable = context.getResources().getDrawable(R.drawable.my_selector);
        }
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return true;
        }
        @Override
        public void decorate(DayViewFacade view) {
            view.setSelectionDrawable(drawable);
            view.addSpan(new StyleSpan(Typeface.BOLD));
        }
    }
    //对一系列点加标注
    public class EventDecorator implements DayViewDecorator {
        private int color;
        private HashSet<CalendarDay> dates;

        public EventDecorator(int color, Collection<CalendarDay> dates) {
            this.color = color;
            this.dates = new HashSet<>(dates);
        }
        @Override
        public boolean shouldDecorate(CalendarDay day) {
            return dates.contains(day);
        }
        @Override
        public void decorate(DayViewFacade view) {
            view.addSpan(new DotSpan(5, color));
        }
    }
    public  void Listener_Init(){
        jump_today.setOnClickListener(new View.OnClickListener() {//跳转到今天
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final CalendarDay current_month = widget.getCurrentDate();//得到当前页面的月份和年份
                final Calendar tod = Calendar.getInstance();//目标年月
                int i = (tod.get(Calendar.YEAR) - current_month.getYear())*12+(tod.get(Calendar.MONTH) - current_month.getMonth());
                jump(i);//在中午12点钱可能会出错
            }
        });
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    if(status){
                        try{
                            Thread.sleep(1000);//1s更新一次
                        }
                        catch (InterruptedException e){
                            e.printStackTrace();
                        }
                        Message my = new Message();
                        my.what = 123;
                        mHandler.sendMessage(my);
                    }
                }
            }
        });
       // thread.start();
        mHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case 123:
                        try{
                            t=t+1;
                            int h = t/3600;
                            int m = (t - h*3600)/60;
                            int s = (t - h*3600 - m*60);
                            String hh ="",mm="",ss="";
                            ss=String.valueOf(s);
                            mm=String.valueOf(m);
                            hh =String.valueOf(h);
                            if(s<10) ss="0"+ss;
                            if(m<10)  mm ="0"+mm;
                            if(h<10)  hh ="0"+hh;
                            if(status)
                                start.setText(mm+":"+ss);
                            if(m>0 && s==0){
                                //peiwei   周时间 月时间++
                                /*
                                day_time++;
                                week_time ++;
                                month_time++;*/
                            }
                        }catch (InternalError e){
                            e.printStackTrace();
                        }
                    case 124:
                        try{

                        }catch (InternalError e){
                            e.printStackTrace();
                        }
                }
            }
        } ;
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = !status;
                if(status){
                }
                else{
                    start.setText("打卡");
                }
            }
        });
    }

}
