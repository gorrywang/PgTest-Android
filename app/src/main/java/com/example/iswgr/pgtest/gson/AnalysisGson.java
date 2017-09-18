package com.example.iswgr.pgtest.gson;

import com.example.iswgr.pgtest.bean.RankBean;
import com.example.iswgr.pgtest.bean.RegisterBean;
import com.example.iswgr.pgtest.bean.TestBean;
import com.example.iswgr.pgtest.bean.UserBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

/**
 * 解析Gson
 * Created by iswgr on 2017/9/18.
 */

public class AnalysisGson {
    /**
     * 解析试题
     *
     * @param data 试题
     * @return 返回list
     */
    public static List<TestBean> analysisTest(String data) {
        //Json的解析类对象
        JsonParser jsonParser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray asJsonArray = jsonParser.parse(data).getAsJsonArray();
        Gson gson = new Gson();
        List<TestBean> list = new ArrayList<>();
        //遍历
        for (JsonElement jsonElement : asJsonArray) {
            list.add(gson.fromJson(jsonElement, TestBean.class));
        }
        return list;
    }


    /**
     * 解析排行榜
     *
     * @param data 排行榜
     * @return 返回list
     */
    public static List<RankBean> analysisRank(String data) {
        //Json的解析类对象
        JsonParser jsonParser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray asJsonArray = jsonParser.parse(data).getAsJsonArray();
        Gson gson = new Gson();
        List<RankBean> list = new ArrayList<>();
        //遍历
        for (JsonElement jsonElement : asJsonArray) {
            list.add(gson.fromJson(jsonElement, RankBean.class));
        }
        return list;
    }

    /**
     * 解析登录
     *
     * @param data 登录数据
     * @return 返回用户实体类
     */
    public static UserBean analysisLogin(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, UserBean.class);
    }

    /**
     * 解析注册
     *
     * @param data 登录数据
     * @return 返回用户实体类
     */
    public static RegisterBean analysisRegister(String data) {
        Gson gson = new Gson();
        return gson.fromJson(data, RegisterBean.class);
    }

}
