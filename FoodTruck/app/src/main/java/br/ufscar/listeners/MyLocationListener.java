package br.ufscar.listeners;

import android.content.Context;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

public class MyLocationListener implements LocationListener
{
    private LatLng localAtual;
    private GoogleMap map;
    private Context ctx;

    public static boolean STARTED = false;

    public MyLocationListener(GoogleMap map, Context ctx)
    {
        this.ctx = ctx;
        this.map = map;
    }

    @Override
    public void onLocationChanged(Location loc)
    {
        if(STARTED)
            return;
        else
            STARTED = true;

        //map.clear();

        localAtual = new LatLng(loc.getLatitude(),loc.getLongitude());

        //List<DataTemplate> region =  Util.getEntriesInRadius(ctx, Util.getEntries(), localAtual, Util.RADIUS);
        //map.addMarker(new MarkerOptions().position(localAtual).title("Posição Atual").snippet("Crimes na região: " + region.size()));
        LatLngBounds bounds = new LatLngBounds.Builder().include(localAtual).build();
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(bounds.getCenter(), 15));

        map.addCircle(new CircleOptions().center(localAtual).radius(200).strokeWidth(20).strokeColor(Color.BLUE));


        //MapListener.addMarkersInRadius(region, localAtual, map, ctx);
    }

    @Override
    public void onProviderDisabled(String provider)
    {}

    @Override
    public void onProviderEnabled(String provider)
    {}

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras)
    {}
}