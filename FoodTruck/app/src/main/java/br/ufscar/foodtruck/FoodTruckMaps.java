package br.ufscar.foodtruck;

import android.annotation.SuppressLint;
import android.Manifest;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.location.LocationManager;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import br.ufscar.auxiliares.DialogAux;
import br.ufscar.listeners.MyLocationListener;


public class FoodTruckMaps extends FragmentActivity implements OnMapReadyCallback {

    MapView mMapView;
    private GoogleMap mMap;

    private String[] mSideBarMenu;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    private CharSequence mTitle;
    private String[] mSideBarTitles;

    public List<Truck> truckList = new ArrayList<Truck>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//Prepara o menu da sidebar com onPrepareOptionsMenu();ns
        setContentView(R.layout.activity_food_truck_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mSideBarMenu = getResources().getStringArray(R.array.sidebar_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        mDrawerList.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        
        mDrawerList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, mSideBarMenu));
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.drawer_open, R.string.drawer_closed) {
            //FUNÇÃO DA SIDEBAR FECHADA
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //invalidateOptionsMenu(); //Prepara o menu da sidebar com onPrepareOptionsMenu();
            }
            //FUNÇÃO DA SIDEBAR ABERTA
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //invalidateOptionsMenu(); //Prepara o menu da sidebar com onPrepareOptionsMenu();
            }
        };
        //Set drawer toggle

        mDrawerLayout.addDrawerListener(mDrawerToggle);
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
            mMap.addMarker(new MarkerOptions().position(truck.getLocalization()).title(truck.getName()));

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

        //mMap.moveCamera(CameraUpdateFactory.newLatLng(newLastLocation));

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(newLastLocation, 15));

        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);
        //mMap.animateCamera(CameraUpdateFactory.zoomIn());
        //mMap.animateCamera(CameraUpdateFactory.zoomTo(15), 2000, null);

        mMap.setOnMarkerClickListener(new DialogAux(this));

    }

    @SuppressLint("NewApi")  // We check which build version we are using.
    //Funcao que pega localização atual do usuário
    public LatLng atualizaPosicao(Context ctx, GoogleMap mMap){

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

        LocationManager mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new MyLocationListener(mMap, this));

        Location lastLocation = mlocManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        //LatLng newLastLocation = new LatLng(lastLocation.getLatitude(),lastLocation.getLongitude());
        LatLng newLastLocation = new LatLng(-22.008474,-47.891448);

//        for(Truck truck : truckList)
//        {
//            mMap.addMarker(new MarkerOptions().position(truck.getLocalization()).title(truck.getName()));
//
//        }

        return newLastLocation;

    }

    // DRAWER FUNCTIONS DOWN BELOW
    // LISTENER DO DRAWER
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id)
        {
            selectItem(position);
        }

    }

    // FUNÇÃO DE SELECIONAR O ITEM NO SIDEBAR
    private void selectItem(int position)
    {
        if(mDrawerList.isItemChecked(position))
            mDrawerList.setSelection(position);
        else
            mDrawerList.setSelection(position);
        //mDrawerLayout.closeDrawer(mDrawerList);
    }

    //Chamado por "invalidateOptionsMenu
    public boolean onPrepareOptions(Menu menu) {
        //se o drawer tiver aberto esconder itens relacionados a view de content
        boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
        menu.findItem(R.id.action_websearch).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    public static class SideBarFragment extends Fragment {
        public static final String ARG_SIDEBAR_NUMBER = "sidebar_number";

        public SideBarFragment() {
            // Empty constructor required for fragment subclasses
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sidebar, container, false);
            int i = getArguments().getInt(ARG_SIDEBAR_NUMBER);
            String planet = getResources().getStringArray(R.array.sidebar_array)[i];

            int imageId = getResources().getIdentifier(planet.toLowerCase(Locale.getDefault()),
                    "drawable", getActivity().getPackageName());
            ((ImageView) rootView.findViewById(R.id.image)).setImageResource(imageId);
            getActivity().setTitle("banana");
            return rootView;
        }
    }
}