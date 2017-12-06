package com.example.katherine.ovsp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class TwitterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //setSupportActionBar(toolbar);


        WebView twitter = (WebView) findViewById(R.id.web_twitter);
        //Activamos el Javascript
        WebSettings webSettings = twitter.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Url que carga la app(webview)
        twitter.loadUrl("https://twitter.com/sgcol");

        //Forzazr el webview para que abra los enlaces internos dentro de la APP
        twitter.setWebViewClient(new WebViewClient());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuregresar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.back) {
            back();
        }
        return super.onOptionsItemSelected(item);
    }

    public void back(){
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}

