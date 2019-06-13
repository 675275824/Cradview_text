package com.example.asuna.cradview_text;

import android.app.Application;

import java.util.ArrayList;
import java.util.HashMap;

public class DemoApplication extends Application {
    private ArrayList<Allact> map= new ArrayList<>();

    private void put(Allact allact){
        map.add(allact);
    }

    private void Delete(Allact key){
        map.remove(key);
    }
}
