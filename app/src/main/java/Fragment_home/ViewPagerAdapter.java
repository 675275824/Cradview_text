package Fragment_home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    private List<View> views;
    private List<String> strings;

    ViewPagerAdapter(List<View> views,List<String> strings) {
        this.views=views;
        this.strings=strings;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return strings.get(position);
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView(views.get(position));
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        container.addView(views.get(position));
        return views.get(position);
    }

    @Override
    public int getCount() {
        return views.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view==o;
    }
}
