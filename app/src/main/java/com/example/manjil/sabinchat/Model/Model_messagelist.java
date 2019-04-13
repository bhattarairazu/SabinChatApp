package com.example.manjil.sabinchat.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_messagelist {
    @SerializedName("results")
    @Expose
    private List<Results> results;

    public Model_messagelist(List<Results> results) {
        this.results = results;
    }

    public List<Results> getResults() {
        return results;
    }

    public void setResults(List<Results> results) {
        this.results = results;
    }
}
