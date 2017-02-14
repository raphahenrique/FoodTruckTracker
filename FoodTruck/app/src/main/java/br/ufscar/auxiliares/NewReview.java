package br.ufscar.auxiliares;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.facebook.Profile;

import br.ufscar.foodtruck.R;
import br.ufscar.foodtruck.Review;

public class NewReview implements View.OnClickListener {

    private Context ctx;
    private RatingBar barReview;
    private EditText txtComentario;

    private int nroFoodTruck;

    public NewReview(Context ctx, int nroFoodTruck) {
        this.ctx = ctx;
        this.nroFoodTruck = nroFoodTruck;
    }

    @Override
    public void onClick(View view) {
        AlertDialog.Builder build = new AlertDialog.Builder(ctx);
        build.setTitle("Nova Avaliação");

        LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View form = inflater.inflate(R.layout.food_truck_review, null);

        txtComentario = (EditText)form.findViewById(R.id.txtComentario);
        barReview = (RatingBar) form.findViewById(R.id.bar_review);


        build.setView(form);

        build.setPositiveButton("Avaliar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) //formulario
            {
                Profile currentProfile = Profile.getCurrentProfile();
                if(currentProfile==null){

                    Snackbar snackbar = Snackbar
                            .make(form, "Voce precisa estar logado com Facebook!!", Snackbar.LENGTH_LONG);

                    snackbar.show();


                }else{
                    String comentario = txtComentario.getText().toString();

                    Data.truckList.get(nroFoodTruck).addReview(new Review(currentProfile.getFirstName() +
                            " "+ currentProfile.getLastName(),barReview.getNumStars(), comentario,
                            currentProfile.getProfilePictureUri(100,100).toString()));



                    //EXEMPLO DE MARKER PERSONALIZADO
                    //MarkerOptions mo = newMarker(posicao, tmp.getTipo().toString(), "Data: " + d + "/" + (m+1) + "/" +a + "\r\nHora: " + hora +  ":" + min, BitmapDescriptorFactory.fromAsset(tmp.getTipo().toString().toLowerCase() + ".png"), ctx);
                    //map.addMarker(mo);


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
}
