package com.example.administrator.oneteam;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.administrator.oneteam.Factory.ServiceFactory;
import com.example.administrator.oneteam.Service.BrunoService;
import com.example.administrator.oneteam.Utils.ContactFragment;
import com.example.administrator.oneteam.Utils.FragmentAdapter;
import com.example.administrator.oneteam.Utils.InfoFragment;
import com.example.administrator.oneteam.Utils.SelfTaskFragment;
import com.example.administrator.oneteam.Utils.TabFragment;
import com.example.administrator.oneteam.Utils.TaskPoolFragment;
import com.example.administrator.oneteam.model.Outcome;
import com.example.administrator.oneteam.model.Person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.example.administrator.oneteam.R.color.colorPrimary;

public class Main extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "Main";

    private String[] titles = new String[]{"微信", "通讯录", "发现", "我"};
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private FragmentAdapter fragmentAdapter;
    private List<android.support.v4.app.Fragment>mFragments;
    private List<String> mTitles;
    private int[] mImgs=new int[]{
            R.drawable.self_task,
            R.drawable.money,
            R.drawable.contact,
            R.drawable.info
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        final ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //region TabLayout

        mViewPager = (ViewPager) findViewById(R.id.viewpager_contain_main);
        mTabLayout = (TabLayout) findViewById(R.id.tablayput_contain_main);


        mFragments = new ArrayList<>();
        mFragments.add(SelfTaskFragment.newInstance());
        mFragments.add(TaskPoolFragment.newInstance());
        mFragments.add(ContactFragment.newInstance());
        mFragments.add(InfoFragment.newInstance());
        fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), mFragments, Arrays.asList(titles));
        mViewPager.setAdapter(fragmentAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                Log.i(TAG, "onPageSelected: " + position);
                if (position == 3){
                    toolbar.setVisibility(View.GONE);
                }
                else{
                    toolbar.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mTabLayout.setupWithViewPager(mViewPager);

        for (int i = 0; i < titles.length; i++){
            TabLayout.Tab itemTab = mTabLayout.getTabAt(i);
            if (itemTab != null){
                itemTab.setCustomView(R.layout.item_tab);
                ImageView imageView = itemTab.getCustomView().findViewById(R.id.icon_item_tab);
                imageView.setImageResource(mImgs[i]);
            }
        }

        mTabLayout.getTabAt(0).getCustomView().setSelected(true);
        //endregion
        toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RecyclerView tmp = mFragments.get(0).getActivity().findViewById(R.id.self_task_recyclerview);
                if(tmp.getAdapter().getItemCount()>0)
                    tmp.scrollToPosition(0);
            }
        });
        final Menu menu1=navigationView.getMenu();
        MenuItem menuItem=menu1.findItem(R.id.nav_email);
        menuItem.setTitle("123");
        menuItem=menu1.findItem(R.id.nav_user);
        menuItem.setTitle("33");
        menuItem=menu1.findItem(R.id.nav_state);
        menuItem.setTitle("队员");
        menuItem=menu1.findItem(R.id.nav_sex);
        menuItem.setTitle("男");
        new AlertDialog.Builder(Main.this)
                .setTitle("通知")
                .setMessage("请完善您的个人信息")
                .setPositiveButton("确定",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplication(),person_detail.class);
                        startActivity(intent);
                    }
                }).show();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.toolbar){
        }
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(getApplication(),"功能尚未实现，敬请期待...",Toast.LENGTH_LONG).show();
            return true;
        }
        if (id == R.id.action_add) {
            Intent intent = new Intent(this,add_task.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_email) {
            Intent intent = new Intent(this,person_detail.class);
            startActivity(intent);
            // Handle the camera action
        } else if (id == R.id.nav_email) {
            Intent intent = new Intent(this,person_detail.class);
            startActivity(intent);

        } else if (id == R.id.nav_user) {
            Intent intent = new Intent(this,person_detail.class);
            startActivity(intent);

        } else if (id == R.id.nav_sex) {
            Intent intent = new Intent(this,person_detail.class);
            startActivity(intent);

        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(this,person_detail.class);
            startActivity(intent);

        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(this,person_detail.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
