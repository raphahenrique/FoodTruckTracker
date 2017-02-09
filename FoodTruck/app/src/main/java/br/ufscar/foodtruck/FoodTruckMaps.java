package br.ufscar.foodtruck;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

import br.ufscar.auxiliares.DialogAux;


public class FoodTruckMaps extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public List<Truck> truckList = new ArrayList<Truck>();
    private CallbackManager callbackManager;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;

    private Profile currentProfile;

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


    }




    @Override
    public void onDestroy() {
        super.onDestroy();
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

        Truck mainTruck = new Truck("Epamiondas", new LatLng(-22.008166, -47.891448));
        truckList.add(new Truck("Quase 2", new LatLng(-22.008474, -47.890708)));
        truckList.add(new Truck("Trem Bão", new LatLng(-22.005748, -47.896759)));
        truckList.add(new Truck("Rancho Marginal", new LatLng(-22.002654, -47.892167)));
        truckList.add(new Truck("Tomodaty", new LatLng(-22.000555, -47.893916)));

        for (Truck truck : truckList) {
            mMap.addMarker(new MarkerOptions().position(truck.getCurrentLocation()).title(truck.getName()));

        }

        //mMap.addMarker(new MarkerOptions().position(mainTruck.getLocalization()).title(mainTruck.getName()));

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

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLastLocation, 15));

        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        //mMap.animateCamera(CameraUpdateFactory.zoomIn());
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        mMap.setOnMarkerClickListener(new DialogAux(this, callbackManager));



    }

    @SuppressLint("NewApi")  // We check which build version we are using.
    //Funcao que pega localização atual do usuário
    public LatLng atualizaPosicao(Context ctx, GoogleMap mMap){



/*
        LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListener(mMap, this));

        Location lastLocation = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        LatLng newLastLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
        //LatLng newLastLocation = new LatLng(-22.008474,-47.891448);

*/

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


}