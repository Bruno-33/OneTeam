package com.example.administrator.oneteam;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class add_task extends AppCompatActivity {
    TextView description,name,max,ddl,budget,back,done;
    ImageView star1,star5,star2,star3,star4;
    ConstraintLayout nn_lt,nmp_lt,nddl_lt,nb_lt,nd_lt;
    AlertDialog.Builder alertDialog;
    ImageView [] all_star;
    int sum;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);
        init_view();
        init_listener();
    }

    private void init_listener() {
        nn_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_alertdialog(name);
            }
        });
        nmp_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_alertdialog(max);
            }
        });
        nd_lt.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar tod = Calendar.getInstance();
                Log.i("here","????");
                new DatePickerDialog(add_task.this,DatePickerDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                    }//onDateSet是点击了确定后的回调函数，year什么的就是选择的
                },tod.get(Calendar.YEAR),tod.get(Calendar.MONTH),tod.get(Calendar.DAY_OF_MONTH)).show();//设置一开始是今天的日期

            }
        });
        nddl_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //todo show calendar
            }
        });
        nb_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_alertdialog(budget);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo 加入远程数据库
                finish();
            }
        });
        for(int i=0;i<5;++i){
            final int tmp = i+1;
            all_star[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sum=tmp;
                    set_star();
                }
            });
        }
    }
    private void set_star() {
        for(int i=0;i<sum;++i){
            all_star[i].setImageResource(R.drawable.star);
        }
        for(int i=sum;i<5;++i)
            all_star[i].setImageResource(R.drawable.empty_star);
    }

    private void init_view() {
        description = (TextView)findViewById(R.id.new_task_description);
        name = (TextView)findViewById(R.id.new_task_name);
        max = (TextView)findViewById(R.id.new_task_max_person);
        ddl = (TextView)findViewById(R.id.new_task_ddl);
        budget = (TextView)findViewById(R.id.new_task_budget);
        back = (TextView)findViewById(R.id.new_task_back);
        done = (TextView)findViewById(R.id.new_task_done);
        star1 = (ImageView)findViewById(R.id.new_task_star1);
        star2 = (ImageView)findViewById(R.id.new_task_star2);
        star3 = (ImageView)findViewById(R.id.new_task_star3);
        star4 = (ImageView)findViewById(R.id.new_task_star4);
        star5 = (ImageView)findViewById(R.id.new_task_star5);
        nn_lt = (ConstraintLayout)findViewById(R.id.nn_lt);
        nb_lt = (ConstraintLayout)findViewById(R.id.nb_lt);
        nddl_lt = (ConstraintLayout)findViewById(R.id.nddl_lt);
        nd_lt = (ConstraintLayout)findViewById(R.id.nd_lt);
        nmp_lt = (ConstraintLayout)findViewById(R.id.nmp_lt);
        all_star=new ImageView[]{star1,star2,star3,star4,star5};
    }
    void init_alertdialog(final TextView view){
        LayoutInflater factor = LayoutInflater.from(add_task.this);
        final View view_in = factor.inflate(R.layout.inflate, null);
        alertDialog= new AlertDialog.Builder(add_task.this);
        alertDialog.setPositiveButton("确定修改",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditText tmp = view_in.findViewById(R.id.inflate_item);
                view.setText(tmp.getText());
            }
        });
        alertDialog.setNegativeButton("放弃修改",null);
        alertDialog.setView(view_in);
        EditText tmp = view_in.findViewById(R.id.inflate_item);
        tmp.setHint(view.getText().toString());
        tmp.setText("");
        alertDialog.show();
    }
}
