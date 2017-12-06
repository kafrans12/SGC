package com.example.katherine.ovsp;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.support.v7.app.AppCompatActivity;


public class EquiposFragment extends Fragment {

    private ImageButton bmap1,bmap2;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=  inflater.inflate(R.layout.fragment_equipos, container, false);
        bmap1 = (ImageButton) view.findViewById(R.id.bmap1);
        bmap2 = (ImageButton) view.findViewById(R.id.bmap2);

        bmap1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivity.class);
                intent.putExtra("departamento", "NARINO");
                    startActivity(intent);

            }
        });

        bmap2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), MapsActivityGPS.class);
                intent.putExtra("volcan", "Galeras");
                startActivity(intent);
            }
        });
        return view;
    }

}
