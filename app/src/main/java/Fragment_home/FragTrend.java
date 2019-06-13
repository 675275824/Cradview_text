package Fragment_home;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.asuna.cradview_text.*;

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

public class FragTrend extends Fragment {
    @Nullable
    public String scearch;
    public Intent intent;
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
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.trend,container,false);
        SearchView searchView=view.findViewById(R.id.searchView);
        searchView.setSubmitButtonEnabled(true);
        searchView.onActionViewExpanded();
        searchView.setBackgroundColor(0x22ff00ff);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                scearch=s;
                postRequest(s);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        return view;
    }
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
                    intent=new Intent(getActivity(),Search_Ressult.class);
                    intent.putExtra("username",jsonObject.getString("1"));
                    intent.putExtra("password",jsonObject.getString("2"));
                    intent.putExtra("beizhu",jsonObject.getString("3"));
                    startActivity(intent);

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

    private void postRequest(String username){
        //建立请求表单，添加上传服务器的参数
        FormBody.Builder requestBody = new FormBody.Builder();
        requestBody.add("acc_account", username);
        //发起请求
        final Request request = new Request.Builder()
                .url("http://139.217.19.18/database_homework/search.php")
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
