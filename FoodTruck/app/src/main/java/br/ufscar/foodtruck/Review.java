package br.ufscar.foodtruck;

import br.ufscar.auxiliares.EasyDate;

public class Review {
    private int rating;
    private String comment;
    private EasyDate date;
    private String picture;

    Review(int rating, String comment, String picture) {
        this.rating = rating;
        this.comment = comment;
        this.picture = picture;
        this.date = new EasyDate();
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

    public EasyDate getDate() {
        return date;
    }

    public void setDate(EasyDate date) {
        this.date = date;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }
}
