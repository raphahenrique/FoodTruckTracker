package br.ufscar.foodtruck;

import android.content.Context;

import java.io.Serializable;

public class FoodTruckTag implements Serializable {
    private String name;
    private Context ctx;

    public FoodTruckTag(String name) {
        this.name = name;
    }

    public FoodTruckTag(int tagID, Context ctx){
        this.ctx = ctx;
        name = ctx.getResources().getStringArray(R.array.tags_array)[tagID];

    }

    public String getName() {
        return name;
    }
}