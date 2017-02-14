package br.ufscar.foodtruck;

import android.os.Parcel;
import android.os.Parcelable;

import br.ufscar.auxiliares.EasyDate;

public class Review implements Parcelable {

    private int rating;
    private String comment;
    private EasyDate date;
    private String picture;
    private String name;


    Review(String name, int rating, String comment, String picture) {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {

        dest.writeInt(rating);
        dest.writeString(comment);
        dest.writeString(picture);
        dest.writeString(name);
    }

    public Review(Parcel in) {
        rating = in.readInt();
        comment = in.readString();
        picture = in.readString();
        name = in.readString();


    }

    public static final Parcelable.Creator<Review> CREATOR = new Parcelable.Creator<Review>() {
        public Review createFromParcel(Parcel in) {
            return new Review(in);
        }

        public Review[] newArray(int size) {
            return new Review[size];
        }
    };

}
