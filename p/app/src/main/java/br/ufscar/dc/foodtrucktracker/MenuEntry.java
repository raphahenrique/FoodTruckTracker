package br.ufscar.dc.foodtrucktracker;

import java.util.Collection;

public class MenuEntry {
    private String name;
    private String description;
    private float price;
    private Collection<Ingredients> ingredients;
    private String picture;

    private class Ingredients {
        private String name;
    }
}
