package com.example.todo_list.App_Options.Model;

import android.content.Context;

import androidx.fragment.app.FragmentManager;

import com.example.todo_list.Reminder.Task;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class Help {
    private String question,answer;
    public Help(String question,String answer) {
        this.question = question;
        this.answer = answer;

    }

    public String getQuestion(){
        return question;
    }

    public String getAnswer(){
        return answer;
    }
}