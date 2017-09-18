package com.example.iswgr.pgtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_tool_title)
    Toolbar mTitle;
    @BindView(R.id.ac_login_edit_num)
    EditText mEditNum;
    @BindView(R.id.ac_login_edit_pwd)
    EditText mEditPwd;
    @BindView(R.id.ac_login_btn_login)
    Button mBtnLogin;
    @BindView(R.id.ac_login_text_register)
    TextView mtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ac_login_btn_login, R.id.ac_login_text_register})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ac_login_btn_login:
                //登录
                break;
            case R.id.ac_login_text_register:
                //注册
                break;
        }
    }
}
