package br.ufscar.dc.foodtrucktracker;

import java.util.Collection;

public class FoodTruck {
    private String name;
    private int priceRange;
    private Collection<FoodTruckTag> tags;
    private Collection<MenuEntry> menu;
    private Collection<Review> reviews;
}
