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

public class person_detail extends AppCompatActivity {
    TextView name,sex,age,time,email,position,back;
    ImageView photo;
    ConstraintLayout p_lt,n_lt,e_lt,posi_lt,s_lt,j_lt,a_lt;
    AlertDialog.Builder alertDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_detail);
        init_view();
        init_listener();
    }

    private void init_listener() {
        n_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_alertdialog(name);
            }
        });
        s_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_alertdialog(sex);
            }
        });
        p_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //todo take or choose photo
            }
        });
        a_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_alertdialog(age);
            }
        });
        e_lt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                init_alertdialog(email);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init_view() {
        name = (TextView)findViewById(R.id.person_name);
        n_lt = (ConstraintLayout)findViewById(R.id.n_lt);
        sex = (TextView)findViewById(R.id.person_sex);
        s_lt = (ConstraintLayout)findViewById(R.id.s_lt);
        age = (TextView)findViewById(R.id.person_age);
        a_lt = (ConstraintLayout)findViewById(R.id.a_lt);
        time = (TextView)findViewById(R.id.person_time);
        j_lt = (ConstraintLayout)findViewById(R.id.j_lt);
        email = (TextView)findViewById(R.id.person_email);
        e_lt = (ConstraintLayout)findViewById(R.id.e_lt);
        position = (TextView)findViewById(R.id.person_position);
        posi_lt =(ConstraintLayout)findViewById(R.id.posi_lt);
        back = (TextView)findViewById(R.id.person_back);
        photo = (ImageView)findViewById(R.id.person_image);
        p_lt = (ConstraintLayout)findViewById(R.id.p_lt);
    }
    void init_alertdialog(final TextView view){
        LayoutInflater factor = LayoutInflater.from(person_detail.this);
        final View view_in = factor.inflate(R.layout.inflate, null);
        alertDialog= new AlertDialog.Builder(person_detail.this);
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
        tmp.setText(view.getText().toString());
        alertDialog.show();
    }
}
