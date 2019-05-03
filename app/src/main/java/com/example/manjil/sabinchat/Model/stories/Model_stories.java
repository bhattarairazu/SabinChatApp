package com.example.manjil.sabinchat.Model.stories;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Model_stories {
    @SerializedName("results")
    @Expose
    private List<Results_stories> results;

    public Model_stories(List<Results_stories> results) {
        this.results = results;
    }

    public List<Results_stories> getResult() {
        return results;
    }

    public void setResult(List<Results_stories> results) {
        this.results = results;
    }
}
