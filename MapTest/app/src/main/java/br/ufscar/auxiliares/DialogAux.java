package br.ufscar.auxiliares;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import br.ufscar.maptest.R;

public class DialogAux implements GoogleMap.OnMarkerClickListener {

    private Context ctx;
    private final Dialog dialog;

    private TextView txtFoodTruckName;
    private TextView txtCardapio;

    private ImageView imgFoodTruck;
    private RatingBar barAvaliacao;
    private ImageButton btnPositivo;
    private ImageButton btnNegativo;
    private Button btnOk;

    public DialogAux(Context ctx){
        this.ctx = ctx;
        dialog = new Dialog(ctx);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        showDialog(ctx,marker);

        return true;
    }

    public void showDialog(Context ctx, Marker marker){

        marker.hideInfoWindow();

        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.food_truck_info);

        loadComponents();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtFoodTruckName.setText(marker.getTitle());

        txtCardapio.setText("Cardapio");



        dialog.show();

    }

    public void loadComponents(){
        txtFoodTruckName = (TextView)dialog.findViewById(R.id.txt_nome_food_truck);
        imgFoodTruck = (ImageView)dialog.findViewById(R.id.img_foodTruck);
        barAvaliacao = (RatingBar) dialog.findViewById(R.id.bar_avaliacao);
        btnPositivo = (ImageButton) dialog.findViewById(R.id.btn_positivo);
        btnNegativo = (ImageButton) dialog.findViewById(R.id.btn_negativo);
        txtCardapio = (TextView)dialog.findViewById(R.id.txt_cardapio_info);
        btnOk = (Button)dialog.findViewById(R.id.btn_ok_info);

    }


}