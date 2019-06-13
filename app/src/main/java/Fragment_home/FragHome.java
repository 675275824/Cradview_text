
package Fragment_home;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.app.AlertDialog;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.asuna.cradview_text.*;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.asuna.cradview_text.R.id.fab01Add;
import static com.example.asuna.cradview_text.R.id.src_atop;

public class FragHome extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Context mContext = null;
    List<CardListview> cardListviews2;
    private ListView listView2;
    MyAdpter myAdpter2;
    List<CardListview> cardListviews1;
    private ListView listView1;
    MyCelltionAdpter myAdpter1;
    List<CardListview> cardListviews3;
    private ListView listView3;
    MyFriendAdpter myAdpter3;
    public int friend=0;
    View view1;
    String str_acc1, str_email1, dateStr1;


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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (myAdpter2 == null) {

        }
        if (myAdpter1 == null) {

        }
        super.onActivityCreated(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.home_tab);
        viewPager = (ViewPager) view.findViewById(R.id.home_viewpager);
        initTab();
        mContext = getContext();

        return view;
    }

    private void initTab() {

        List<View> viewList = new ArrayList<>();
        List<String> titlelist = new LinkedList<>();
        titlelist.add("收藏");
        titlelist.add("账号");
        titlelist.add("好友");
        View view1 = getLayoutInflater().inflate(R.layout.home_live, null);
        View view2 = getLayoutInflater().inflate(R.layout.home_hot, null);
        View view3 = getLayoutInflater().inflate(R.layout.home_love, null);
        //账号点击事件
        FloatingActionButton win2 = view2.findViewById(R.id.fab01Add);
        win2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showWindows2(view);
            }
        });
        //收藏点击事件
        FloatingActionButton win1 = view1.findViewById(R.id.live_add);
        win1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                showWindows1(v);
            }
        });
        //添加好友
        FloatingActionButton win3=view3.findViewById(R.id.friend_add);
        win3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showWindows3(v);
            }
        });


        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        /*
        账号添加页面适配器添加
         */
        cardListviews2 = new ArrayList<>();
        listView2 = view2.findViewById(R.id.account_listview);
        myAdpter2 = new MyAdpter(getActivity(), cardListviews2);

        /*
        收藏添加页面适配器添加
         */
        cardListviews1 = new ArrayList<>();
        listView1 = view1.findViewById(R.id.cellection_listview);
        myAdpter1 = new MyCelltionAdpter(getActivity(), cardListviews1);

        cardListviews3 = new ArrayList<>();
        listView3 = view3.findViewById(R.id.friend_listview);
        myAdpter3 = new MyFriendAdpter(getActivity(), cardListviews3);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setAdapter(new ViewPagerAdapter(viewList, titlelist));
        tabLayout.getTabAt(1).select();
    }

    private void showWindows3(View view) {
        final View contentView = LayoutInflater.from(mContext).inflate(R.layout.love_dialog, null);//弹窗布局
        //设置按钮的点击事件
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        Button button = contentView.findViewById(R.id.btn_friend);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("test", "1");

                EditText account = (contentView.findViewById(R.id.friend_input));
                str_acc1 = account.getText().toString();
                EditText email = (contentView.findViewById(R.id.email_friend));
                str_email1 = email.getText().toString();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                dateStr1 = dateformat.format(System.currentTimeMillis());

                postRequest2(str_acc1);
                Log.i("friend1", String.valueOf(friend));
                if(friend==1)
                {

                }
                popupWindow.dismiss();

                friend=0;

            }
        });
        //弹出框位置
        int local[] = new int[2];
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.TOP, 100, 200);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度1

            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }

    /*
    收藏弹出框
     */
    private void showWindows1(View view) {
        final View contentView = LayoutInflater.from(mContext).inflate(R.layout.live_dialog, null);//弹窗布局
        //设置按钮的点击事件
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        Button button = contentView.findViewById(R.id.btnc_enroll);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("test", "1");
                String str_acc, str_pass, str_beizh;
                EditText account = (contentView.findViewById(R.id.neirong_input));
                str_acc = account.getText().toString();
                EditText password = contentView.findViewById(R.id.coming_input);
                str_pass = password.getText().toString();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = dateformat.format(System.currentTimeMillis());
                int r=0, m;
                m = (int) (Math.random() * 10 % 5);
                switch (m) {
                    case 0:
                        r = R.drawable.cell1;
                        break;
                    case 1:
                        r = R.drawable.cell2;
                        break;
                    case 2:
                        r = R.drawable.cell3;
                        break;
                    case 3:
                        r = R.drawable.cell4;
                        break;
                    case 4:
                        r = R.drawable.cell5;
                        break;
                    default:
                }

                cardListviews1.add(new CardListview("内容概要：" + str_acc, "收藏来源：" + str_pass, dateStr.substring(dateStr.length() - 8), null, r));
                postRequest1(str_acc,str_pass);
                listView1.setAdapter(myAdpter1);
                myAdpter1.notifyDataSetChanged();
                popupWindow.dismiss();

            }
        });
        //弹出框位置
        int local[] = new int[2];
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.TOP, 100, 200);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度1

            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }

    /*
    账号弹出框
     */
    private void showWindows2(View view) {
        //自定义布局
        final View contentView = LayoutInflater.from(mContext).inflate(R.layout.hot_dialog, null);//弹窗布局
        final View cruuentView = this.getLayoutInflater().inflate(R.layout.account_card, null);//单个listview布局
        //设置按钮的点击事件
        final PopupWindow popupWindow = new PopupWindow(contentView, ViewPager.LayoutParams.WRAP_CONTENT, ViewPager.LayoutParams.WRAP_CONTENT, true);
        popupWindow.setTouchable(true);
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                return false;
            }
        });
        Button button = contentView.findViewById(R.id.btn_enroll);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.i("test", "1");
                String str_acc, str_pass, str_beizh;
                EditText account = (contentView.findViewById(R.id.account_input));
                str_acc = account.getText().toString();
                EditText password = contentView.findViewById(R.id.password_input);
                str_pass = password.getText().toString();
                EditText beizhu = contentView.findViewById(R.id.beizhu);
                str_beizh = beizhu.getText().toString();
                SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String dateStr = dateformat.format(System.currentTimeMillis());
                cardListviews2.add(new CardListview("账号：" + str_acc, "密码：" + str_pass, dateStr.substring(dateStr.length() - 8), "备注：" + str_beizh, R.drawable.sao));
                postRequest(str_acc,str_pass,str_beizh);
                listView2.setAdapter(myAdpter2);
                myAdpter2.notifyDataSetChanged();
                int n = myAdpter2.getCount();
                popupWindow.dismiss();

            }
        });
        //弹出框位置
        int local[] = new int[2];
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = 0.4f;
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        getActivity().getWindow().setAttributes(lp);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.LEFT | Gravity.TOP, 100, 200);
        popupWindow.update();
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            // 在dismiss中恢复透明度1

            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getActivity().getWindow().setAttributes(lp);
            }
        });

    }


    @SuppressLint("HandlerLeak")
    private Handler mHandle2 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                String ReturnMessage = (String) msg.obj;
                Log.i("获取的返回信息",ReturnMessage);
                try {
                    JSONObject jsonObject=new JSONObject(ReturnMessage);
                    //Toast.makeText(Login.this,jsonObject.getString("login"),Toast.LENGTH_SHORT).show();
                    String str1=jsonObject.getString("friend");
                    //String str2=jsonObject.getString("re");
                    Log.i("msg,",str1);
                    if(str1.equals("false")&&jsonObject.getString("re").equals("true")){

                        Thread thread=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        friend=1;
                        cardListviews3.add(new CardListview("好友账号：" + str_acc1,"好友邮箱："+str_email1,dateStr1.substring(dateStr1.length() - 8), null,R.drawable.sao));
                        listView3.setAdapter(myAdpter3);
                        myAdpter3.notifyDataSetChanged();

                        Log.i("friend", String.valueOf(friend));
                        Toast.makeText(getActivity(),"添加成功",Toast.LENGTH_SHORT).show();
                    }
                    else if(jsonObject.getString("friend").equals("true")){

                        Toast.makeText(getActivity(),"好友已经存在",Toast.LENGTH_SHORT).show();
                    }
                    else if(jsonObject.getString("re").equals("false")){
                        Toast.makeText(getActivity(),"好友未注册",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(getActivity(),"添加失败",Toast.LENGTH_SHORT).show();
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
                    if(jsonObject.getString("add_m").equals("true_addm")){

                        Thread thread=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Toast.makeText(getActivity(),"添加成功",Toast.LENGTH_SHORT).show();
                    }
                    else if(jsonObject.getString("add_m").equals("false_addm")){

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


    @SuppressLint("HandlerLeak")
    private Handler mHandle1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.what==1){
                String ReturnMessage = (String) msg.obj;
                Log.i("获取的返回信息",ReturnMessage);
                try {
                    JSONObject jsonObject=new JSONObject(ReturnMessage);
                    //Toast.makeText(Login.this,jsonObject.getString("login"),Toast.LENGTH_SHORT).show();
                    if(jsonObject.getString("add_c").equals("true_addc")){

                        Thread thread=new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        Toast.makeText(getActivity(),"收藏成功",Toast.LENGTH_SHORT).show();
                    }
                    else if(jsonObject.getString("add_c").equals("false_addc")){

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

    private void postRequest(String username,String password,String beizhu){
        //建立请求表单，添加上传服务器的参数
        FormBody.Builder requestBody = new FormBody.Builder();
        requestBody.add("acc_account", username);
        requestBody.add("acc_password", password);
        requestBody.add("acc_annotation", beizhu);
        requestBody.add("usernumber2", "111");
        //发起请求
        final Request request = new Request.Builder()
                .url("http://139.217.19.18/database_homework/add_account.php")
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
    private void postRequest1(String username,String password){
        //建立请求表单，添加上传服务器的参数
        FormBody.Builder requestBody = new FormBody.Builder();
        requestBody.add("coll_content", username);
        requestBody.add("coll_sourse", password);
        //发起请求
        final Request request = new Request.Builder()
                .url("http://139.217.19.18/database_homework/add_collection.php")
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
                        mHandle1.obtainMessage(1,response.body().string()).sendToTarget();
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
    private void postRequest2(String username){
        //建立请求表单，添加上传服务器的参数
        FormBody.Builder requestBody = new FormBody.Builder();
        requestBody.add("fri_number", username);
        //发起请求
        final Request request = new Request.Builder()
                .url("http://139.217.19.18/database_homework/add_friend.php")
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
                        mHandle2.obtainMessage(1,response.body().string()).sendToTarget();
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


