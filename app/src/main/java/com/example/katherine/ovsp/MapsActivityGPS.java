package com.example.katherine.ovsp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MapsActivityGPS extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String volcan;
    private Retrofit retrofit;
    LatLng coordenada, colombia;
    private Spinner spinner;
    private List lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps_gps);

        spinner = (Spinner) findViewById(R.id.spinnergps);
        String[] v = {"Todos","Azufral","Doña Juana","Las Ánimas","Galeras","Huila","Sotará","Puracé",
        "Machin","Ruiz","Santa Isable","Santa Rosa"};

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, v));
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());
        if (status == ConnectionResult.SUCCESS){
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.mapgps);
            mapFragment.getMapAsync(this);
        }else{
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status,(Activity)getApplicationContext(),10);
            dialog.show();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        retrofit = new Retrofit.Builder().baseUrl("http://www.datos.gov.co/resource/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        SitiosService service = retrofit.create(SitiosService.class);
        Call<List<Gps>> sitioRespuestaCall = service.obtenerListadeEquipos();

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);

        UiSettings uiSettings = mMap.getUiSettings();
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setAllGesturesEnabled(true);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            // Show rationale and request permission.
        }

        // Add a marker in Colombia and move the camera

        colombia = new LatLng(4.0000000, -72.0000000);
        float zoomLevel = 5;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(colombia, zoomLevel));

        sitioRespuestaCall.enqueue(new Callback<List<Gps>>() {
            @Override
            public void onResponse(Call<List<Gps>> call, Response<List<Gps>> response) {

                lista = response.body();
                //List<String> yt = new ArrayList<String>();
                recorrer(spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString());
                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                        mMap.clear();

                        String option = spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString();

                        recorrer(option);

                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        // Another interface callback
                    }


                });

            }

            @Override
            public void onFailure(Call<List<Gps>> call, Throwable t) {

                Log.e("ERROR", " OnFaillure: "+ t.getMessage());
            }
        });


    }

    public void recorrer(String option){

        for(int i=0; i < lista.size(); i++){

            Gps g = (Gps) lista.get(i);

            double latitud = Double.parseDouble(g.getLatitud());
            double longitud = Double.parseDouble(g.getLongitud());
            String volcan = (g.getVolcN());
            String nombre = (g.getNombre());
            String ovs = (g.getOvs());
            String ID = (g.getId());


            coordenada = new LatLng(latitud, longitud);

            if(option.equals("Todos")){

                dibujarMarcador(volcan, nombre, ovs,ID);

            }

            else if(volcan.equals(option)){

                dibujarMarcador(volcan, nombre, ovs, ID);

            }

        }

    }

    public void dibujarMarcador (String volcan, String nombre, String ovs, String ID){

        if(volcan.equals("Azufral")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Nombre: " + nombre + " ID: " + ID).snippet(ovs)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

        else if(volcan.equals("Doña Juana")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Nombre: " + nombre + " ID: " + ID).snippet(ovs)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        }

        else if(volcan.equals("Las Ánimas ")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Nombre: " + nombre + " ID: " + ID).snippet(ovs)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        }

        else if(volcan.equals("Galeras")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Nombre: " + nombre + " ID: " + ID).snippet(ovs)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }

        else if(volcan.equals("Huila")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Nombre: " + nombre + " ID: " + ID).snippet(ovs)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }

        else if(volcan.equals("Sotará")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Nombre: " + nombre + " ID: " + ID).snippet(ovs)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
        }
        else if(volcan.equals("Puracé")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Nombre: " + nombre + " ID: " + ID).snippet(ovs)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
        }
        else if(volcan.equals("Machin")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Nombre: " + nombre + " ID: " + ID).snippet(ovs)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }
        else if(volcan.equals("Ruiz")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Nombre: " + nombre + " ID: " + ID).snippet(ovs)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
        }
        else if(volcan.equals("Santa Isabel")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Nombre: " + nombre + " ID: " + ID).snippet(ovs)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
        }
        else if(volcan.equals("Santa Rosa")) {
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Nombre: " + nombre + " ID: " + ID).snippet(ovs)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
        }
        else{
            mMap.addMarker(new MarkerOptions().position(coordenada).title("Nombre: " + nombre + " ID: " + ID).snippet(ovs)
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
        }

    }
}
