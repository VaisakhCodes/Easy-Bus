package com.example.findmybussss;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

public class CustomerViewReserveds extends AppCompatActivity implements AdapterView.OnItemClickListener,JsonResponse {

    ListView lv;
    SharedPreferences sh;
    String logid;
    public static String rids,amount,status,qrss;
    String[] rid,noofseats,name,fplace,tplace,ramount,rstatus,details,bus,qrs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_view_reserveds);

        lv=(ListView)findViewById(R.id.lvcreservation);
        lv.setOnItemClickListener(this);
        sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        logid=sh.getString("logid", "");

        JsonReq jr= new JsonReq();
        jr.json_response=(JsonResponse)CustomerViewReserveds.this;
        String q="viewreservations/?logid="+logid;
        jr.execute(q);

    }

    @Override
    public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        // TODO Auto-generated method stub
        rids=rid[arg2];
        amount=ramount[arg2];
        status=rstatus[arg2];
        qrss=qrs[arg2];

        SharedPreferences.Editor e=sh.edit();
        e.putString("qr",qrss);
        e.putString("rstatus",status);
        e.putString("ramount",amount);
        e.putString("rids",rids);
        e.commit();


        if(status.equals("pending"))
        {
            final CharSequence[] items = {"Pay","View QR","Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(CustomerViewReserveds.this);
            builder.setTitle("Select Any!");
            builder.setItems(items, new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Pay"))
                    {
                        startActivity(new Intent(getApplicationContext(),Payment.class));

                    }
                    else if (items[item].equals("View QR"))
                    {
                        startActivity(new Intent(getApplicationContext(),ViewQRss.class));

                    }
                    else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
        else {
            final CharSequence[] items = {"View QR", "Cancel"};

            AlertDialog.Builder builder = new AlertDialog.Builder(CustomerViewReserveds.this);
            builder.setTitle("Select Any!");
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int item) {

                    if (items[item].equals("Pay")) {
                        startActivity(new Intent(getApplicationContext(), Payment.class));

                    } else if (items[item].equals("View QR")) {
                        startActivity(new Intent(getApplicationContext(), ViewQRss.class));

                    } else if (items[item].equals("Cancel")) {
                        dialog.dismiss();
                    }
                }
            });
            builder.show();
        }
    }

    @Override
    public void response(JSONObject jo) {
        // TODO Auto-generated method stub


        try{
            String status=jo.getString("status");
            if(status.equalsIgnoreCase("success"))
            {
                JSONArray ja=(JSONArray)jo.getJSONArray("data");
                rid=new String[ja.length()];
                noofseats= new String[ja.length()];
                bus=new String[ja.length()];
                fplace= new String[ja.length()];
                tplace=new String[ja.length()];
                ramount= new String[ja.length()];
                rstatus= new String[ja.length()];
                details= new String[ja.length()];
                qrs= new String[ja.length()];



                for(int i=0;i<ja.length();i++)
                {
                    rid[i]=ja.getJSONObject(i).getString("reservation_id");
                    noofseats[i]=ja.getJSONObject(i).getString("no_of_seats");
                    bus[i]=ja.getJSONObject(i).getString("bus_name");
                    fplace[i]=ja.getJSONObject(i).getString("fplace");
                    tplace[i]=ja.getJSONObject(i).getString("tplace");
                    ramount[i]=ja.getJSONObject(i).getString("reservation_amount");
                    rstatus[i]=ja.getJSONObject(i).getString("reservation_status");
                    qrs[i]=ja.getJSONObject(i).getString("qrcode");
//
                    details[i]="Bus : "+bus[i]+"\nFrom : "+fplace[i]+"\nTo : "+tplace[i]+"\nNo Of Seats : "+noofseats[i]+"\nAmount : "+ramount[i];
                }
                //driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
                lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(),  R.layout.cust_list,details));
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