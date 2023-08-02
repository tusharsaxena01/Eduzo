package com.bitAndroid.eduzo.recyclerview;

public class ResultData {

    String testName;
    String date;
    int score;
    int totalQues;


    public ResultData() {
    }

    public ResultData(String date, int totalQues, int score) {
        this.date = date;
        this.totalQues = totalQues;
        this.score = score;
    }

    public String getTestName() {
        return testName;
    }

    public void setTestName(String testName) {
        this.testName = testName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getTotalQues() {
        return totalQues;
    }

    public void setTotalQues(int totalQues) {
        this.totalQues = totalQues;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
