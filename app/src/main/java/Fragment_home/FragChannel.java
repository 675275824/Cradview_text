package Fragment_home;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.asuna.cradview_text.*;

public class FragChannel extends Fragment {
    @Nullable

    private String data[] = {"aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd","aa","bb","cc","dd"};//假数据
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.channel,container,false);
        ListView listView=view.findViewById(R.id.channel_listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),R.layout.friend_list,data);
        listView.setAdapter(adapter);
        return view;
    }
}
