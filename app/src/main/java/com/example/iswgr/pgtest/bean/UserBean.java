package com.example.iswgr.pgtest.bean;

/**
 * 用户实体类
 * Created by iswgr on 2017/9/18.
 */

public class UserBean {
    /**
     * result : 1
     * uuid : 1543344BD7EA407DBD7D19A91EAB51A4
     * username : admin
     * sex : 1
     * cla : 联想
     * email : 1152041176@qq.com
     * emailvalidate : 1
     * vip : 1
     * autograph :
     */

    private int result;
    private String uuid;
    private String username;
    private int sex;
    private String cla;
    private String email;
    private int emailvalidate;
    private int vip;
    private String autograph;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getCla() {
        return cla;
    }

    public void setCla(String cla) {
        this.cla = cla;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getEmailvalidate() {
        return emailvalidate;
    }

    public void setEmailvalidate(int emailvalidate) {
        this.emailvalidate = emailvalidate;
    }

    public int getVip() {
        return vip;
    }

    public void setVip(int vip) {
        this.vip = vip;
    }

    public String getAutograph() {
        return autograph;
    }

    public void setAutograph(String autograph) {
        this.autograph = autograph;
    }
}
