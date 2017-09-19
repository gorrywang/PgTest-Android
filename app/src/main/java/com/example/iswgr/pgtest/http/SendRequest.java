package com.example.iswgr.pgtest.http;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

/**
 * 联网发送请求类
 * Created by iswgr on 2017/9/18.
 */

public class SendRequest {
    /**
     * POST请求返回文本数据
     *
     * @param context 上下文
     * @param url     链接
     * @param params  参数
     * @param handler 回调接口
     */
    public static void sendRequestForPostBackTest(Context context, String url, RequestParams params, TextHttpResponseHandler handler) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setBasicAuth("api","key-e57dc67716cd04d846b7589cdd8ebcfe");
        client.post(context, url, params, handler);
    }

}
