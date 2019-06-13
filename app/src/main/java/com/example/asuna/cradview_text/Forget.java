package com.example.asuna.cradview_text;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Forget extends AppCompatActivity {
    private EditText name;
    private EditText pwd;
    private ImageView reg;
    private EditText emiltext;
    String usernames,passwords,emilnumber;


    private CookieJar cookieJar = new CookieJar() {
        private final Map<String, List<Cookie>> cookiesMap = new HashMap<String, List<Cookie>>();

        public void saveFromResponse(HttpUrl arg0, List<Cookie> arg1) {
            String host = arg0.host();
            List<Cookie> cookiesList = cookiesMap.get(host);
            if (cookiesList != null) {
                cookiesMap.remove(host);
            }
            cookiesMap.put(host, arg1);
        }

        public List<Cookie> loadForRequest(HttpUrl arg0) {
            List<Cookie> cookiesList = cookiesMap.get(arg0.host());
            return cookiesList != null ? cookiesList : new ArrayList<Cookie>();
        }
    };


    private OkHttpClient okHttpClient = new OkHttpClient.Builder().cookieJar(cookieJar).build();



    @SuppressLint("HandlerLeak")
    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                String ReturnMessage = (String) msg.obj;
                Log.i("获取的返回信息",ReturnMessage);
                try {
                    JSONObject jsonObject=new JSONObject(ReturnMessage);
                    //Toast.makeText(Login.this,jsonObject.getString("login"),Toast.LENGTH_SHORT).show();
                    String str1 = jsonObject.getString("number");
                    String str2 = jsonObject.getString("mail");
                    if(str1.equals("true")&&str2.equals("true")){
                        Toast.makeText(Forget.this,"修改成功",Toast.LENGTH_SHORT).show();
                        Thread thread=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(2000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Intent intent=new Intent(Forget.this,Login.class);
                        startActivity(intent);
                    }
                    else if(str1.equals("false")){
                        Toast.makeText(Forget.this,"账号不正确，请重新输入",Toast.LENGTH_SHORT).show();
                        name.setText("");
                        pwd.setText("");
                        emiltext.setText("");
                    } else if(str2.equals("false")){
                        Toast.makeText(Forget.this,"邮箱不正确，请重新输入",Toast.LENGTH_SHORT).show();
                        name.setText("");
                        pwd.setText("");
                        emiltext.setText("");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                //final UserBean userBean=new Gson().fromJson(ReturnMessage,UserBean.class);
                //final String AA = userBean.getMsg();
                /***
                 * 在此处可以通过获取到的Msg值来判断
                 * 给出用户提示注册成功 与否，以及判断是否用户名已经存在
                 */
                // Log.i("MSG",AA);
            }
            else{

            }
        }
    };

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forget);

        //获取账号密码
        name = findViewById(R.id.account_foget);
        pwd = findViewById(R.id.password_forget);
        reg = findViewById(R.id.btn_forget);
        emiltext = findViewById(R.id.email_forget);


        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usernames = name.getText().toString().trim();
                passwords = pwd.getText().toString().trim();
                emilnumber = emiltext.getText().toString().trim();
                postRequest(usernames,passwords,emilnumber);
            }
        });
    }
    private void postRequest(String username,String password,String emil){
        //建立请求表单，添加上传服务器的参数
        FormBody.Builder requestBody = new FormBody.Builder();
        requestBody.add("usernumber", username);
        requestBody.add("password", password);
        requestBody.add("mallnumber",emil);
        //发起请求
        final Request request = new Request.Builder()
                .url("http://139.217.19.18/database_homework/forget_password.php")
                .post(requestBody.build())
                .build();
        //新建一个线程，用于得到服务器响应的参数

        new Thread(new Thread()){
            @Override
            public void run() {
                Response response=null;
                //回调
                try{
                    response = okHttpClient.newCall(request).execute();
                    Log.i("request", String.valueOf(response));
                    if(response.isSuccessful()){
                        //将服务器响应的参数response.body().string())发送到hanlder中，并更新ui
                        mHandle.obtainMessage(1,response.body().string()).sendToTarget();
                    }
                    else{
                        throw new IOException("Unexpected code:" + response);
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
