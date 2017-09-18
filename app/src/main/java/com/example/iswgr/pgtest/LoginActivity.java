package com.example.iswgr.pgtest;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iswgr.pgtest.bean.UserBean;
import com.example.iswgr.pgtest.gson.AnalysisGson;
import com.example.iswgr.pgtest.http.SendRequest;
import com.example.iswgr.pgtest.utils.MD5Utils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.example.iswgr.pgtest.http.HttpURL.URL_LOGIN;

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
                //获取用户名和密码
                String n = mEditNum.getText().toString().trim();
                String p = mEditPwd.getText().toString().trim();
                if (TextUtils.isEmpty(n) || TextUtils.isEmpty(p)) {
                    Toast.makeText(LoginActivity.this, "请输入用户名和密码", Toast.LENGTH_SHORT).show();
                    return;
                }
                //查询登录
                RequestParams params = new RequestParams();
                params.add("username", n);
                params.add("password", MD5Utils.MD5(p));
                SendRequest.sendRequestForPostBackTest(LoginActivity.this, URL_LOGIN, params, new TextHttpResponseHandler() {
                    @Override
                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(LoginActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, String responseString) {
                        UserBean user = AnalysisGson.analysisLogin(responseString);
                        int result = user.getResult();
                        if (result == 1) {
                            //存在
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            //保存数据
                            saveData(user);
                            finish();
                        } else {
                            //错误
                            Toast.makeText(LoginActivity.this, "用户名或密码错误", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                break;
            case R.id.ac_login_text_register:
                //注册
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 保存用户登录数据
     */
    private void saveData(UserBean bean) {
        SharedPreferences sp = getSharedPreferences("info", MODE_PRIVATE);
        SharedPreferences.Editor edit = sp.edit();
        edit.putString("uuid", bean.getUuid());
        edit.putString("username", bean.getUsername());
        edit.putInt("sex", bean.getSex());
        edit.putString("cla", bean.getCla());
        edit.putString("email", bean.getEmail());
        edit.putString("autograph", bean.getAutograph());
        edit.putInt("emailvalidate", bean.getEmailvalidate());
        edit.putInt("vip", bean.getVip());
        edit.commit();
    }
}
