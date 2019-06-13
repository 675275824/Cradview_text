package com.example.asuna.cradview_text;

import android.Manifest;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.provider.ContactsContract;

import java.math.*;

public class ImageHelper {
    private Bitmap bitmap;

    public ImageHelper(Drawable drawable){
        bitmap=((BitmapDrawable)drawable).getBitmap();
    }

    public ImageHelper(Bitmap bitmap){
        this.bitmap=bitmap;
    }

    public Drawable getCircle(int r){
        Bitmap result=Bitmap.createBitmap(2*r,2*r,Bitmap.Config.ARGB_8888);
        Canvas canvas=new Canvas(result);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        Path path=new Path();
        path.addCircle(r,r,r,Path.Direction.CW);
        canvas.clipPath(path);
        int leng=Math.min(bitmap.getWidth(),bitmap.getHeight());
        canvas.drawBitmap(bitmap,new Rect(0,0,leng,leng),new Rect(0,0,2*r,2*r),paint);
        return new BitmapDrawable(result);
    }

    public Drawable getSquare(int leng){
        Bitmap result=Bitmap.createBitmap(leng,leng,Bitmap.Config.ARGB_8888);
        Paint paint=new Paint();
        paint.setAntiAlias(true);
        Canvas canvas=new Canvas(result);
        int l=Math.min(bitmap.getWidth(),bitmap.getHeight());
        canvas.drawBitmap(bitmap,new Rect(0,0,l,l),new Rect(0,0,leng,leng),paint);
        return new BitmapDrawable(result);
    }
}
