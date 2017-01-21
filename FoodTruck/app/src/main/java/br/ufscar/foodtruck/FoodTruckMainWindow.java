package br.ufscar.foodtruck;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FoodTruckMainWindow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_truck_main_window);
    }

    public void changeActivity(View view)
    {
        Intent intent = new Intent(this, FoodTruckMaps.class);
        startActivity(intent);
    }
}
