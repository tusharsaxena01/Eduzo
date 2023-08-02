package com.bitAndroid.eduzo.recyclerview;

public class HistoryData {
    String testName;
    String date;
    int score;
    int totalQues;

    public HistoryData() {
    }

    public HistoryData(String testName, String date, int score, int totalQues) {
        this.testName = testName;
        this.date = date;
        this.score = score;
        this.totalQues = totalQues;
    }

    public HistoryData(String date, int score, int totalQues) {
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

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getTotalQues() {
        return totalQues;
    }

    public void setTotalQues(int totalQues) {
        this.totalQues = totalQues;
    }
}
