package com.example.findmybus;

import org.json.JSONArray;
import org.json.JSONObject;

import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ViewOtherPlaces extends Activity implements JsonResponse, OnItemClickListener
{

	ListView lv;
	String[] opid,opname,optype,lati,longi,details;
	String logid,opids,latis,longis;
	SharedPreferences sh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_view_other_places);
		
		lv=(ListView)findViewById(R.id.lvnearbyplacess);
		lv.setOnItemClickListener(this);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)ViewOtherPlaces.this;
		String q="viewnearbyplaces/?lati="+LocationService.lati+"&longi="+LocationService.logi;
		jr.execute(q);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.view_other_places, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		
		try{
			String status=jo.getString("status");
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				opid=new String[ja.length()];
				opname= new String[ja.length()];
				optype= new String[ja.length()];
				lati=new String[ja.length()];
				longi= new String[ja.length()];
				
				details= new String[ja.length()];
				
				
				for(int i=0;i<ja.length();i++)
				{
					opid[i]=ja.getJSONObject(i).getString("other_place_id");
					opname[i]=ja.getJSONObject(i).getString("other_places_name");
					optype[i]=ja.getJSONObject(i).getString("other_places_type");
					lati[i]=ja.getJSONObject(i).getString("latitude");
					longi[i]=ja.getJSONObject(i).getString("longitude");
					
//					
					details[i]="Place : "+opname[i]+"\nType : "+optype[i];
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

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		opids=opid[arg2];
		latis=lati[arg2];
		longis=longi[arg2];
		
		String url1 = "http://www.google.com/maps?saddr="+LocationService.lati+""+","+LocationService.logi+""+"&&daddr="+latis+","+longis;
        Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
        startActivity(in);
	}

}
