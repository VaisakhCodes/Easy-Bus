package com.example.findmybussss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class OtherPlacesss extends AppCompatActivity {

    Button b1,b2,b3,b4,b5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_placesss);

        b1=(Button)findViewById(R.id.button10 );
        b2=(Button)findViewById(R.id.button11 );
        b3=(Button)findViewById(R.id.button12 );
        b4=(Button)findViewById(R.id.button13 );
        b5=(Button)findViewById(R.id.button14 );


        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri ="http://www.google.com/maps/search/hospitals/@" + LocationService.lati + "," + LocationService.logi;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
            }
        });
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri ="http://www.google.com/maps/search/police station/@" + LocationService.lati + "," + LocationService.logi;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
            }
        });
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri ="http://www.google.com/maps/search/bus stop/@" + LocationService.lati + "," + LocationService.logi;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
            }
        });
        b4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri ="http://www.google.com/maps/search/ATM/@" + LocationService.lati + "," + LocationService.logi;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
            }
        });
        b5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uri ="http://www.google.com/maps/search/pump/@" + LocationService.lati + "," + LocationService.logi;
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(uri)));
            }
        });
    }
}