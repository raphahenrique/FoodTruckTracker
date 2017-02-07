package br.ufscar.foodtruck;

import android.graphics.Bitmap;
import com.google.android.gms.maps.model.LatLng;
import java.util.Collection;
import java.util.LinkedList;

import br.ufscar.auxiliares.EasyDate;
import br.ufscar.auxiliares.EasyTime;

public class Truck {
    private String name;
    private EasyDate lastUpdate;
    private int priceRange;
    private Bitmap coverPicture;
    private boolean byOwner;

    private Collection<FoodTruckLocation> locations;
    private Collection<FoodTruckTag> tags;
    private Collection<MenuEntry> menu;
    private Collection<Review> reviews;

    Truck(String name, LatLng location, int priceRange, Collection<FoodTruckTag> tags){
        this.name = name;
        this.locations = new LinkedList<>();
        locations.add(new FoodTruckLocation(location));
        this.priceRange = priceRange;
    }

    public LatLng getCurrentLocation() {
        for (FoodTruckLocation l : locations) {
            if (EasyDate.nowInRange(l.getStartDate(), l.getEndDate()) && EasyTime.nowInRange(l.getOpensAt(), l.getClosesAt()))
                return l.getLocation();
        }
        return null;
    }

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EasyDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(EasyDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public int getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(int priceRange) {
        this.priceRange = priceRange;
    }

    public Bitmap getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(Bitmap coverPicture) {
        this.coverPicture = coverPicture;
    }

    public Collection<FoodTruckLocation> getLocations() {
        return locations;
    }

    public void setLocations(Collection<FoodTruckLocation> locations) {
        this.locations = locations;
    }

    public Collection<FoodTruckTag> getTags() {
        return tags;
    }

    public void setTags(Collection<FoodTruckTag> tags) {
        this.tags = tags;
    }

    public Collection<MenuEntry> getMenu() {
        return menu;
    }

    public void setMenu(Collection<MenuEntry> menu) {
        this.menu = menu;
    }

    public Collection<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Collection<Review> reviews) {
        this.reviews = reviews;
    }

    public boolean isByOwner() {
        return byOwner;
    }

    public void setByOwner(boolean byOwner) {
        this.byOwner = byOwner;
    }
}
