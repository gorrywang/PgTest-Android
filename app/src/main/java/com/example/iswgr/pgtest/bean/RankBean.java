package com.example.iswgr.pgtest.bean;

/**
 * 排行榜实体类
 * Created by iswgr on 2017/9/18.
 */

public class RankBean {
    /**
     * classify_id : 1
     * createdate : Sep 10, 2017 2:13:44 PM
     * id : 22
     * uuid : 1543344BD7EA407DBD7D19A91EAB51A4
     * fraction : 100
     * username : admin
     */

    private int classify_id;
    private String createdate;
    private int id;
    private String uuid;
    private int fraction;
    private String username;

    public int getClassify_id() {
        return classify_id;
    }

    public void setClassify_id(int classify_id) {
        this.classify_id = classify_id;
    }

    public String getCreatedate() {
        return createdate;
    }

    public void setCreatedate(String createdate) {
        this.createdate = createdate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public int getFraction() {
        return fraction;
    }

    public void setFraction(int fraction) {
        this.fraction = fraction;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
