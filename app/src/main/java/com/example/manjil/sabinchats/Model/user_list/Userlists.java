package com.example.manjil.sabinchats.Model.user_list;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Userlists {
    @SerializedName("results")
    @Expose
    private List<Resultss> results;

    public Userlists(List<Resultss> results) {
        this.results = results;
    }

    public List<Resultss> getResults() {
        return results;
    }

    public void setResults(List<Resultss> results) {
        this.results = results;
    }
}
