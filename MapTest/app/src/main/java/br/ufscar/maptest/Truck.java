package br.ufscar.maptest;


import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class Truck {
    Truck(String _name, LatLng _localization){
        this.name = _name;
        this.localization = _localization;
        this.lastUpdate = new Date();
    }

    public String getName(){ return name; }
    public LatLng getLocalization() { return localization; }
    public Date getLastUpdate() { return lastUpdate; }

    public void setLocalization(LatLng _localization) { localization = _localization; }
    public void setLastUpdate() { lastUpdate = new Date(); }
    public void setName(String _name) { name = _name; }

    private String name;
    private LatLng localization;
    private Date lastUpdate;

}
