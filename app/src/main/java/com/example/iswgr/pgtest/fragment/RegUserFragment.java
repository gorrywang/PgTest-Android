package com.example.iswgr.pgtest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iswgr.pgtest.R;

/**
 * 信息
 * Created by iswgr on 2017/9/19.
 */

public class RegUserFragment extends BaseFragment {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_reg_user, container, false);
        return mView;
    }

    @Override
    protected void lazyLoad() {

    }
}
