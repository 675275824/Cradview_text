package com.example.asuna.cradview_text;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class Search_Ressult extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        TextView textView1=findViewById(R.id.account_result);
        TextView textView2=findViewById(R.id.password_result);
        TextView textView3=findViewById(R.id.beizhu_result);
        Intent intent = getIntent();
        String username=intent.getStringExtra("username");
        String password=intent.getStringExtra("password");
        String beizhu=intent.getStringExtra("beizhu");
        textView1.setText("账号："+username);
        textView2.setText("密码："+password);
        textView3.setText("备注："+beizhu);

    }
}
