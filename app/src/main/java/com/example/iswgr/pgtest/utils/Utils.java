package com.example.iswgr.pgtest.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 基础工具类
 * Created by iswgr on 2017/9/18.
 */

public class Utils {
    /**
     * 匹配邮箱
     *
     * @param email 邮箱
     * @return boolean
     */
    public static boolean checkEmail(String email) {
        String RULE_EMAIL = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$";

        Pattern p = Pattern.compile(RULE_EMAIL);

        Matcher m = p.matcher(email);

        return m.matches();
    }

    /**
     * 匹配用户名
     *
     * @param name 用户名
     * @return boolean
     */
    public static boolean checkName(String name) {
        String RULE_EMAIL = "[a-zA-Z][a-zA-Z0-9]{5,15}";

        Pattern p = Pattern.compile(RULE_EMAIL);

        Matcher m = p.matcher(name);

        return m.matches();
    }
}
