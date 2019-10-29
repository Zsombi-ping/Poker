package com.example.planningpoker;

import android.util.ArrayMap;

public class QuestionResponses {
    String questionId;
    ArrayMap<String, Integer> responses;

    public QuestionResponses(String questionId, ArrayMap<String, Integer> responses) {
        this.questionId = questionId;
        this.responses = responses;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public ArrayMap<String, Integer> getResponses() {
        return responses;
    }

    public void setResponses(ArrayMap<String, Integer> responses) {
        this.responses = responses;
    }
}
