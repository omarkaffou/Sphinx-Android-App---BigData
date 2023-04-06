package com.example.sphinxpfa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity {
    private Button started;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle(Html.fromHtml("<font color=\"#FFC300\">" + getString(R.string.app_name) + "</font>"));
        started = (Button) findViewById(R.id.started);
        started.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opencamera();
            }
        });
    }
    public void opencamera() {
        Intent intent = new Intent(this, Acceuil.class);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return true;
    }
}
