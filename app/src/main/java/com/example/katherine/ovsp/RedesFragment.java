package com.example.katherine.ovsp;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.twitter.sdk.android.core.Twitter;


public class RedesFragment extends Fragment {

    private FragmentActivity myContext;
    public ImageButton facebook,twitter,instagram,youtube;


    @Override
    public void onAttach(Activity activity) {
        myContext=(FragmentActivity) activity;
        super.onAttach(activity);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_redes, container, false);
        facebook = (ImageButton)view.findViewById(R.id.btnfacebook);
        twitter = (ImageButton)view.findViewById(R.id.btntwitter);
        youtube = (ImageButton)view.findViewById(R.id.btnyoutube);
        instagram = (ImageButton)view.findViewById(R.id.btninstagram);

        facebook.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i_facebook = new Intent(getContext(), FacebookActivity.class);
                startActivity(i_facebook);

            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i_twitter = new Intent(getContext(), TwitterActivity.class);
                startActivity(i_twitter);

            }
        });

        youtube.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i_youtube = new Intent(getContext(), YoutubeActivity.class);
                startActivity(i_youtube);

            }
        });

        instagram.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i_instagram = new Intent(getContext(), InstagramActivity.class);
                startActivity(i_instagram);

            }
        });
        return view;
    }

}
