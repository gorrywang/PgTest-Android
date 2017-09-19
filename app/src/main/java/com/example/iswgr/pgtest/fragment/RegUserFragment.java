package com.example.iswgr.pgtest.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iswgr.pgtest.R;
import com.example.iswgr.pgtest.app.CustomApp;
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
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

import static com.example.iswgr.pgtest.http.HttpURL.URL_REGISTER;

/**
 * 信息
 * Created by iswgr on 2017/9/19.
 */

public class RegUserFragment extends BaseFragment {
    @BindView(R.id.frag_user_edit_username)
    EditText mEditUsername;
    @BindView(R.id.frag_user_edit_pwd)
    EditText mEditPwd;
    @BindView(R.id.frag_user_btn_ok)
    Button mBtnOk;
    Unbinder unbinder;
    @BindView(R.id.frag_user_edit_cla)
    EditText mEditCla;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reg_user, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    protected void lazyLoad() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.frag_user_btn_ok)
    public void onViewClicked() {
        //获取数据
        String user = mEditUsername.getText().toString().trim();
        String pwd = mEditPwd.getText().toString().trim();
        String cla = mEditCla.getText().toString().trim();

        if (TextUtils.isEmpty(user) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(cla)) {
            Toast.makeText(CustomApp.getCtx(), "请输入正确的数据", Toast.LENGTH_LONG).show();
            return;
        }
        //检查格式
        if (!Utils.checkName(user)) {
            Toast.makeText(CustomApp.getCtx(), "用户名需要字母开头的6位字符", Toast.LENGTH_LONG).show();
            return;
        }
        //发送请求
        SharedPreferences sp = CustomApp.getCtx().getSharedPreferences("info", Context.MODE_PRIVATE);
        String email = sp.getString("email", null);
        sendReg(email, user, pwd, cla);
    }

    /**
     * 注册
     *
     * @param cla   班级
     * @param email 邮箱
     * @param user  用户名
     * @param pwd   密码
     */
    private void sendReg(String email, String user, String pwd, String cla) {
        RequestParams requestParams = new RequestParams();
        requestParams.add("username", user);
        requestParams.add("password", MD5Utils.MD5(pwd));
        requestParams.add("sex", "1");
        requestParams.add("email", email);
        requestParams.add("emailvalidate", "1");
        requestParams.add("cla", cla);
        SendRequest.sendRequestForPostBackTest(CustomApp.getCtx(), URL_REGISTER, requestParams, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(CustomApp.getCtx(), "请检查网络", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                RegisterBean bean = AnalysisGson.analysisRegister(responseString);
                String result = bean.getResult();
                if (result.equals("1")) {
                    Toast.makeText(CustomApp.getCtx(), "注册成功", Toast.LENGTH_LONG).show();
                    getActivity().finish();
                } else if (result.equals("0")) {
                    Toast.makeText(CustomApp.getCtx(), "发生错误, 请重试", Toast.LENGTH_LONG).show();
                } else if (result.equals("-1")) {
                    Toast.makeText(CustomApp.getCtx(), "用户名已存在", Toast.LENGTH_LONG).show();
                } else if (result.equals("-2")) {
                    Toast.makeText(CustomApp.getCtx(), "邮箱已存在", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
