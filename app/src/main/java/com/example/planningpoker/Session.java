package com.example.planningpoker;

import java.util.ArrayList;

public class Session {
    private String sessionId;
    private String sessionName;
    private ArrayList<String> questions;

    public Session() {
    }

    public Session(String sessionId, String sessionName, ArrayList<String> questions) {
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.questions = questions;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionName() {
        return sessionName;
    }

    public void setSessionName(String sessionName) {
        this.sessionName = sessionName;
    }

    public ArrayList<String> getQuestions() {
        return questions;
    }

    public void setQuestions(ArrayList<String> questions) {
        this.questions = questions;
    }
}
