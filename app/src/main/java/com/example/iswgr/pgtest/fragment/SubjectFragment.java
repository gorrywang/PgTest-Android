package com.example.iswgr.pgtest.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iswgr.pgtest.R;

/**
 * 背诵题目
 * Created by iswgr on 2017/9/18.
 */

public class SubjectFragment extends Fragment {
    private View mView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_subject, container, false);
        return mView;
    }
}
