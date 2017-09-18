package com.example.iswgr.pgtest.bean;

/**
 * 题目实体类
 * Created by iswgr on 2017/9/18.
 */

public class TestBean {

    /**
     * qa : 职业道德教育
     * qb : 职业道德修养
     * qc : 职业道德准则
     * qd : 职业素质
     * question : 树立对职业道德的认识，培养职业道德情感是【 】的内容。
     * answer : B
     * questionclassify_id : 1
     * id : 1
     * correctrate : 0
     * analysis : 记忆：设计专利权期限10年
     */

    private String qa;
    private String qb;
    private String qc;
    private String qd;
    private String question;
    private String answer;
    private int questionclassify_id;
    private int id;
    private int correctrate;
    private String analysis;

    public String getQa() {
        return qa;
    }

    public void setQa(String qa) {
        this.qa = qa;
    }

    public String getQb() {
        return qb;
    }

    public void setQb(String qb) {
        this.qb = qb;
    }

    public String getQc() {
        return qc;
    }

    public void setQc(String qc) {
        this.qc = qc;
    }

    public String getQd() {
        return qd;
    }

    public void setQd(String qd) {
        this.qd = qd;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getQuestionclassify_id() {
        return questionclassify_id;
    }

    public void setQuestionclassify_id(int questionclassify_id) {
        this.questionclassify_id = questionclassify_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCorrectrate() {
        return correctrate;
    }

    public void setCorrectrate(int correctrate) {
        this.correctrate = correctrate;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }
}
