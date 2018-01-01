package com.example.administrator.oneteam.model;

/**
 * Created by D105-01 on 2018/1/1.
 */

public class Contact {

    private String name;
    private String phonebookLabel;
    private String pinyinName;



    private String phoneNumber;

    public String getPhonebookLabel() {
        return phonebookLabel;
    }

    public void setPhonebookLabel(String phonebookLabel) {
        this.phonebookLabel = phonebookLabel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyinName() {
        return pinyinName;
    }

    public void setPinyinName(String pinyinName) {
        this.pinyinName = pinyinName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

}

