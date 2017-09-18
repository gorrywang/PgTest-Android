package com.example.iswgr.pgtest;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iswgr.pgtest.bean.TestBean;
import com.example.iswgr.pgtest.gson.AnalysisGson;
import com.example.iswgr.pgtest.http.HttpURL;
import com.example.iswgr.pgtest.http.SendRequest;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.msebera.android.httpclient.Header;

import static com.example.iswgr.pgtest.http.HttpURL.URL_RANDOM_TEST;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.ac_test_toolbar_title)
    Toolbar mTitle;
    @BindView(R.id.ac_test_text_q)
    TextView mQ;
    @BindView(R.id.ac_test_text_a)
    TextView mA;
    @BindView(R.id.ac_test_text_b)
    TextView mB;
    @BindView(R.id.ac_test_text_c)
    TextView mC;
    @BindView(R.id.ac_test_text_d)
    TextView mD;
    private int mMethod;
    private List<TestBean> mList;

    //题目
    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
        //title
        setSupportActionBar(mTitle);
        ActionBar supportActionBar = getSupportActionBar();
        supportActionBar.setTitle("测试");
        //获取method
        getMethod();
        //下载题目
        addData();
    }

    /**
     * 获取题目数据
     */
    private void addData() {
        String url = null;
        RequestParams params = new RequestParams();
        if (mMethod == 1) {
            url = HttpURL.URL_GETALL;
        } else if (mMethod == 2) {
            url = URL_RANDOM_TEST;
            params.add("num", "50");
        } else if (mMethod == 3) {
            url = URL_RANDOM_TEST;
            params.add("num", "100");
        }
        SendRequest.sendRequestForPostBackTest(this, url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Toast.makeText(TestActivity.this, "网络错误，请检查", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                //解析数据
                mList = AnalysisGson.analysisTest(responseString);
                if (mList == null) {
                    Toast.makeText(TestActivity.this, "数据发生错误", Toast.LENGTH_LONG).show();
                    return;
                }
//                Toast.makeText(TestActivity.this, "数据" + mList.size(), Toast.LENGTH_LONG).show();
                //加载题目
                addTest();
            }
        });
    }

    //正确答案
    private String answer;
    //错误分析
    private String error;

    /**
     * 加载题目
     */
    private void addTest() {
        if (count == mList.size()) {
            //做完了
            return;
        }
        error = null;
        TestBean bean = mList.get(count);
        //正确答案
        answer = bean.getAnswer();
        //分析
        if (bean.getAnalysis() != null) {
            error = bean.getAnalysis();
        }
        //问题
        mQ.setText("问题: \n" + bean.getQuestion());
        //选项
        mA.setText("A: " + bean.getQa());
        mB.setText("B: " + bean.getQb());
        mC.setText("C: " + bean.getQc());
        mD.setText("D: " + bean.getQd());
        //最后下一个count
        count++;
    }

    /**
     * 获取模式
     */
    private void getMethod() {
        mMethod = getIntent().getIntExtra("method", 0);
    }

    /**
     * 从main进行跳转
     *
     * @param context 上下文
     * @param i       测试类型
     */
    public static void jumpActivity(Context context, int i) {
        Intent intent = new Intent(context, TestActivity.class);
        intent.putExtra("method", i);
        context.startActivity(intent);
    }


    @OnClick({R.id.ac_test_text_a, R.id.ac_test_text_b, R.id.ac_test_text_c, R.id.ac_test_text_d})
    public void onViewClicked(View view) {
        String use = null;
        switch (view.getId()) {
            case R.id.ac_test_text_a:
                use = "A";
                break;
            case R.id.ac_test_text_b:
                use = "B";
                break;
            case R.id.ac_test_text_c:
                use = "C";
                break;
            case R.id.ac_test_text_d:
                use = "D";
                break;
        }
        if (answer.equals(use)) {
            //对了,提示正确
            Toast.makeText(TestActivity.this, "正确", Toast.LENGTH_SHORT).show();
            //进入下一题
            addTest();
        } else {
            //错误,提示并显示错误
            showError();
        }
    }

    /**
     * 显示错误
     */
    private void showError() {
        AlertDialog.Builder builder = new AlertDialog.Builder(TestActivity.this);
        builder.setTitle("正确答案");
        String msg = null;
        if (answer.equals("A")) {
            msg = mA.getText().toString().trim();
        } else if (answer.equals("B")) {
            msg = mB.getText().toString().trim();
        } else if (answer.equals("C")) {
            msg = mC.getText().toString().trim();
        } else if (answer.equals("D")) {
            msg = mD.getText().toString().trim();
        }
        //是否有分析
        if (error != null) {
            msg += "\n分析: " + error;
        }
        builder.setMessage(msg);
        builder.setCancelable(true);
        builder.setPositiveButton("下一题", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                //进入下一题
                addTest();
            }
        });
        builder.show();
    }
}
