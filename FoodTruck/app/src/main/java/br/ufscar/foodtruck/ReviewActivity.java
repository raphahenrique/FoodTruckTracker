package br.ufscar.foodtruck;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.facebook.Profile;

import java.io.Serializable;
import java.util.ArrayList;

import br.ufscar.auxiliares.NewReview;

public class ReviewActivity extends AppCompatActivity implements Serializable {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ImageView reviewImgFoodTruck;
    private TextView reviewTxtName;
    private ListView reviewListview;

    private Profile currentProfile;
    private static CustomAdapter adapter;
    private ArrayList<Review> dataReview;

    private String firstName;
    private String lastName;
    private String picture;

    private String truckName;
    private int truckPos;
    private int cont;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        loadBundle();

        loadComponents();

        toolbar.setTitle("Avaliações");

        toolbar.setLogo(R.drawable.flat_icon);

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        loadListView();

        new DownloadImage(reviewImgFoodTruck).execute("http://peixeurbano.s3.amazonaws.com/2011/9/29/0d47c39e-4ca6-4375-8405-78219355834d/Big/000254871jp_v1_big_001.jpg");
        reviewImgFoodTruck.setScaleType(ImageView.ScaleType.CENTER_INSIDE);

        reviewTxtName.setText(truckName.toString());

        fab.setOnClickListener(new NewReview(this,truckPos));



    }

    private void loadBundle() {

        Bundle bundle = getIntent().getExtras();

        currentProfile = Profile.getCurrentProfile();
        if (currentProfile != null) {
            Log.e("LOGADO", "Usuario logado=" + currentProfile.getFirstName() + " " + currentProfile.getLastName());
        }
        firstName = bundle.getString("first_name");
        lastName = bundle.getString("last_name");
        picture = bundle.getString("picture");
        truckName = bundle.getString("truck_name");
        cont = bundle.getInt("cont");
        truckPos = bundle.getInt("truckPos");

        dataReview = new ArrayList<Review>();

        for(int i = 0; i<=cont;i++){

            String name = bundle.getString("user_"+i);
            int rating = bundle.getInt("rating_"+i);
            String comment = bundle.getString("comment_"+i);
            Review newReview = new Review(name,rating,comment,null);
            dataReview.add(newReview);
            Log.e("data review:", "name:"+name+"; rating" + rating);
        }

        //

    }

    private void loadComponents(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        reviewImgFoodTruck = (ImageView) findViewById(R.id.review_img_food_truck);
        reviewTxtName = (TextView) findViewById(R.id.review_name);
        reviewListview = (ListView) findViewById(R.id.review_listview);


    }

    private void loadListView(){


        adapter = new CustomAdapter(this,dataReview);

        reviewListview.setAdapter(adapter);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // handle arrow click here
        if (item.getItemId() == android.R.id.home) {
            finish(); // close this activity and return to preview activity (if there is any)
        }

        return super.onOptionsItemSelected(item);
    }

}
