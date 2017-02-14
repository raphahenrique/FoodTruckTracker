package br.ufscar.foodtruck;

import android.graphics.Bitmap;

import com.google.android.gms.maps.model.LatLng;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.ufscar.auxiliares.EasyDate;
import br.ufscar.auxiliares.EasyTime;

public class Truck {
    private int id;
    private String name;
    private EasyDate lastUpdate;
    private int priceRange;
    private Bitmap coverPicture;
    private boolean byOwner;

    private Collection<FoodTruckLocation> locations;
    private Collection<FoodTruckTag> tags;
    private List<MenuEntry> menu;
    private Collection<Review> reviews;

    //Construtor com reviews
    public Truck(String name, LatLng location, int priceRange, Collection<FoodTruckTag> tags,
                 Collection<Review> reviews){
        id = -1;
        this.name = name;
        this.locations = new LinkedList<>();
        locations.add(new FoodTruckLocation(location));
        this.priceRange = priceRange;
        this.tags = tags;
        this.reviews = reviews;
        this.menu = new LinkedList<>();
    }

    public Truck(int id, String name, LatLng location, int priceRange, Collection<FoodTruckTag> tags){
        this.id = id;
        this.name = name;
        this.locations = new LinkedList<>();
        locations.add(new FoodTruckLocation(location));
        this.priceRange = priceRange;
        this.tags = tags;
        this.reviews = new LinkedList<>();
        this.menu = new LinkedList<>();
    }


    public FoodTruckLocation getCurrentLocation() {
        for (FoodTruckLocation l : locations) {
            if (EasyDate.nowInRange(l.getStartDate(), l.getEndDate()) && EasyTime.nowInRange(l.getOpensAt(), l.getClosesAt()))
                return l;
        }
        return null;
    }

    public void addReview(Review r) {
        reviews.add(r);
    }

    public float mediaReviews() {
        float somaNotas = 0;
        int contador = 0;
        if (reviews!=null) {
            for (Review r : reviews) {
                somaNotas += r.getRating();
                contador++;
            }
        }
        if (contador == 0)
            return 0;
        return somaNotas/contador;
    }

    public CharSequence[] menuItems() {
        CharSequence[] items = new CharSequence[menu.size()];
        for (int i = 0; i < menu.size(); i++)
            items[i] = menu.get(i).getName();
        return items;
    }

    public void addMenuItem(MenuEntry e) {
        menu.add(e);
    }

    // Getters and setters

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

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

    public void setMenu(List<MenuEntry> menu) {
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
