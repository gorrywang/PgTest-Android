package com.example.iswgr.pgtest.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.iswgr.pgtest.LoginActivity;
import com.example.iswgr.pgtest.R;
import com.example.iswgr.pgtest.TestActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 测试题目
 * Created by iswgr on 2017/9/18.
 */

public class TestFragment extends BaseFragment {
    @BindView(R.id.frag_test_btn_1)
    Button mTestBtn1;
    @BindView(R.id.frag_test_btn_2)
    Button mTestBtn2;
    @BindView(R.id.frag_test_btn_3)
    Button mTestBtn3;
    Unbinder unbinder;
    private View mView;
    //是否存在uuid
    private String mUUID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_test, container, false);
        unbinder = ButterKnife.bind(this, mView);
        //获取UUID
        SharedPreferences sp = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        mUUID = sp.getString("uuid", null);
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

    @OnClick({R.id.frag_test_btn_1, R.id.frag_test_btn_2, R.id.frag_test_btn_3})
    public void onViewClicked(View view) {

        if (mUUID == null) {
            //未登录
            Snackbar.make(mTestBtn1, "您暂未登录, 无法测试", Snackbar.LENGTH_LONG).setAction("登录", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getContext(), LoginActivity.class);
                    startActivity(intent);
                }
            }).show();
            return;
        }

        switch (view.getId()) {
            case R.id.frag_test_btn_1:
                //全部测试
                Snackbar.make(mTestBtn1, "做题需要很长时间，准备好了?", Snackbar.LENGTH_LONG).setAction("好", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        TestActivity.jumpActivity(getContext(), 1);
                    }
                }).show();
                break;
            case R.id.frag_test_btn_2:
                //随机50题目
                TestActivity.jumpActivity(getContext(), 2);
                break;
            case R.id.frag_test_btn_3:
                //随机100题目
                TestActivity.jumpActivity(getContext(), 3);
                break;
        }
    }
}
