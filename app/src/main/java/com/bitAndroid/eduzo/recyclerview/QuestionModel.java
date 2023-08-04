package com.bitAndroid.eduzo.recyclerview;

public class QuestionModel {
    public String question;
    public String option1;
    public String option2;
    public String option3;
    public String option4;
    public String answer;

    public QuestionModel() {
        // Empty constructor needed for Firebase
    }

    public QuestionModel(String question, String option1, String option2, String option3, String option4, String answer) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.option4 = option4;
        this.answer = answer;
    }
}