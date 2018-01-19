package com.example.administrator.oneteam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.SimpleResource;
import com.example.administrator.oneteam.Factory.ServiceFactory;
import com.example.administrator.oneteam.Service.BrunoService;
import com.example.administrator.oneteam.model.Expenditure;
import com.example.administrator.oneteam.model.Task;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class expense_detail extends AppCompatActivity {
    TextView task_name,back,description,money,time,state;
    ImageView photo;
    String expense_id;
    Expenditure expenditure;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expense_detail);
        init_view();
        expense_id = getIntent().getStringExtra("id");
        ServiceFactory.getmRetrofit("http://172.18.92.176:3333")
                .create(BrunoService.class)
                .get_expense_by_id(expense_id)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Expenditure>(){
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(Expenditure outcome){
                        expenditure= outcome;
                        money.setText(String.valueOf(outcome.money));
                        time.setText(outcome.expenditure_date);
                        state.setText(outcome.state.equals("undone")?"未报销":"已报销");
                        description.setText("支出描述: "+outcome.expenditure_description);
                        //Glide.with(getApplication()).load("http://172.18.92.176:3333/"+String.valueOf(outcome.person_id)+".png").into(photo);
                        get_name();
                    }
                });


    }

    private void init_view() {
        task_name = (TextView)findViewById(R.id.expense_task_name);
        money = (TextView)findViewById(R.id.expense_num);
        back = (TextView)findViewById(R.id.expense_back);
        time = (TextView)findViewById(R.id.expensetime);
        state = (TextView)findViewById(R.id.expense_state);
        description =(TextView)findViewById(R.id.en_des);
        photo=(ImageView)findViewById(R.id.expense_image);
    }

    public void get_name() {
        ServiceFactory.getmRetrofit("http://172.18.92.176:3333")
                .create(BrunoService.class)
                .get_task(String.valueOf(expenditure.task_id))
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Task>(){
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplication(),e.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onNext(Task outcome){
                        task_name.setText(outcome.task_name);
                    }
                });
    }
}
