package com.example.administrator.oneteam;

import android.content.DialogInterface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class add_expense extends AppCompatActivity {
    TextView description,name,money,time,back,done;
    ConstraintLayout en_lt,ee_lt,et_lt;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);
        init_view();
        init_listener();
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
                //todo show calendar  time
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
}