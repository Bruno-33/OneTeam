package com.example.administrator.oneteam;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.oneteam.Factory.ServiceFactory;
import com.example.administrator.oneteam.Service.BrunoService;
import com.example.administrator.oneteam.model.Outcome;

import java.util.Calendar;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class add_expense extends AppCompatActivity {
    TextView description,name,money,time,back,done;
    ConstraintLayout en_lt,ee_lt,et_lt;
    AlertDialog.Builder alertDialog;
    String input_ddl,id,task_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        task_name = getIntent().getStringExtra("name");
        id = getIntent().getStringExtra("id");
        input_ddl="";
        init_view();
        init_listener();
        name.setText(task_name);
    }

    private void init_listener() {
        en_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
        ee_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_alertdialog(money);
            }
        });
        et_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar tod = Calendar.getInstance();
                new DatePickerDialog(add_expense.this,DatePickerDialog.THEME_HOLO_LIGHT,new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        input_ddl=String.valueOf(year)+"-"+String.valueOf(month)+"-"+String.valueOf(dayOfMonth);
                        input_ddl+=" 00:00:00";
                        time.setText(input_ddl);
                    }//onDateSet是点击了确定后的回调函数，year什么的就是选择的
                },tod.get(Calendar.YEAR),tod.get(Calendar.MONTH),tod.get(Calendar.DAY_OF_MONTH)).show();//设置一开始是今天的日期
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
                if(is_null()){
                    Toast.makeText(getApplication(),"部分信息没有输入，请完善后提交",Toast.LENGTH_LONG).show();
                }
                else{
                    ServiceFactory.getmRetrofit("http://172.18.92.176:3333")
                            .create(BrunoService.class)
                            .new_expense("48",id,money.getText().toString(),input_ddl,description.getText().toString())
                            .subscribeOn(Schedulers.newThread())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Subscriber<Outcome>(){
                                @Override
                                public void onCompleted() {

                                }
                                @Override
                                public void onError(Throwable e) {
                                    Toast.makeText(add_expense.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                                }
                                @Override
                                public void onNext(Outcome outcome) {
                                    if(outcome.stage.equals("TRUE")){

                                    }
                                    else{

                                    }
                                }
                            });
                    finish();
                }

            }
        });
    }
    private void init_view() {
        description = (TextView)findViewById(R.id.new_expense_description);
        name = (TextView)findViewById(R.id.new_expense_name);
        back = (TextView)findViewById(R.id.new_expense_back);
        time = (TextView)findViewById(R.id.new_expense_time);
        money = (TextView)findViewById(R.id.new_expense_sum);
        done = (TextView)findViewById(R.id.new_expense_done);
        en_lt = (ConstraintLayout)findViewById(R.id.en_lt);
        ee_lt = (ConstraintLayout)findViewById(R.id.ee_lt);
        et_lt = (ConstraintLayout)findViewById(R.id.et_lt);
    }
    void init_alertdialog(final TextView view){
        LayoutInflater factor = LayoutInflater.from(add_expense.this);
        final View view_in = factor.inflate(R.layout.inflate, null);
        alertDialog= new AlertDialog.Builder(add_expense.this);
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
    public boolean is_null() {
        if(input_ddl.equals("")) return true;
        else if(description.getText().toString().equals("")) return true;
        else if(money.getText().toString().equals("")) return true;
        return false;
    }
}