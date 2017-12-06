package com.example.katherine.ovsp;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageManager;
import android.nfc.Tag;
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

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    String departamento;
    private Retrofit retrofit;
    LatLng coordenada, colombia;
    private Spinner spinner;
    private List lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        spinner = (Spinner) findViewById(R.id.spinner);
        String[] depto = {"Todo", "ANTIOQUIA", "ARAUCA", "ARCHIPIELAGO DE SAN ANDRES. PROV. Y STA CATALINA", "BOLIVAR"
                , "BOYACA", "CALDAS", "CAQUETA", "CASANARE", "CAUCA", "CESAR", "CHOCO", "CORDOBA",
                "CUNDINAMARCA", "GUAVIARE", "HUILA", "LA GUAJIRA", "MAGDALENA", "META", "NARINO",
                "NORTE DE SANTANDER", "PUTUMAYO", "QUINDIO", "RISARALDA", "SANTANDER", "SUCRE",
                "TOLIMA", "VALLE DEL CAUCA"};

        spinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, depto));
        departamento = getIntent().getExtras().getString("departamento");

        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getApplicationContext());

        if (status == ConnectionResult.SUCCESS) {
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
        } else {
            Dialog dialog = GooglePlayServicesUtil.getErrorDialog(status, (Activity) getApplicationContext(), 10);
            dialog.show();
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

            retrofit = new Retrofit.Builder().baseUrl("http://www.datos.gov.co/resource/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            SitiosService service = retrofit.create(SitiosService.class);
            Call<List<Acelerografo>> sitioRespuestaCall = service.obtenerListadeSitios();


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

            sitioRespuestaCall.enqueue(new Callback<List<Acelerografo>>() {
                @Override
                public void onResponse(Call<List<Acelerografo>> call, Response<List<Acelerografo>> response) {

                    lista = response.body();

                    recorrer(spinner.getItemAtPosition(spinner.getSelectedItemPosition()).toString());
                    spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                public void onFailure(Call<List<Acelerografo>> call, Throwable t) {

                    Log.e("ERROR", " OnFaillure: " + t.getMessage());
                }
            });
    }

    public void recorrer(String option) {

            for (int i = 0; i < lista.size(); i++) {

                Acelerografo a = (Acelerografo) lista.get(i);

                double latitud = Double.parseDouble(a.getLatitud());
                double longitud = Double.parseDouble(a.getLongitud());
                String depto = (a.getDepartamento());
                String nombre = (a.getNombreEstacion());
                String estado = (a.getEstado());
                String municipio = (a.getMunicipio());


                coordenada = new LatLng(latitud, longitud);

                if (option.equals("Todo")) {

                    dibujarMarcador(depto, nombre, estado, municipio);

                }

                if (depto.equals(option)) {

                    dibujarMarcador(depto, nombre, estado, municipio);

                }

            }

    }

    public void dibujarMarcador(String depto, String nombre, String estado, String municipio) {

            if (depto.equals("ANTIOQUIA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            } else if (depto.equals("ARAUCA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            } else if (depto.equals("ARCHIPIELAGO DE SAN ANDRES. PROV. Y STA CATALINA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            } else if (depto.equals("BOLIVAR")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            } else if (depto.equals("BOYACA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            } else if (depto.equals("CALDAS")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            } else if (depto.equals("CAQUETA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            } else if (depto.equals("CASANARE")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            } else if (depto.equals("CAUCA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            } else if (depto.equals("CESAR")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            } else if (depto.equals("CHOCO")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            } else if (depto.equals("CORDOBA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
            } else if (depto.equals("CUNDINAMARCA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            } else if (depto.equals("GUAVIARE")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            } else if (depto.equals("HUILA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            } else if (depto.equals("LA GUAJIRA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            } else if (depto.equals("MAGDALENA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            } else if (depto.equals("META")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)));
            } else if (depto.equals("NARINO")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
            } else if (depto.equals("NORTE DE SANTANDER ")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)));
            } else if (depto.equals("PUTUMAYO")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            } else if (depto.equals("QUINDIO")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_MAGENTA)));
            } else if (depto.equals("RISARALDA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ORANGE)));
            } else if (depto.equals("SANTANDER")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            } else if (depto.equals("SUCRE")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE)));
            } else if (depto.equals("TOLIMA")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_VIOLET)));
            } else if (depto.equals("VALLE DEL CAUCA ")) {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            } else {
                mMap.addMarker(new MarkerOptions().position(coordenada).title("Estación: " + nombre + " Estado:" + estado).snippet(municipio)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }

        }


    }

