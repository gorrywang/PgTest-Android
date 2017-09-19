package com.example.iswgr.pgtest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.iswgr.pgtest.fragment.RegEmailFragment;
import com.example.iswgr.pgtest.fragment.RegUserFragment;

public class RegisterActivity extends AppCompatActivity {

    private Fragment mFragEmail;
    private static Fragment mFragUser;
    private static FragmentManager mManager;
    private static FragmentTransaction mTran;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mManager = getSupportFragmentManager();
        //加载验证邮箱页面
        loadEmailPage();
    }

    /**
     * 加载email
     */
    private void loadEmailPage() {
        if (mFragEmail == null) {
            mFragEmail = new RegEmailFragment();
        }
        mTran = mManager.beginTransaction();
        mTran.replace(R.id.ac_reg_frame_show, mFragEmail);
        mTran.commit();
    }

    /**
     * 加载user
     */
    public static void loadUserPage() {
        if (mFragUser == null) {
            mFragUser = new RegUserFragment();
        }
        mTran = mManager.beginTransaction();
        mTran.replace(R.id.ac_reg_frame_show, mFragUser);
        mTran.commit();
    }

}
