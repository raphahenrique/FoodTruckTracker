package br.ufscar.auxiliares;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.net.Uri;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
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
import br.ufscar.foodtruck.Review;
import br.ufscar.foodtruck.ReviewActivity;
import br.ufscar.foodtruck.Truck;

public class DialogAux implements GoogleMap.OnMarkerClickListener {

    private Context ctx;
    private final Dialog dialog;

    private Button buttonCardapio;

    private ImageView imgFoodTruck;
    private RatingBar barAvaliacao;
    private ImageView btnPositivo;
    private ImageView btnNegativo;
    private Button btnOk;
    private TextView txtTags;
    private TextView txtPriceRange;
    private TextView txtNomeFoodtruck;
    private TextView txtScore;

    //private Truck currentTruck;

    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private Profile currentProfile;

    public DialogAux(Context ctx){
        this.ctx = ctx;
        dialog = new Dialog(ctx);
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialogInterface, int i, KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.KEYCODE_BACK)
                    dialog.dismiss();
                return true;
            }
        });
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        //currentTruck = Truck.getFoodTruckByName(marker.getTitle());
        showDialog(ctx, marker);

        return true;
    }



    public void showDialog(final Context ctx, Marker marker){
        marker.hideInfoWindow();

        //dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.food_truck_info);

        dialog.setTitle(marker.getTitle());

        final Truck curTruck = (Truck) marker.getTag();
        loadComponents(curTruck);

        barAvaliacao.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                Bundle bundle = new Bundle();

                currentProfile = Profile.getCurrentProfile();
                if (currentProfile != null) {
                    bundle.putString("first_name",currentProfile.getFirstName());
                    bundle.putString("last_name",currentProfile.getLastName());
                    bundle.putString("picture",currentProfile.getProfilePictureUri(200,200).toString());
                    Log.e("LOGADO", "Usuario logado=" + currentProfile.getFirstName() + " " + currentProfile.getLastName());
                }

                //bundle.putSerializable("currentTruck", curTruck);
                int i=0;
                for(Review elem : curTruck.getReviews()){
                    bundle.putInt("rating_"+i,elem.getRating());
                    bundle.putString("user_"+i,elem.getName());
                    bundle.putString("comment_"+i,elem.getComment());
                    i++;
                }

                bundle.putInt("cont",i);
                Intent reviewIntent = new Intent(ctx.getApplicationContext(),ReviewActivity.class);
                reviewIntent.putExtras(bundle);
                ctx.startActivity(reviewIntent);
                return false;
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonCardapio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //abre uma lista do cardapio  exemplo:

                final AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

                // Set the dialog title
                builder.setTitle("Cardápio")
                        // Specify the list array, the items to be selected by default (null for none),
                        // and the listener through which to receive callbacks when items are selected
                        .setItems(curTruck.menuItems(), null)
                        // Set the action buttons
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                // User clicked OK, so save the mSelectedItems results somewhere
                                // or return them to the component that opened the dialog

                            }
                        });


                builder.create();
                builder.show();

            }
        });

        btnPositivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curTruck.getCurrentLocation().upvote();
                txtScore.setText("Veracidade: "+curTruck.getCurrentLocation().getScore());
            }
        });

        btnNegativo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                curTruck.getCurrentLocation().downvote();
                txtScore.setText("Veracidade: "+curTruck.getCurrentLocation().getScore());
            }
        });

        dialog.show();

    }


    public void loadComponents(Truck t){
        imgFoodTruck = (ImageView)dialog.findViewById(R.id.img_foodTruck);
        barAvaliacao = (RatingBar) dialog.findViewById(R.id.bar_avaliacao);
        btnPositivo = (ImageView) dialog.findViewById(R.id.btn_positivo);
        btnNegativo = (ImageView) dialog.findViewById(R.id.btn_negativo);
        buttonCardapio = (Button) dialog.findViewById(R.id.buttonCardapio);
        btnOk = (Button)dialog.findViewById(R.id.btn_ok_info);
        txtTags = (TextView)dialog.findViewById(R.id.txtTags);
        txtPriceRange = (TextView)dialog.findViewById(R.id.txtPriceRange);
        txtNomeFoodtruck = (TextView)dialog.findViewById(R.id.txtNomeFoodtruck);
        txtScore = (TextView) dialog.findViewById(R.id.txtScore);

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

            new DownloadImage(imgFoodTruck).execute("http://peixeurbano.s3.amazonaws.com/2011/9/29/0d47c39e-4ca6-4375-8405-78219355834d/Big/000254871jp_v1_big_001.jpg");
            imgFoodTruck.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

            txtNomeFoodtruck.setText(t.getName());
        } else {
            Log.e("Null truck", "Null truck was returned");
        }
    }


}