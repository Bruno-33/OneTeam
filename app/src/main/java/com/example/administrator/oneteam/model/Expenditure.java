package com.example.administrator.oneteam.model;

import java.util.Date;

/**
 * Created by Administrator on 2018/1/1 0001.
 */

public class Expenditure {

    public int expenditure_id;
    public int person_id;
    public int task_id;
    public String expenditure_description;
    public String expenditure_date;
    public String state;
    public int money;

    public Expenditure(int expenditure_id, int person_id, int task_id, String expenditure_description, String expenditure_date, String state, int money) {
        this.expenditure_id = expenditure_id;
        this.person_id = person_id;
        this.task_id = task_id;
        this.expenditure_description = expenditure_description;
        this.expenditure_date = expenditure_date;
        this.state = state;
        this.money = money;
    }

    public Expenditure() {
        this.expenditure_id = 1;
        this.person_id = 1;
        this.task_id = 1;
        this.expenditure_description = "desp";
        this.expenditure_date = "date";
        this.state = "state";
        this.money = 1;
    }
}
