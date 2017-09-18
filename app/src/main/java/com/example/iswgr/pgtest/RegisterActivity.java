package com.example.iswgr.pgtest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iswgr.pgtest.bean.RegisterBean;
import com.example.iswgr.pgtest.gson.AnalysisGson;
import com.example.iswgr.pgtest.http.SendRequest;
import com.example.iswgr.pgtest.utils.MD5Utils;
import com.example.iswgr.pgtest.utils.Utils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.example.iswgr.pgtest.http.HttpURL.URL_REGISTER;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.ac_reg_bar_title)
    Toolbar mTitle;
    @BindView(R.id.ac_reg_edit_email)
    EditText mEditEmail;
    @BindView(R.id.ac_reg_edit_username)
    EditText mEditUsername;
    @BindView(R.id.ac_reg_edit_pwd)
    EditText mEditPwd;
    @BindView(R.id.ac_reg_edit_cla)
    EditText mEditCla;
    @BindView(R.id.ac_reg_btn_register)
    Button mBtnRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.ac_reg_btn_register)
    public void onViewClicked() {
        //获取数据
        String email = mEditEmail.getText().toString().trim();
        String username = mEditUsername.getText().toString().trim();
        String password = mEditPwd.getText().toString().trim();
        String cla = mEditCla.getText().toString().trim();
        //检查是否存在
        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(cla)) {
            Toast.makeText(RegisterActivity.this, "请填写完整", Toast.LENGTH_SHORT).show();
            return;
        }
        //检查邮箱格式
        boolean b = Utils.checkEmail(email);
        if (!b) {
            Toast.makeText(RegisterActivity.this, "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
            return;
        }
        //检查用户名长度
        if (username.length() < 6) {
            Toast.makeText(RegisterActivity.this, "用户名最少6位字符", Toast.LENGTH_SHORT).show();
            return;
        }
        //检查密码长度
        if (password.length() < 6) {
            Toast.makeText(RegisterActivity.this, "密码最少6位字符", Toast.LENGTH_SHORT).show();
            return;
        }
        //发送请求
        RequestParams params = new RequestParams();
        params.add("username", username);
        params.add("password", MD5Utils.MD5(password));
        params.add("sex", "1");
        params.add("email", email);
        params.add("emailvalidate", "0");
        params.add("cla", cla);
        SendRequest.sendRequestForPostBackTest(RegisterActivity.this, URL_REGISTER, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(RegisterActivity.this, "请检查网络", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //解析数据
                RegisterBean bean = AnalysisGson.analysisRegister(responseString);
                int result = Integer.parseInt(bean.getResult());
                if (result == 1) {
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                    finish();
                } else if (result == 0) {
                    Toast.makeText(RegisterActivity.this, "发生错误，请重新尝试", Toast.LENGTH_SHORT).show();
                } else if (result == -1) {
                    Toast.makeText(RegisterActivity.this, "用户名已存在, 请更换", Toast.LENGTH_SHORT).show();
                } else if (result == -2) {
                    Toast.makeText(RegisterActivity.this, "邮箱已存在, 请更换", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
