package com.example.findmybus;

import org.json.JSONArray;
import org.json.JSONObject;



import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class SearchRoutes extends Activity implements OnItemSelectedListener, OnItemClickListener,JsonResponse {

	ListView lv;
	Spinner s1,s2;
	String[] splaceid,splace,eplaceid,eplace,busid,busname,regno,seat,tripid,route_name,details,fare,lattitude,longitude,stime,etime;
	public static String splaceids,eplaceids,tripids,splaces,eplaces,route,bus,busids,seats,fares,lati,longi,stimes,etimes;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_routes);
		
		s1=(Spinner) findViewById(R.id.spsplace);
		s1.setOnItemSelectedListener(this);
		s2=(Spinner)findViewById(R.id.speplace);
		s2.setOnItemSelectedListener(this);
		
		lv=(ListView)findViewById(R.id.lvsroutes);
		lv.setOnItemClickListener(this);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)SearchRoutes.this;
		String q="viewspstart/";
		jr.execute(q);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.search_routes, menu);
		return true;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		
		
		tripids=tripid[arg2];
		route=route_name[arg2];
		bus=busname[arg2];
		busids=busid[arg2];
		seats=seat[arg2];
		Toast.makeText(getApplicationContext(), seats, Toast.LENGTH_LONG).show();
//		fares=fare[arg2];
//		lati=lattitude[arg2];
//		longi=longitude[arg2];
		
		
		final CharSequence[] items = {"Reserve Seats", "View Reserved", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(SearchRoutes.this);
        builder.setTitle("Select Any!");
        builder.setItems(items, new DialogInterface.OnClickListener() 
        {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Reserve Seats")) 
                {
                    startActivity(new Intent(getApplicationContext(),ReserveSeat.class));

                } else if (items[item].equals("View Reserved")) {
                   
                	startActivity(new Intent(getApplicationContext(),ViewReserved.class));

                } 
//                else if (items[item].equals("Location")) 
//                {
//                    
//                	String url = "http://www.google.com/maps?q="+lati+","+longi;
//                    Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
//                    startActivity(in);
//            		
//                }
                 else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
        
        
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		
		if(arg0==s1)
		{
			splaceids=splaceid[arg2];
			splaces=splace[arg2];
			
			JsonReq jr= new JsonReq();
			jr.json_response=(JsonResponse)SearchRoutes.this;
			String q="viewspend/?splace="+splaceids;
			jr.execute(q);
		}
		else if(arg0==s2)
		{
			eplaceids=eplaceid[arg2];
			eplaces=eplace[arg2];
			JsonReq jr= new JsonReq();
			jr.json_response=(JsonResponse)SearchRoutes.this;
			String q="viewroutes/?splace="+splaceids+"&eplace="+eplaceids;
			jr.execute(q);
		}
		
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try{
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("viewspstart"))
			{
				try{
					String status=jo.getString("status");
					if(status.equalsIgnoreCase("success"))
					{
						JSONArray ja=(JSONArray)jo.getJSONArray("data");
						splaceid=new String[ja.length()];
						splace= new String[ja.length()];
						
						
						for(int i=0;i<ja.length();i++)
						{
							splaceid[i]=ja.getJSONObject(i).getString("place_id");
							splace[i]=ja.getJSONObject(i).getString("place_name");
							
						}
						//driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
						s1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext,splace));
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
			else if(method.equalsIgnoreCase("viewspend"))
			{
				try{
					String status=jo.getString("status");
					if(status.equalsIgnoreCase("success"))
					{
						JSONArray ja=(JSONArray)jo.getJSONArray("data");
						eplaceid=new String[ja.length()];
						eplace= new String[ja.length()];
						
						
						for(int i=0;i<ja.length();i++)
						{
							eplaceid[i]=ja.getJSONObject(i).getString("place_id");
							eplace[i]=ja.getJSONObject(i).getString("place_name");
							
						}
						//driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
						s2.setAdapter(new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext,eplace));
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
			
			
			
			else if(method.equalsIgnoreCase("viewroutes"))
			{
				try{
					String status=jo.getString("status");
					if(status.equalsIgnoreCase("success"))
					{
						JSONArray ja=(JSONArray)jo.getJSONArray("data");
						busid=new String[ja.length()];
						busname= new String[ja.length()];
						regno=new String[ja.length()];
						seat= new String[ja.length()];
						tripid=new String[ja.length()];
						route_name= new String[ja.length()];
						fare= new String[ja.length()];
//						lattitude= new String[ja.length()];
//						longitude= new String[ja.length()];
						stime= new String[ja.length()];
						etime= new String[ja.length()];
						details= new String[ja.length()];
						
						
						for(int i=0;i<ja.length();i++)
						{
							busid[i]=ja.getJSONObject(i).getString("bus_id");
							busname[i]=ja.getJSONObject(i).getString("bus_name");
							regno[i]=ja.getJSONObject(i).getString("reg_number");
							seat[i]=ja.getJSONObject(i).getString("noofseats");
							
							tripid[i]=ja.getJSONObject(i).getString("trip_id");
//							fare[i]=ja.getJSONObject(i).getString("fare");
							route_name[i]=ja.getJSONObject(i).getString("route_name");
//							lattitude[i]=ja.getJSONObject(i).getString("latitude");
//							longitude[i]=ja.getJSONObject(i).getString("longitude");
							stime[i]=ja.getJSONObject(i).getString("stime");
							etime[i]=ja.getJSONObject(i).getString("etime");
							
							details[i]="Bus : "+busname[i]+"\nRegno : "+regno[i]+"\nRoute : "+route_name[i]+"\nStart Time : "+stime[i]+"\nEnd Time : "+etime[i];
							
							
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
			
		}catch(Exception e){
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "haii"+e, Toast.LENGTH_LONG).show();
		}
	}

}
