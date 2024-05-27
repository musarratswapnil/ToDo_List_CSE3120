package com.example.todo_list.App_Options.Model;

public class Help {
    private String question;
    private String answer;

    // No-argument constructor
    public Help() {
    }

    // Parameterized constructor
    public Help(String question, String answer) {
        this.question = question;
        this.answer = answer;
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
}
