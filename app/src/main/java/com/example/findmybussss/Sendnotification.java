package com.example.findmybussss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class Sendnotification extends AppCompatActivity implements  JsonResponse{
    EditText e1;
    Button b1;

    SharedPreferences sh;
    String notification;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sendnotification);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        e1=(EditText) findViewById(R.id.editTextTextPersonName);

        b1=(Button) findViewById(R.id.button);

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                notification=e1.getText().toString();
                if (notification.equalsIgnoreCase("")){
                    e1.setError("ENTER THE NOTIFICATION");
                    e1.setFocusable(true);
                }else {
                    JsonReq JR= new JsonReq();
                    JR.json_response=(JsonResponse)Sendnotification.this;
                    String q="notification/?notification=" + notification + "&conductor_id=" + sh.getString("logid","") ;
                    JR.execute(q);
                }
            }
        });


    }

    @Override
    public void response(JSONObject jo) {
        try {
            String status = jo.getString("status");
            Log.d("method",status);

            if (status.equalsIgnoreCase("success")){
                Toast.makeText(this, "Notification send success", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),BusHome.class));
            }


        }

        catch (JSONException e) {
            e.printStackTrace();
        }
    }
}