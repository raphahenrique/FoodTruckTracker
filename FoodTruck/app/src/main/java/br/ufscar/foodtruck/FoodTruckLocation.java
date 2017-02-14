package br.ufscar.foodtruck;

import com.google.android.gms.maps.model.LatLng;

import br.ufscar.auxiliares.EasyDate;
import br.ufscar.auxiliares.EasyTime;

public class FoodTruckLocation {

    private LatLng location;
    private EasyDate startDate;
    private EasyDate endDate;
    private EasyTime opensAt;
    private EasyTime closesAt;
    private int score;

    public FoodTruckLocation(LatLng location, EasyDate startDate, EasyDate endDate, EasyTime opensAt, EasyTime closesAt) {
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.opensAt = opensAt;
        this.closesAt = closesAt;
        score = 0;
    }

    public FoodTruckLocation(LatLng location) {
        this.location = location;
        startDate = new EasyDate();
        endDate = new EasyDate();
        endDate.increaseDate(7);
        opensAt = new EasyTime("00:00:00");
        closesAt = new EasyTime("23:59:59");
        score = 0;
    }

    void updateEndDate() {
        endDate = new EasyDate();
        endDate.increaseDate(7);
    }

    public void upvote() {
        score += 1;
        updateEndDate();
    }

    public void downvote() {
        score -= 1;
    }

    // Getters
    public LatLng getLocation() {
        return location;
    }

    public EasyDate getStartDate() {
        return startDate;
    }

    public EasyDate getEndDate() {
        return endDate;
    }

    public EasyTime getOpensAt() {
        return opensAt;
    }

    public EasyTime getClosesAt() {
        return closesAt;
    }

    public int getScore() { return score; }


}
