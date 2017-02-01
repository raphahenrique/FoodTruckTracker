package br.ufscar.foodtruck;


import com.google.android.gms.maps.model.LatLng;

import java.util.Collection;
import java.util.Date;

import br.ufscar.auxiliares.EasyDate;
import br.ufscar.auxiliares.EasyTime;

public class Truck {
    private String name;
    //private LatLng localization;
    private EasyDate lastUpdate;
    private int priceRange;
    private Collection<FoodTruckLocation> locations;
    private Collection<FoodTruckTag> tags;
    private Collection<MenuEntry> menu;
    private Collection<Review> reviews;

    Truck(String _name, LatLng _localization){
        this.name = _name;
        this.localization = _localization;
        this.lastUpdate = new Date();
    }

    public LatLng getCurrentLocation() {
        for (FoodTruckLocation l : locations) {
            if (EasyDate.nowInRange(l.getStartDate(), l.getEndDate()) && EasyTime.nowInRange(l.getOpensAt(), l.getClosesAt()))
                return l.getLocation();
        }
        return null;
    }

    //public Date getLastUpdate() { return lastUpdate; }

    public String getName(){ return name; }
    public void setLocalization(LatLng _localization) { localization = _localization; }
    public void setLastUpdate() { lastUpdate = new Date(); }
    public void setName(String _name) { name = _name; }

}
