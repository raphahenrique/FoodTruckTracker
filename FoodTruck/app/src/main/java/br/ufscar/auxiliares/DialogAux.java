package br.ufscar.auxiliares;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.facebook.CallbackManager;
import com.facebook.Profile;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import br.ufscar.foodtruck.FoodTruckTag;
import br.ufscar.foodtruck.R;
import br.ufscar.foodtruck.Truck;

public class DialogAux implements GoogleMap.OnMarkerClickListener {

    private Context ctx;
    private final Dialog dialog;

    private TextView txtCardapio;

    private ImageView imgFoodTruck;
    private RatingBar barAvaliacao;
    private ImageButton btnPositivo;
    private ImageButton btnNegativo;
    private Button btnOk;
    private TextView txtTags;
    private TextView txtPriceRange;
    private TextView txtNomeFoodtruck;

    private Truck currentTruck;

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private Profile currentProfile;

    public DialogAux(Context ctx){
        this.ctx = ctx;
        dialog = new Dialog(ctx);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //currentTruck = Truck.getFoodTruckByName(marker.getTitle());
        showDialog(ctx, marker);

        return true;
    }

    public void showDialog(Context ctx, Marker marker){



        marker.hideInfoWindow();

        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.food_truck_info);

        dialog.setTitle(marker.getTitle());

//        loadComponents(Data.getTruckById(Integer.parseInt(marker.getSnippet())));
        loadComponents((Truck) marker.getTag());

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        txtCardapio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abre uma lista do cardapio  exemplo:
                /*
                final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                // Set the dialog title
                builder.setTitle("Tags para o Food Truck")
                        // Specify the list array, the items to be selected by default (null for none),
                        // and the listener through which to receive callbacks when items are selected
                        .setMultiChoiceItems(R.array.tags_array, null,
                                new DialogInterface.OnMultiChoiceClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which,
                                                        boolean isChecked) {

                                        FoodTruckTag currentTag = new FoodTruckTag(which, ctx);
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
                 */
            }
        });

        txtTags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //abre uma lista das tags
            }
        });

        dialog.show();

    }


    public void loadComponents(Truck t){
        imgFoodTruck = (ImageView)dialog.findViewById(R.id.img_foodTruck);
        barAvaliacao = (RatingBar) dialog.findViewById(R.id.bar_avaliacao);
        btnPositivo = (ImageButton) dialog.findViewById(R.id.btn_positivo);
        btnNegativo = (ImageButton) dialog.findViewById(R.id.btn_negativo);
        txtCardapio = (TextView)dialog.findViewById(R.id.txt_cardapio_info);
        btnOk = (Button)dialog.findViewById(R.id.btn_ok_info);
        txtTags = (TextView)dialog.findViewById(R.id.txtTags);
        txtPriceRange = (TextView)dialog.findViewById(R.id.txtPriceRange);
        txtNomeFoodtruck = (TextView)dialog.findViewById(R.id.txtNomeFoodtruck);

        if (t != null) {
            String tags = "";
            for (FoodTruckTag tag : t.getTags())
                tags += tag.getName() + ", ";
            txtTags.setText(tags.substring(0, tags.length() - 2));

            String priceRange = "";
            for (int i = 0; i < t.getPriceRange(); i++)
                priceRange += "$";
            txtPriceRange.setText(priceRange);

            barAvaliacao.setRating(t.mediaReviews());

            //imgFoodTruck.setImageBitmap(t.getCoverPicture());

            txtNomeFoodtruck.setText(t.getName());
        } else {
            Log.e("Null truck", "Null truck was returned");
        }
    }


}