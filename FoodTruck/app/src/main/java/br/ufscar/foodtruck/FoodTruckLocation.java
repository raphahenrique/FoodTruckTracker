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

    public FoodTruckLocation(LatLng location, String startDate, String endDate, String )

    void updateEndDate() {
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
}
