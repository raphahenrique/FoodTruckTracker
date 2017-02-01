package br.ufscar.auxiliares;

import android.content.Context;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import android.app.Activity;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import br.ufscar.foodtruck.R;


public class FoodTruckInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

    private final View myView;
    private Context ctx;

    public FoodTruckInfoWindowAdapter(Context ctx) {
        myView = ((Activity)ctx).getLayoutInflater().inflate(R.layout.food_truck_info,null);
    }

    @Override
    public View getInfoContents(Marker marker) {
        TextView txtFoodTruckName = (TextView)myView.findViewById(R.id.txt_nome_food_truck);
        ImageView imgFoodTruck = (ImageView)myView.findViewById(R.id.img_foodTruck);
        RatingBar barAvaliacao = (RatingBar) myView.findViewById(R.id.bar_avaliacao);
        //ImageButton btnPositivo = (ImageButton) myView.findViewById(R.id.btn_positivo);
        //ImageButton btnNegativo = (ImageButton) myView.findViewById(R.id.btn_negativo);

        TextView txtCardapio = (TextView)myView.findViewById(R.id.txt_cardapio_info);





        return myView;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }
}
