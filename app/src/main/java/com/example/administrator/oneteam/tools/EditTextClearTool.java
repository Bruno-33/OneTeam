package com.example.administrator.oneteam.tools;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

/**
 * Created by D105-01 on 2017/12/23.
 */

public class EditTextClearTool {
    public static void addClearListener(final EditText editText, final ImageView imageView){
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                //String temp =
                if(s.length() > 0){
                    imageView.setVisibility(View.VISIBLE);
                }
                else {
                    imageView.setVisibility(View.GONE);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editText.setText("");
            }
        });
    }

}
