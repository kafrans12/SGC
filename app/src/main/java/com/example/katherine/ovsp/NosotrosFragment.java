package com.example.katherine.ovsp;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import java.util.List;


public class NosotrosFragment extends Fragment {

    private FragmentActivity myContext;
    private RecyclerView recycler;
    private RecyclerView.Adapter adapter;
    public String linea;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nosotros, container, false);

        WebView objetivos = (WebView)view.findViewById(R.id.txobjetivos);
        objetivos.loadData(getResources().getString(R.string.txtobjetivos), "text/html","utf-8");
        objetivos.setBackgroundColor(Color.TRANSPARENT);
        return view;
    }

}
