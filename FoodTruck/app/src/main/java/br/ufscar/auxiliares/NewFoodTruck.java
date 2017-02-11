package br.ufscar.auxiliares;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Collection;

import br.ufscar.foodtruck.FoodTruckTag;
import br.ufscar.foodtruck.R;
import br.ufscar.foodtruck.Truck;


public class NewFoodTruck implements GoogleMap.OnMapLongClickListener {

    private Context ctx;
    private GoogleMap mMap;

    private EditText txtNome;
    private Button btnTags;
    private SeekBar priceRange;
    private LatLng location;

    private int intPriceRange;

    private Collection<FoodTruckTag> tagsSelected;

    public NewFoodTruck(Context ctx,GoogleMap googleMap){
        this.ctx = ctx;
        mMap = googleMap;
    }

    @Override
    public void onMapLongClick(LatLng latLng) {

        AlertDialog.Builder build = new AlertDialog.Builder(ctx);
        build.setTitle("Novo Food Truck");

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View form = inflater.inflate(R.layout.food_truck_register, null);

        txtNome = (EditText)form.findViewById(R.id.txt_nome_food_truck);
        priceRange = (SeekBar)form.findViewById(R.id.price_range_seekbar);
        btnTags = (Button)form.findViewById(R.id.btn_tags);


        PriceRangeSetup();

        location = latLng;


        btnTags.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                tagsSelected = new ArrayList<FoodTruckTag>();

                final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                //LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                //final View form = inflater.inflate(R.layout.item_data, null);

                // Set the dialog title
                builder.setTitle("Tags para o Food Truck")
                        // Specify the list array, the items to be selected by default (null for none),
                        // and the listener through which to receive callbacks when items are selected
                        .setMultiChoiceItems(R.array.tags_array, null,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which,
                                                        boolean isChecked) {

                                        FoodTruckTag currentTag = new FoodTruckTag(String.valueOf(which));
                                        if (isChecked) {
                                            // If the user checked the item, add it to the selected items
                                            tagsSelected.add(currentTag);
                                            Log.e("Adiciona tag: ",currentTag.getName());
                                        } else {
                                            // Else, if the item is already in the array, remove it
                                            tagsSelected.remove(currentTag);
                                            Log.e("remove tag: ",currentTag.getName());
                                        }
                                    }
                                })
                        // Set the action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK, so save the mSelectedItems results somewhere
                                // or return them to the component that opened the dialog

                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                for (FoodTruckTag iTag : tagsSelected) {
                                    tagsSelected.remove(iTag);
                                }
                            }
                        });


                builder.create();
                builder.show();

            }
        });


        build.setView(form);

        build.setPositiveButton("Criar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) //formulario
            {
                if(!emptyName()){
                    String name = txtNome.getText().toString();
                    Truck newTruck = new Truck(name,location,intPriceRange,tagsSelected);

                    mMap.addMarker(new MarkerOptions().position(location).title(name));

                    //EXEMPLO DE MARKER PERSONALIZADO
                    //MarkerOptions mo = newMarker(posicao, tmp.getTipo().toString(), "Data: " + d + "/" + (m+1) + "/" +a + "\r\nHora: " + hora +  ":" + min, BitmapDescriptorFactory.fromAsset(tmp.getTipo().toString().toLowerCase() + ".png"), ctx);
                    //map.addMarker(mo);

                }else{
                    //fazer uma janelinha de erro
                    Log.e("erro food truck", "LANCAR JANELA PARA PREENCHER NOME ");
                }

            }
        });

        build.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        build.create();
        build.show();


    }

    private boolean emptyName(){

        if((txtNome.getText().toString().equals(""))||(txtNome.getText().toString().equals(" "))){
            return true;
        }else{
            return false;
        }

    }

    private void PriceRangeSetup(){
        priceRange.setProgress(1);
        priceRange.incrementProgressBy(1);
        priceRange.setMax(2);       // 0 - barato
                                    // 1 - Médio
                                    // 2 - Caro

        priceRange.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {

                intPriceRange = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

    }

}
