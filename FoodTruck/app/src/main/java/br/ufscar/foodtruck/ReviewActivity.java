package br.ufscar.foodtruck;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.Serializable;
import java.util.Collection;

public class ReviewActivity extends AppCompatActivity implements Serializable {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ImageView reviewImgFoodTruck;
    private TextView reviewTxtName;
    private ListView reviewListview;

    private static CustomAdapter adapter;
    private Collection<Review> dataReview;

    private String firstName;
    private String lastName;
    private String picture;

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

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadBundle() {

        Bundle bundle = getIntent().getExtras();

        firstName = bundle.getString("first_name");
        lastName = bundle.getString("last_name");
        picture = bundle.getString("picture");

        cont = bundle.getInt("cont");


        for(int i = 0; i<=cont;i++){

            String name = bundle.getString("user_"+i);
            int rating = bundle.getInt("rating_"+i);
            String comment = bundle.getString("comment_"+i);
            Review newReview = new Review(name,rating,comment,null);
            dataReview.add(newReview);
            Log.e("data review:", "name:"+name+"; rating" + rating);
        }

        Log.e("FIRST NAME: ", firstName);

    }

    private void loadComponents(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        reviewImgFoodTruck = (ImageView) findViewById(R.id.review_img_food_truck);
        reviewTxtName = (TextView) findViewById(R.id.review_name);
        reviewListview = (ListView) findViewById(R.id.review_listview);


    }

    private void loadListView(){


        //adapter = new CustomAdapter(this, )


        //reviewListview.setAdapter(adapter);

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
