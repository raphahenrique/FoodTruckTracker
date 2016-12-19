package br.ufscar.dc.foodtrucktracker;

import java.util.Date;

public class Review {
    private int rating;
    private String comment;
    private Date date;
    private String picture;

    Review(int rating, String comment, String picture) {
        this.rating = rating;
        this.comment = comment;
        this.picture = picture;
        //this.date = current date
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
