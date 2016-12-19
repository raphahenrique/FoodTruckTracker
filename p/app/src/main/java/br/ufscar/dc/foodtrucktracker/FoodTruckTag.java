package br.ufscar.dc.foodtrucktracker;


public class FoodTruckTag {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public FoodTruckTag(String name) {
        this.name = name;
    }
}
