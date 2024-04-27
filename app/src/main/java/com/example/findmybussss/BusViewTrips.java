package com.example.findmybussss;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class BusViewTrips extends Activity implements JsonResponse {

	ListView lv;
	String[] bus,route,splace,eplace,stime,etime,details;
	String logid;
	SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_view_trips);
		
		lv=(ListView)findViewById(R.id.lvviewtrips);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)BusViewTrips.this;
		String q="viewtrips/?logid="+logid; 
		jr.execute(q);
		
	}


	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try{
			String status=jo.getString("status");
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				bus=new String[ja.length()];
				route= new String[ja.length()];
				splace=new String[ja.length()];
				eplace= new String[ja.length()];
				stime=new String[ja.length()];
				etime= new String[ja.length()];
				details= new String[ja.length()];
				
				
				for(int i=0;i<ja.length();i++)
				{
					bus[i]=ja.getJSONObject(i).getString("bus_name");
					route[i]=ja.getJSONObject(i).getString("route_name");
					splace[i]=ja.getJSONObject(i).getString("splace");
					eplace[i]=ja.getJSONObject(i).getString("eplace");
					stime[i]=ja.getJSONObject(i).getString("stime");
					etime[i]=ja.getJSONObject(i).getString("etime");
//					
					details[i]="Bus : "+bus[i]+"\nRoute : "+route[i]+"\nStart Place : "+splace[i]+"\nEnd Place : "+eplace[i]+"\nStart Time : "+stime[i]+"\nEnd Time: "+etime[i];
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
