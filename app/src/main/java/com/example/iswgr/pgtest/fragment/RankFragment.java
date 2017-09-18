package com.example.iswgr.pgtest.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.iswgr.pgtest.R;
import com.example.iswgr.pgtest.bean.RankBean;
import com.example.iswgr.pgtest.gson.AnalysisGson;
import com.example.iswgr.pgtest.http.HttpURL;
import com.example.iswgr.pgtest.http.SendRequest;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cz.msebera.android.httpclient.Header;

/**
 * 排行榜
 * Created by iswgr on 2017/9/18.
 */

public class RankFragment extends BaseFragment {

    @BindView(R.id.frag_rank_recycler_show)
    RecyclerView mRecyclerView;
    Unbinder unbinder;

    private View mView;
    private List<RankBean> mList;
    private MyAdapter mAdapter;
    private String mUUID;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_rank, container, false);
        unbinder = ButterKnife.bind(this, mView);
        //获取UUID
        SharedPreferences sp = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
        mUUID = sp.getString("uuid", null);
        return mView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * 加载排行数据
     */
    private void addData() {
        RequestParams params = new RequestParams();
        params.add("method", "1");
        SendRequest.sendRequestForPostBackTest(getContext(), HttpURL.URL_RANK, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(getContext(), "错误", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
//                Toast.makeText(getContext(), responseString, Toast.LENGTH_LONG).show();
                mList = AnalysisGson.analysisRank(responseString);
                //显示数据
                showData();
            }
        });
    }

    /**
     * 显示数据
     */
    private void showData() {
        mAdapter = new MyAdapter(R.layout.item_rank, mList);
        mAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //头部布局
        //设置名称
        View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_rank_header, null);
        if (mUUID == null) {
            ((TextView) itemView.findViewById(R.id.frag_rank_text_name)).setText("未登录");
        } else {
            SharedPreferences sp = getActivity().getSharedPreferences("info", Context.MODE_PRIVATE);
            String username = sp.getString("username", null);
            ((TextView) itemView.findViewById(R.id.frag_rank_text_name)).setText(username + "");
        }
        mAdapter.addHeaderView(itemView);
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

    @Override
    protected void lazyLoad() {
        if (mList != null)
            return;
        //加载数据
        addData();
    }


    /**
     * 适配器
     */
    private class MyAdapter extends BaseQuickAdapter<RankBean, MyAdapter.ViewHolder> {


        public MyAdapter(@LayoutRes int layoutResId, @Nullable List<RankBean> data) {
            super(layoutResId, data);
        }

        @Override
        protected void convert(MyAdapter.ViewHolder helper, RankBean item) {
            int adapterPosition = helper.getAdapterPosition() - 1;
            helper.num.setText(adapterPosition + "");
            helper.img.setImageDrawable(getResources().getDrawable(R.drawable.userimage));
            helper.username.setText(mList.get(adapterPosition).getUsername() + "");
            helper.result.setText(mList.get(adapterPosition).getFraction() + "");

        }

        //ViewHolder
        class ViewHolder extends BaseViewHolder {
            TextView num, username, result;
            ImageView img;

            public ViewHolder(View view) {
                super(view);
                num = view.findViewById(R.id.item_rank_text_num);
                username = view.findViewById(R.id.item_rank_text_name);
                result = view.findViewById(R.id.item_rank_text_result);
                img = view.findViewById(R.id.item_rank_img_user);
            }
        }
    }
}
