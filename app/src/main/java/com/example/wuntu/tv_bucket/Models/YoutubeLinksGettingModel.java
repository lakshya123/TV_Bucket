package com.example.wuntu.tv_bucket.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Wuntu on 29-07-2017.
 */

public class YoutubeLinksGettingModel
{
    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("results")
    @Expose
    private List<YoutubelinksFinalModel> results = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<YoutubelinksFinalModel> getResults() {
        return results;
    }

    public void setResults(List<YoutubelinksFinalModel> results) {
        this.results = results;
    }
}
