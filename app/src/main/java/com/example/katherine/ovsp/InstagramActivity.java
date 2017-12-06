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

public class InstagramActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instagram);



        WebView facebook = (WebView) findViewById(R.id.web_instagram);
        //Activamos el Javascript
        WebSettings webSettings = facebook.getSettings();
        webSettings.setJavaScriptEnabled(true);

        //Url que carga la app(webview)
        facebook.loadUrl("https://www.instagram.com/serviciogeologicocolombiano/");

        //Forzazr el webview para que abra los enlaces internos dentro de la APP
        facebook.setWebViewClient(new WebViewClient());
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

