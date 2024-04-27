package com.example.findmybus;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class ViewReserved extends Activity implements OnItemClickListener,JsonResponse {

	ListView lv;
	SharedPreferences sh;
	String logid;
	public static String rids,amount,status;
	String[] rid,noofseats,name,fplace,tplace,ramount,rstatus,details,bus;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_reserved);
		
		
		lv=(ListView)findViewById(R.id.lvcreservation);
		lv.setOnItemClickListener(this);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)ViewReserved.this;
		String q="viewreservations/?logid="+logid; 
		jr.execute(q);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_reserved, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		rids=rid[arg2];
		amount=ramount[arg2];
		status=rstatus[arg2];
		
		if(status.equals("pending"))
		{
			final CharSequence[] items = {"Pay","Cancel"};
	
	        AlertDialog.Builder builder = new AlertDialog.Builder(ViewReserved.this);
	        builder.setTitle("Select Any!");
	        builder.setItems(items, new DialogInterface.OnClickListener() 
	        {
	            @Override
	            public void onClick(DialogInterface dialog, int item) {
	
	                if (items[item].equals("Pay")) 
	                {
	                    startActivity(new Intent(getApplicationContext(),Payment.class));
	
	                }
	                else if (items[item].equals("Cancel")) {
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
				
				
				
				for(int i=0;i<ja.length();i++)
				{
					rid[i]=ja.getJSONObject(i).getString("reservation_id");
					noofseats[i]=ja.getJSONObject(i).getString("no_of_seats");
					bus[i]=ja.getJSONObject(i).getString("bus_name");
					fplace[i]=ja.getJSONObject(i).getString("fplace");
					tplace[i]=ja.getJSONObject(i).getString("tplace");
					ramount[i]=ja.getJSONObject(i).getString("reservation_amount");
					rstatus[i]=ja.getJSONObject(i).getString("reservation_status");
//					
					details[i]="Bus : "+bus[i]+"\nFrom : "+fplace[i]+"\nTo : "+tplace[i]+"\nNo Of Seats : "+noofseats[i]+"\nAmount : "+ramount[i];
				}
				//driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
				lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext,details));
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
