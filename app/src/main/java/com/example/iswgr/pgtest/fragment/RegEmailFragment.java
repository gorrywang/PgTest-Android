package com.example.iswgr.pgtest.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.iswgr.pgtest.R;
import com.example.iswgr.pgtest.RegisterActivity;
import com.example.iswgr.pgtest.app.CustomApp;
import com.example.iswgr.pgtest.http.SendRequest;
import com.example.iswgr.pgtest.utils.Utils;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

import static com.example.iswgr.pgtest.http.HttpURL.URL_EMAIL;
import static com.example.iswgr.pgtest.http.HttpURL.URL_SENDEMAIL;

/**
 * 电子邮件
 * Created by iswgr on 2017/9/19.
 */

public class RegEmailFragment extends BaseFragment {
    @BindView(R.id.frag_email_edit_email)
    EditText mEditEmail;
    @BindView(R.id.frag_email_edit_code)
    EditText mEditCode;
    @BindView(R.id.frag_email_edit_father)
    TextInputLayout mEditFather;
    @BindView(R.id.frag_email_btn_ok)
    Button mBtnOk;
    Unbinder unbinder;
    @BindView(R.id.frag_email_edit_father1)
    TextInputLayout mEditFather1;
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reg_email, container, false);
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

    //按钮切换
    private boolean mBtnEmailBool = false;

    @OnClick(R.id.frag_email_btn_ok)
    public void onViewClicked() {
        if (mBtnEmailBool) {
            //邮箱可以使用,验证码匹配
            String code = mEditCode.getText().toString().trim();
            if ((mCode + "").equals(code)) {
                //匹配
                Toast.makeText(CustomApp.getCtx(), "验证成功", Toast.LENGTH_SHORT).show();
                //进入userfragment
                SharedPreferences sp = CustomApp.getCtx().getSharedPreferences("info", Context.MODE_PRIVATE);
                sp.edit().putString("email", mEditEmail.getText().toString().trim()).commit();
                RegisterActivity.loadUserPage();

            } else {
                //不匹配
                Toast.makeText(CustomApp.getCtx(), "验证码错误, 请重新输入", Toast.LENGTH_SHORT).show();
            }

        } else {
            //验证邮箱, 获取数据， 验证格式 ,判断是否被注册
            String email = mEditEmail.getText().toString().trim();
            boolean b = Utils.checkEmail(email);
            if (!b) {
                Toast.makeText(CustomApp.getCtx(), "请输入正确的邮箱", Toast.LENGTH_SHORT).show();
                return;
            }
            //发送请求
            sendValiEmail(email);
            mBtnOk.setEnabled(false);
        }
    }

    /**
     * 验证邮箱是否已经注册
     *
     * @param email 邮箱
     */
    private void sendValiEmail(final String email) {
        RequestParams params = new RequestParams();
        params.add("email", email);
        SendRequest.sendRequestForPostBackTest(getContext(), URL_EMAIL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(CustomApp.getCtx(), "请检查网络", Toast.LENGTH_SHORT).show();
                mBtnOk.setEnabled(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                try {
                    JSONObject jsonObject = new JSONObject(responseString);
                    String result = jsonObject.getString("result");
                    if (result.equals("1")) {
                        //发送验证邮件
                        sendEmail(email);
                    } else {
                        Toast.makeText(CustomApp.getCtx(), "邮箱已存在, 请更换", Toast.LENGTH_SHORT).show();
                        mEditEmail.setText("");
                        mBtnOk.setEnabled(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        });
    }

    private int mCode;

    /**
     * 发送验证邮件
     *
     * @param email 邮件
     */
    private void sendEmail(String email) {
        RequestParams params = new RequestParams();
        params.add("from", "易晨刷题助手 <register@pg.abug.xyz>");
        params.add("to", email);
        params.add("subject", "验证码");
        mCode = (int) (Math.random() * 9000 + 1000);
        Log.e("a",mCode+"");
        params.add("text", "尊敬的用户, 您的验证码：" + mCode);

        SendRequest.sendRequestForPostBackTest(getContext(), URL_SENDEMAIL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(CustomApp.getCtx(), "网络错误", Toast.LENGTH_SHORT).show();
                mBtnOk.setEnabled(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                Toast.makeText(CustomApp.getCtx(), "验证码发送成功", Toast.LENGTH_LONG).show();
                mBtnEmailBool = true;
                mEditFather.setVisibility(View.VISIBLE);
                mEditFather1.setVisibility(View.GONE);
                mBtnOk.setText("验证");
                mBtnOk.setEnabled(true);
            }
        });
    }
}
