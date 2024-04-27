package com.example.findmybussss;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class Viewnotification extends AppCompatActivity implements JsonResponse{
    ListView l1;
    String[] notification_id,notification_desc,date,conductor_id,value;

    SharedPreferences sh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewnotification);

        l1=(ListView) findViewById(R.id.notifview);

        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse)Viewnotification.this;
        String q="viewnenotificati/?id=" + sh.getString("logid","");
        jr.execute(q);

    }

    @Override
    public void response(JSONObject jo) {
        try{
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success"))
            {
                JSONArray ja=(JSONArray)jo.getJSONArray("data");
                notification_id=new String[ja.length()];
                notification_desc= new String[ja.length()];
                date=new String[ja.length()];
                conductor_id= new String[ja.length()];
                value=new String[ja.length()];



                for(int i=0;i<ja.length();i++)
                {
                    notification_id[i]=ja.getJSONObject(i).getString("notification_id");
                    notification_desc[i]=ja.getJSONObject(i).getString("notification_desc");
                    date[i]=ja.getJSONObject(i).getString("date");
                    conductor_id[i]=ja.getJSONObject(i).getString("conductor_id");

                    value[i]="Notification : "+notification_desc[i]+"\nDate : "+date[i];
                }
                //driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
                l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext,value));
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