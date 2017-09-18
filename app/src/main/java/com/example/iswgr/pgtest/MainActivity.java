package com.example.iswgr.pgtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iswgr.pgtest.fragment.RankFragment;
import com.example.iswgr.pgtest.fragment.SubjectFragment;
import com.example.iswgr.pgtest.fragment.TestFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements TabLayout.OnTabSelectedListener {

    @BindView(R.id.ac_main_toolbar_title)
    Toolbar mToolBar;
    @BindView(R.id.ac_main_table_tab)
    TabLayout mTabLayout;
    @BindView(R.id.ac_main_pager_show)
    ViewPager mViewPager;
    @BindView(R.id.ac_main_nav_menu)
    NavigationView mNavMenu;
    @BindView(R.id.ac_main_drawer_father)
    DrawerLayout mDrawerFather;

    private List<Fragment> mList = new ArrayList<>();
    private MyAdapter mAdapter;
    private ActionBar mBar;
    private String mUUID;
    private SharedPreferences mSp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //监听
        listener();
        //sp
        mSp = getSharedPreferences("info", MODE_PRIVATE);
        //加载toolbar
        addTitle();
        //加载底部导航
        addTab();
    }

    /**
     * 侧滑中的个人信息
     */
    private void addSlid() {
        //是否登录
        mSp = getSharedPreferences("info", MODE_PRIVATE);
        mUUID = mSp.getString("uuid", null);
        View headerView = mNavMenu.getHeaderView(0);
        TextView username = headerView.findViewById(R.id.nav_text_name);
        TextView email = headerView.findViewById(R.id.nav_text_email);
        ImageView vip = headerView.findViewById(R.id.nav_img_vip);
        if (mUUID == null) {
            email.setText("点击立即登录");
            username.setText("");
            vip.setVisibility(View.VISIBLE);
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    mDrawerFather.closeDrawers();
                }
            });
        } else {
            String u = mSp.getString("username", null);
            String e = mSp.getString("email", null);
            int v = mSp.getInt("vip", 0);
            username.setText(u);
            email.setText(e);
            //是否是会员
            if (v == 1) {
                vip.setVisibility(View.VISIBLE);
            } else {
                vip.setVisibility(View.GONE);
            }
            email.setOnClickListener(null);
        }
    }

    /**
     * 监听
     */
    private void listener() {
        //底部导航栏监听
        mTabLayout.addOnTabSelectedListener(this);
        //侧滑
        mNavMenu.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_main_1_home:
                        //会员信息
                        Toast.makeText(MainActivity.this, "会员可以进行搜题，下个版本见", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_main_2_home:
                        Toast.makeText(MainActivity.this, "暂不支持修改个人资料，下个版本见", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_main_3_home:
                        Toast.makeText(MainActivity.this, "暂不支持查看历史成绩，下个版本见", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_main_4_home:
                        Toast.makeText(MainActivity.this, "暂不支持修改个性装扮，下个版本见", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.menu_main_5_home:
                        //关于我们
                        break;
                    case R.id.menu_main_6_home:
                        //退出账号
                        mSp.edit().clear().commit();
                        Toast.makeText(MainActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                        break;
                }
                mDrawerFather.closeDrawers();
                return true;
            }
        });
        //侧滑个人信息
        mDrawerFather.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                Log.e("tag", 1 + "");
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                Log.e("tag", 2 + "");
                //信息
                addSlid();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.e("tag", 3 + "");
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                Log.e("tag", 4 + "");

            }
        });
    }

    private void addTitle() {
        setSupportActionBar(mToolBar);
        mBar = getSupportActionBar();
        mBar.setTitle("背诵");
        mBar.setDisplayHomeAsUpEnabled(true);
        mBar.setHomeAsUpIndicator(R.drawable.other);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //打开
                mDrawerFather.openDrawer(Gravity.START);
                break;
        }
        return true;
    }

    /**
     * 底部导航栏
     */
    private void addTab() {
        //加载数据
        Fragment f1 = new SubjectFragment();
        Fragment f2 = new TestFragment();
        Fragment f3 = new RankFragment();
        mList.add(f1);
        mList.add(f2);
        mList.add(f3);
        //初始化适配器
        mAdapter = new MyAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mAdapter);
        mTabLayout.setupWithViewPager(mViewPager);
        //初始化图标
        resetIcon();
        mTabLayout.getTabAt(0).setIcon(R.drawable.dynamic_fill);
    }

    /**
     * 设置底部icon
     */
    private void resetIcon() {
        mTabLayout.getTabAt(0).setIcon(R.drawable.dynamic);
        mTabLayout.getTabAt(1).setIcon(R.drawable.brush);
        mTabLayout.getTabAt(2).setIcon(R.drawable.select);
    }

    //TabLayout监听事件
    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        //还原icon
        resetIcon();
        //设置icon
        int position = tab.getPosition();
        if (position == 0) {
            tab.setIcon(R.drawable.dynamic_fill);
            mBar.setTitle("背诵");
        } else if (position == 1) {
            tab.setIcon(R.drawable.brush_fill);
            mBar.setTitle("测试");
        } else if (position == 2) {
            tab.setIcon(R.drawable.select_fill);
            mBar.setTitle("排行");
        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

    /**
     * 滑动适配器
     */
    private class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mList.get(position);
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

        }
    }
}
