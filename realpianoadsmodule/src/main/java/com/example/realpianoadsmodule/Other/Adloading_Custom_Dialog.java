package com.example.realpianoadsmodule.Other;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.TextView;

import com.example.realpianoadsmodule.R;


public class Adloading_Custom_Dialog extends ProgressDialog {

    private final String txt_title;

    public Adloading_Custom_Dialog(Context context, String title) {
        super(context, R.style.Adload_Dialog);
        this.txt_title = title;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init();
    }

    private void init() {
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        setContentView(R.layout.adload_dialog);
        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(txt_title);
        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        getWindow().setAttributes(params);
    }

    @Override
    public void show() {
        super.show();
    }
}
