package com.example.asuna.cradview_text;

import android.widget.ImageView;

public class CardListview {
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private int imageId;
    public CardListview(String test1,String text2,String text3,String text4,int imageId){
        this.text1=test1;//账号
        this.text2=text2;//密码
        this.text3=text3;//时间
        this.text4=text4;//备注
        this.imageId=imageId;//图片
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }

    public String getText3() {
        return text3;
    }

    public String getText4() {
        return text4;
    }

    public int getImageId() {
        return imageId;
    }


}
