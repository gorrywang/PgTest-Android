package com.example.iswgr.pgtest.fragment;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.iswgr.pgtest.R;
import com.example.iswgr.pgtest.bean.TestBean;
import com.example.iswgr.pgtest.gson.AnalysisGson;
import com.example.iswgr.pgtest.http.HttpURL;
import com.example.iswgr.pgtest.http.SendRequest;
import com.google.gson.Gson;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

/**
 * 背诵题目
 * Created by iswgr on 2017/9/18.
 */

public class SubjectFragment extends BaseFragment {
    @BindView(R.id.frag_sub_recycler_show)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    private List<TestBean> mList;
    private View mView;
    private MyAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_subject, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void addData() {
        SendRequest.sendRequestForPostBackTest(getContext(), HttpURL.URL_GETALL, null, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getContext(), "网络错误，请检查", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //解析数据
                mList = AnalysisGson.analysisTest(responseString);
                if (mList == null) {
                    Toast.makeText(getContext(), "数据发生错误", Toast.LENGTH_LONG).show();
                    return;
                }
//                Toast.makeText(getContext(), "数据" + mList.size(), Toast.LENGTH_LONG).show();
                //显示数据
                loadData();
            }
        });
    }

    /**
     * 显示数据
     */
    private void loadData() {
        mAdapter = new MyAdapter(R.layout.item_subjects, mList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //惰性加载
    @Override
    protected void lazyLoad() {
        if (mList == null) {
            //加载数据
            addData();
        }
    }

    /**
     * 适配器
     */
    private class MyAdapter extends BaseQuickAdapter<TestBean, MyAdapter.ViewHolder> {


        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<TestBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(ViewHolder helper, TestBean item) {
            helper.q.setText(item.getQuestion());
            String answer = item.getAnswer();
            if (answer.equals("A")) {
                helper.a.setText("答案: " + item.getQa());
            } else if (answer.equals("B")) {
                helper.a.setText("答案: " + item.getQb());
            } else if (answer.equals("C")) {
                helper.a.setText("答案: " + item.getQc());
            } else if (answer.equals("D")) {
                helper.a.setText("答案: " + item.getQd());
            }
        }

        //ViewHolder
        class ViewHolder extends BaseViewHolder {
            TextView q, a;

            public ViewHolder(View view) {
                super(view);
                a = view.findViewById(R.id.item_sub_text_a);
                q = view.findViewById(R.id.item_sub_text_q);
            }
        }
    }
}
