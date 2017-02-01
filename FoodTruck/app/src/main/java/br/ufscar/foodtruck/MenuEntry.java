package br.ufscar.foodtruck;

import java.util.Collection;

public class MenuEntry {
    private String name;
    private String description;
    private float price;
    private Collection<String> ingredients;
    private String picture;

    public MenuEntry(String name, String description, float price, Collection<String> ingredients, String picture) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.ingredients = ingredients;
        this.picture = picture;
    }
}