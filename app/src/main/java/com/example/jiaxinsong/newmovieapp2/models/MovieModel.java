package com.example.jiaxinsong.newmovieapp2.models;

import java.net.URL;

/**
 * Created by Jiaxin Song on 2018-02-26.
 */

public class MovieModel {
        private String title;
        private int vote_count;
        private double vote_average;
        private double popularity;
        private String original_language;
        private String overview;
        private String release_date;
        private String poster_path;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getVote_count() {
        return vote_count;
    }

    public void setVote_count(int vote_count) {
        this.vote_count = vote_count;
    }

    public double getVote_average() {
        return vote_average;
    }

    public void setVote_average(double vote_average) {
        this.vote_average = vote_average;
    }

    public double getPopular() {
        return popularity;
    }

    public void setPopular(double popular) {
        this.popularity = popular;
    }

    public String getOriginal_laguage() {
        return original_language;
    }

    public void setOriginal_laguage(String original_laguage) {
        this.original_language = original_laguage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getImage() {
        return poster_path;
    }

    public void setImage(String poster_path) {
        this.poster_path = poster_path;
    }
}
