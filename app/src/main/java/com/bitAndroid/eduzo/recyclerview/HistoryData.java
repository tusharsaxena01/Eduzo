package com.bitAndroid.eduzo.recyclerview;

public class HistoryData {
    String testName;
    String date;
    String score;
    String totalQues;

    public HistoryData() {
    }

    public HistoryData(String testName) {
        this.testName = testName;
    }

    public HistoryData(String date, String score, String totalQues) {
        this.date = date;
        this.score = score;
        this.totalQues = totalQues;
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

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getTotalQues() {
        return totalQues;
    }

    public void setTotalQues(String totalQues) {
        this.totalQues = totalQues;
    }
}
