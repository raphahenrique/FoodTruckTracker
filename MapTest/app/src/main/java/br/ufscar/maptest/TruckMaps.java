package br.ufscar.maptest;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class TruckMaps extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public List<Truck> truckList = new ArrayList<Truck>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_truck_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

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

        truckList.add(new Truck("Tomodaty",new LatLng(-22.000555, -47.893916)));

        for(Truck truck : truckList)
        {
            mMap.addMarker(new MarkerOptions().position(truck.getLocalization()).title(truck.getName()));
        }
        mMap.addMarker(new MarkerOptions().position(mainTruck.getLocalization()).title(mainTruck.getName()));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(mainTruck.getLocalization()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mainTruck.getLocalization(), 15));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
    }
}
