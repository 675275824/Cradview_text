package com.example.asuna.cradview_text;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class MyCelltionAdpter extends BaseAdapter {
    private List<CardListview> data;
    private LayoutInflater layoutInflater;
    private Context context;

    public MyCelltionAdpter(Context context, List<CardListview> data){
        this.context=context;
        this.data=data;
        this.layoutInflater=LayoutInflater.from(context);
    }

    //单个listview控件
    public final class Part{
        public ImageView imageView;
        public TextView textView1;
        public TextView textView2;
        public TextView textView3;//时间
        public TextView textView4;
    }

    @Override
    public int getCount() {
        return data.size();
    }
    /*
    获得某一位置
     */
    @Override
    public Object getItem(int i) {
        return data.get(i);
    }


    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Log.i("text","ok");
        Part part=null;
        if(view==null){
            part=new Part();
            view=layoutInflater.inflate(R.layout.cellection_card,null);
            part.imageView=view.findViewById(R.id.card_imagec);
            part.textView1=view.findViewById(R.id.neirong);
            part.textView2=view.findViewById(R.id.coming);
            part.textView3=view.findViewById(R.id.timec_card);
            view.setTag(part);
        }else{
            part=(Part)view.getTag();
        }
        part.imageView.setImageResource(data.get(i).getImageId());
        part.textView1.setText(data.get(i).getText1());
        part.textView2.setText(data.get(i).getText2());
        part.textView3.setText(data.get(i).getText3());
        return view;
    }
}
