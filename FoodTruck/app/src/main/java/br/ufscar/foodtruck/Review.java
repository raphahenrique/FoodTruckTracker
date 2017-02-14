package br.ufscar.foodtruck;

import br.ufscar.auxiliares.EasyDate;

public class Review {

    private float rating;
    private String comment;
    private EasyDate date;
    private String picture;
    private String name;


    public Review(String name, float rating, String comment, String picture) {
        this.name = name;
        this.rating = rating;
        this.comment = comment;
        this.picture = picture;
        this.date = new EasyDate();
    }

    Review(int rating) {
        this.name = "Carlos";
        this.rating = rating;
        this.comment = "Muito bom!! estão de parabéns";
        //this.picture = picture;
        this.date = new EasyDate();
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public float getRating() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



}
