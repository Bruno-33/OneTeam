package com.example.administrator.oneteam;

import android.app.Fragment;
import android.media.Image;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.oneteam.Utils.ContactFragment;
import com.example.administrator.oneteam.Utils.FragmentAdapter;
import com.example.administrator.oneteam.Utils.InfoFragment;
import com.example.administrator.oneteam.Utils.SelfTaskFragment;
import com.example.administrator.oneteam.Utils.TabFragment;
import com.example.administrator.oneteam.Utils.TaskPoolFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            R.drawable.task_pool,
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
