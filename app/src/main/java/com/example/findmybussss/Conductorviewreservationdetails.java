package com.example.findmybussss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Conductorviewreservationdetails extends AppCompatActivity implements JsonResponse {

    ListView lv;
    SharedPreferences sh;
    String logid;
    String rids;
    EditText e1;

    String[] rid,noofseats,name,fplace,tplace,ramount,rstatus,details;

    String bdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conductorviewreservationdetails);
        lv=(ListView)findViewById(R.id.lvcreservation);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse)Conductorviewreservationdetails.this;
        String q="viewreservationsss/?rid="+AndroidBarcodeQrExample.vals;
        jr.execute(q);

    }

    @Override
    public void response(JSONObject jo) {

        try{
            String status=jo.getString("status");
            Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
            if(status.equalsIgnoreCase("success"))
            {
                JSONArray ja=(JSONArray)jo.getJSONArray("data");
                rid=new String[ja.length()];
                noofseats= new String[ja.length()];
                name=new String[ja.length()];
                fplace= new String[ja.length()];
                tplace=new String[ja.length()];
                ramount= new String[ja.length()];
//                rstatus= new String[ja.length()];
                details= new String[ja.length()];


                for(int i=0;i<ja.length();i++)
                {
                    rid[i]=ja.getJSONObject(i).getString("reservation_id");
                    noofseats[i]=ja.getJSONObject(i).getString("no_of_seats");
                    name[i]=ja.getJSONObject(i).getString("name");
                    fplace[i]=ja.getJSONObject(i).getString("fplace");
                    tplace[i]=ja.getJSONObject(i).getString("tplace");
                    ramount[i]=ja.getJSONObject(i).getString("reservation_amount");
//                    rstatus[i]=ja.getJSONObject(i).getString("reservation_status");
//
                    details[i]="Name : "+name[i]+"\nFrom : "+fplace[i]+"\nTo : "+tplace[i]+"\nNo Of Seats : "+noofseats[i]+"\nAmount : "+ramount[i];
//					Toast.makeText(getApplicationContext(), details[i], Toast.LENGTH_LONG).show();
                }
                //driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
                lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.custtext,details));
            }
            else
            {
                Toast.makeText(getApplicationContext(), "No Data", Toast.LENGTH_LONG).show();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "haii"+e, Toast.LENGTH_LONG).show();
        }
    }
}