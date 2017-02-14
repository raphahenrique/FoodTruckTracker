package br.ufscar.foodtruck;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import br.ufscar.auxiliares.Data;
import br.ufscar.auxiliares.DialogAux;
import br.ufscar.auxiliares.FacebookLogin;
import br.ufscar.auxiliares.NewFoodTruck;


public class FoodTruckMaps extends AppCompatActivity
        implements OnMapReadyCallback, Serializable {

    private GoogleMap mMap;
    private String[] mTagsTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private RelativeLayout mDrawerContainer;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;


    private CallbackManager callbackManager;
    private LoginButton loginButton;
    private Profile currentProfile;

    private List<FoodTruckTag> activeTags;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        setContentView(R.layout.activity_food_truck_maps);

        callbackManager = CallbackManager.Factory.create();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        activeTags = new LinkedList<>();
        for (String s : mTagsTitles = getResources().getStringArray(R.array.tags_array))
            activeTags.add(new FoodTruckTag(s));

        loadComponents();

        if(isLoggedIn(accessToken)){

            accessTokenTracker = new AccessTokenTracker() {
                @Override
                protected void onCurrentAccessTokenChanged(
                        AccessToken oldAccessToken,
                        AccessToken currentAccessToken) {

                    // Set the access token using
                    // currentAccessToken when it's loaded or set.
                }
            };
            // If the access token is available already assign it.
            accessToken = AccessToken.getCurrentAccessToken();

            currentProfile = Profile.getCurrentProfile();
            if (currentProfile != null) {
                Log.e("LOGADO", "Usuario logado=" + currentProfile.getFirstName() + " " + currentProfile.getLastName());
            }

        }


        //Lista de permissoes pode ser encontrada no site do facebook developers
        List<String> permissionNeeds = Arrays.asList("email", "public_profile");
        loginButton.setReadPermissions(permissionNeeds);

        loginButton.registerCallback(callbackManager,new FacebookLogin());

        if (currentProfile != null) {
            //carregaImagem(currentProfile);
        }

        for (int i = 0; i < getResources().getStringArray(R.array.tags_array).length; i++)
            mDrawerList.setItemChecked(i, true);

    }

    private void loadComponents() {

        mTitle = mDrawerTitle = getTitle();
        mTagsTitles = getResources().getStringArray(R.array.tags_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                // invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerContainer = (RelativeLayout) findViewById(R.id.left_drawer_container);
        mDrawerList = (ListView)mDrawerContainer.findViewById(R.id.left_drawer);


        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mTagsTitles));

        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                boolean tagFound = false;
                for (FoodTruckTag tag : activeTags)
                    if (tag.getName().equals(getResources().getStringArray(R.array.tags_array)[i])) {
                        tagFound = true;
                        activeTags.remove(tag);
                        break;
                    }

                if (!tagFound)
                    activeTags.add(new FoodTruckTag(getResources().getStringArray(R.array.tags_array)[i]));

                pinTrucks();
            }
        });
        mDrawerList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);



        loginButton = (LoginButton)mDrawerContainer.findViewById(R.id.login_button);



        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setHomeButtonEnabled(true);



    }

    public boolean activeTag(FoodTruckTag tag) {
        for (FoodTruckTag t : activeTags) {
            if (t.getName().equals(tag.getName()))
                return true;
        }
        return false;
    }

    public void pinTrucks() {
        mMap.clear();
        boolean active;
        for (Truck truck : Data.truckList) {
            active = false;
            for (FoodTruckTag tag : truck.getTags())
                if (this.activeTag(tag))
                    active = true;
            if (active) {
                Data.markers.clear();
                Data.markers.add(mMap.addMarker(new MarkerOptions()
                        .position(truck.getCurrentLocation().getLocation())
                        .title(truck.getName())));
                Data.markers.peekLast().setTag(truck);
            }
        }
    }

    public void carregaImagem(Profile profile){
        //profile.getProfilePictureUri(200,200).toString()
        //profile.getProfilePictureUri(imgFoodTruck.getWidth(),imgFoodTruck.getHeight());

        //Preparar para imagem
        //new DownloadImage(imgFoodTruck).execute(profile.getProfilePictureUri(50,50).toString());

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (accessTokenTracker != null)
            accessTokenTracker.stopTracking();
    }

    public boolean isLoggedIn(AccessToken accessToken) {
        accessToken = AccessToken.getCurrentAccessToken();
        return accessToken != null;
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //Adicionado novo construtor de truck
        //Truck mainTruck = new Truck("Epamiondas", new LatLng(-22.008166, -47.891448));

        Collection<FoodTruckTag> tagsSelected;
        tagsSelected = new ArrayList<FoodTruckTag>();
        tagsSelected.add(new FoodTruckTag("Brasil"));
        tagsSelected.add(new FoodTruckTag("Cerveja Artesanal"));
        tagsSelected.add(new FoodTruckTag("Mexicano"));

        Collection<Review> reviewsSelected;
        reviewsSelected = new ArrayList<Review>();
        reviewsSelected.add(new Review("Marcio Oliveira",4,"Belo trabalho realizado, rápidos!",null));
        reviewsSelected.add(new Review("Marcia Oliveira",2,"Carne horrível",null));
        reviewsSelected.add(new Review("Jose Vieira",5,"Bom",null));

        //Truck mainTruck = new Truck("Epamiondas", new LatLng(-22.008166, -47.891448),1, tagsSelected);
        Data.truckList.add(new Truck("Quase 2", new LatLng(-22.008474, -47.890708),1, tagsSelected,reviewsSelected));
        Data.truckList.add(new Truck("Trem Bão", new LatLng(-22.005748, -47.896759),2, tagsSelected,reviewsSelected));
        Data.truckList.add(new Truck("Rancho Marginal", new LatLng(-22.002654, -47.892167),2, tagsSelected,null));
        Data.truckList.add(new Truck("Tomodaty", new LatLng(-22.000555, -47.893916),0, tagsSelected,null));

        //
        //Data.truckList.get(0).setCoverPicture(Convert.downloadImage("http://peixeurbano.s3.amazonaws.com/2011/9/29/0d47c39e-4ca6-4375-8405-78219355834d/Big/000254871jp_v1_big_001.jpg"));

        //Data.truckList.get(0).setCoverPicture();

        Data.truckList.get(0).addReview(new Review("Felipe",5, "daora demais", null));
        Data.truckList.get(0).addReview(new Review("Joao Marcelo",4, "medio daora", null));

        Data.truckList.get(0).addMenuItem(new MenuEntry("X-Tudo", "tem tudo", 10, null, null));
        Data.truckList.get(0).addMenuItem(new MenuEntry("X-Salada", "tem salada", 8, null, null));
        Data.truckList.get(0).addMenuItem(new MenuEntry("X-Bacon", "tem bacon", 9, null, null));
        Data.truckList.get(0).addMenuItem(new MenuEntry("X-Egg", "tem ovo", 8, null, null));

        for (Truck truck : Data.truckList) {
//            mMap.addMarker(new MarkerOptions()
//                            .position(truck.getCurrentLocation())
//                            .title(truck.getName())
//                            .snippet(Integer.toString(truck.getId())));
            Data.markers.add(mMap.addMarker(new MarkerOptions()
                                            .position(truck.getCurrentLocation().getLocation())
                                            .title(truck.getName())));
            Data.markers.peekLast().setTag(truck);
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);

        LatLng newLastLocation = atualizaPosicao(this,mMap);

        mMap.moveCamera(CameraUpdateFactory.newLatLng(newLastLocation));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        mMap.setOnMarkerClickListener(new DialogAux(this));

        mMap.setOnMapLongClickListener(new NewFoodTruck(this,mMap));


    }

    @SuppressLint("NewApi")  // We check which build version we are using.
    //Funcao que pega localização atual do usuário
    public LatLng atualizaPosicao(Context ctx, GoogleMap mMap){

        try {
            LocationManager mLocationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location myLocation = getLastKnownLocation(mLocationManager);

            LatLng newLastLocation = new LatLng(myLocation.getLatitude(), myLocation.getLongitude());

            return newLastLocation;
        } catch (NullPointerException e) {
            return new LatLng(-22.013429, -47.899514);
        }

    }


    private Location getLastKnownLocation(LocationManager mLocationManager) {
        mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;

        //Necessário para pegar a localização atual
        if ( Build.VERSION.SDK_INT >= 23 &&
                ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION )
                        != PackageManager.PERMISSION_GRANTED  &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION )
                        != PackageManager.PERMISSION_GRANTED
                )
        {
            Log.e("ERRO","RETORNANDO NULL");
            return null;
        }
        for (String provider : providers) {
            Location l = mLocationManager.getLastKnownLocation(provider);
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                // Found best last known location: %s", l);
                bestLocation = l;
            }
        }
        return bestLocation;
    }

    //Drawer Methods
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        //funcao de remocao e adicao de trucks aqui
        setTitle(mTagsTitles[position]);
        if(mDrawerList.isItemChecked(position))
            mDrawerList.setSelection(position);
        else
            mDrawerList.setSelection(position);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        //getActionBar().setTitle(mTitle);
    }

    /* Called whenever we call invalidateOptionsMenu() */
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        // If the nav drawer is open, hide action items related to the content view
        boolean drawerOpened = mDrawerLayout.isDrawerOpen(mDrawerContainer);
        //menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }
}