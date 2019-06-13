package com.example.asuna.cradview_text;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.hardware.ConsumerIrManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ThemedSpinnerAdapter;

import Fragment_home.FragChannel;
import Fragment_home.FragHome;
import Fragment_home.FragTrend;
import Fragment_home.Fragshopping;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private BottomNavigationView bottomNavigationView;
    private CircleImageView nav_header_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Intent intent=getIntent();
        String username = intent.getStringExtra("usernames");

        toolbar=(Toolbar)findViewById(R.id.toolbar);
        navigationView=(NavigationView)findViewById(R.id.navigation);
        drawerLayout=(DrawerLayout)findViewById(R.id.main_drawer);
        bottomNavigationView=(BottomNavigationView)findViewById(R.id.main_bottomnav);
        nav_header_image=(CircleImageView)findViewById(R.id.nav_header_image);

        initToolbar();
        initnav();
        initBottomNav();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.menu_history:
                        nav_header_image.setImageResource(R.drawable.asuna);
                        break;
                    case R.id.exit:{
                        Intent intent=new Intent(MainActivity.this,Login.class);
                        startActivity(intent);
                    }break;
                        default:break;
                }
                return true;
            }
        });
    }

    void initToolbar(){
        setSupportActionBar(toolbar);
        /*getSupportActionBar().setDisplayUseLogoEnabled(false);//显示导航图标*/
        getSupportActionBar().setDisplayShowTitleEnabled(false);//隐藏标题
        toolbar.setNavigationIcon(new ImageHelper(getResources().getDrawable(R.drawable.asuna)).getCircle(180));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    void initnav(){
        navigationView.setCheckedItem(R.id.menu_nav_home);
        ColorStateList colorStateList=getResources().getColorStateList(R.color.color_nav_menu);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                drawerLayout.closeDrawers();
                return true;
            }
        });
    }

    void initBottomNav(){
        ColorStateList colorStateList=getResources().getColorStateList(R.color.color_nav_menu);
        bottomNavigationView.setItemIconTintList(colorStateList);
        bottomNavigationView.setItemTextColor(colorStateList);
        final FragHome fragHome=new FragHome();
        final FragChannel fragChannel=new FragChannel();
        final Fragshopping fragshopping=new Fragshopping();
        final FragTrend fragTrend=new FragTrend();
        getSupportFragmentManager().beginTransaction().add(R.id.main_content,fragHome).commit();
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentTransaction transaction=getSupportFragmentManager().beginTransaction();
                switch (menuItem.getItemId()){
                    case R.id.menu_bottomnav_home:
                        transaction.replace(R.id.main_content,fragHome).commit();
                        break;

                    case R.id.menu_bottomnav_trend:
                        transaction.replace(R.id.main_content,fragTrend).commit();
                        break;

                        default:break;
                }
                return true;
            }
        });
    }

}
