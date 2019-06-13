package com.example.asuna.cradview_text;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

public class Home_hot extends Activity {
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_hot);

    }

    //弹出框添加账号
    public void click_addamount(View view) {
        Log.i("msg:","弹出");
        EditText input_editText = new EditText(Home_hot.this);
        AlertDialog.Builder builder = new AlertDialog.Builder(Home_hot.this);
        builder.setTitle("添加账号");
        builder.setView(getLayoutInflater().inflate(R.layout.hot_dialog,null));
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
    }
}
