package br.ufscar.foodtruck;

import java.io.Serializable;
import java.util.Collection;

public class MenuEntry implements Serializable {
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

    // Getters

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public float getPrice() {
        return price;
    }

    public Collection<String> getIngredients() {
        return ingredients;
    }

    public String getPicture() {
        return picture;
    }
}