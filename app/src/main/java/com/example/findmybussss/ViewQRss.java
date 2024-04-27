package com.example.findmybussss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class ViewQRss extends AppCompatActivity {

    ImageView iv;
    SharedPreferences sh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_q_rss);
        iv=(ImageView)findViewById(R.id.imageView);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


        String pth = "http://"+sh.getString("ip", "")+"/"+sh.getString("qr","");
        pth = pth.replace("~", "");
        Toast.makeText(getApplicationContext(), pth, Toast.LENGTH_LONG).show();

        Log.d("-------------", pth);
        Picasso.with(getApplicationContext())
                .load(pth)
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background).into(iv);

    }
}