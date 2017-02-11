package br.ufscar.auxiliares;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import br.ufscar.foodtruck.R;


public class NewFoodTruck implements GoogleMap.OnMapLongClickListener {

    private Context ctx;

    private EditText txtNome;
    private Button btnTags;
    private SeekBar priceRange;


    public NewFoodTruck(Context ctx){
        this.ctx = ctx;

    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        AlertDialog.Builder build = new AlertDialog.Builder(ctx);
        build.setTitle("Novo Food Truck");

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View form = inflater.inflate(R.layout.food_truck_register, null);

        txtNome = (EditText)form.findViewById(R.id.txt_nome_food_truck);

        btnTags = (Button)form.findViewById(R.id.btn_tags);

        btnTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*
                AlertDialog.Builder build3 = new AlertDialog.Builder(ctx);
                build3.setTitle("Hora ocorr�ncia");

                LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                final View form = inflater.inflate(R.layout.item_hora, null);

                tm = ((TimePicker)form.findViewById(R.id.timePicker));

                tm.setIs24HourView(true);

                build3.setView(form);

                build3.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) //formulario
                    {
                        hora = tm.getCurrentHour();
                        min = tm.getCurrentMinute();

                        txtHora.setText(hora+":"+min);
                    }
                });

                build3.create();
                build3.show();
                 */
            }
        });


        build.setView(form);

        build.setPositiveButton("Criar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) //formulario
            {

                //MarkerOptions mo = newMarker(posicao, tmp.getTipo().toString(), "Data: " + d + "/" + (m+1) + "/" +a + "\r\nHora: " + hora +  ":" + min, BitmapDescriptorFactory.fromAsset(tmp.getTipo().toString().toLowerCase() + ".png"), ctx);

                //map.addMarker(mo);

            }
        });


        build.create();
        build.show();


    }
}
