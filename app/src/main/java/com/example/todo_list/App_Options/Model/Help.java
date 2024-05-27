/**
 * The Help class represents a help item with a question and an answer.
 */
package com.example.todo_list.App_Options.Model;

public class Help {
    private String question;
    private String answer;

    /**
     * Constructs a new Help object with no arguments.
     */
    public Help() {
    }

    /**
     * Constructs a new Help object with the specified question and answer.
     *
     * @param question The question for which help is provided.
     * @param answer   The answer to the question.
     */
    public Help(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    /**
     * Gets the question.
     *
     * @return The question.
     */
    public String getQuestion() {
        return question;
    }

    /**
     * Sets the question.
     *
     * @param question The question to set.
     */
    public void setQuestion(String question) {
        this.question = question;
    }

    /**
     * Gets the answer.
     *
     * @return The answer.
     */
    public String getAnswer() {
        return answer;
    }

    /**
     * Sets the answer.
     *
     * @param answer The answer to set.
     */
    public void setAnswer(String answer) {
        this.answer = answer;
    }
}
