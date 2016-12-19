package br.ufscar.dc.foodtrucktracker;

import com.google.android.gms.maps.model.LatLng;

import java.sql.Time;
import java.util.Date;

public class FoodTruckLocation {
    private Date startDate;
    private Date endDate;
    private LatLng location;
    private Time opensAt;
    private Time closesAt;

    void updateEndDate() {
    }
}
