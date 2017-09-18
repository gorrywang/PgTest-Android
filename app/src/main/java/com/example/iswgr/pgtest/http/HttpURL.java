package com.example.iswgr.pgtest.http;

/**
 * Created by iswgr on 2017/9/18.
 * 链接
 */

public class HttpURL {
    //主机 地址
    public static final String URL_HOST = "http://pg.eachwang.com";
    //获取所有题目
    public static final String URL_GETALL = URL_HOST + "/getAll";
    //获取排行榜
    public static final String URL_RANK = URL_HOST + "/getRanking";
    //获取随机题目
    public static final String URL_RANDOM_TEST = URL_HOST + "/getRandomTest";
    //登录
    public static final String URL_LOGIN = URL_HOST + "/login";
    //注册
    public static final String URL_REGISTER = URL_HOST + "/register";
    //提交成绩
    public static final String URL_RESULT = URL_HOST + "/saveResult";
}
