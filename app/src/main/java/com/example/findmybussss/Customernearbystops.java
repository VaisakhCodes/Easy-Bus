package com.example.findmybussss;

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

public class Customernearbystops extends Activity implements JsonResponse, OnItemClickListener {

	ListView lv;
	String[] pid,pname,lati,longi,details;
	String logid;
	SharedPreferences sh;
	String latis,logis;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_customernearbystops);

		lv=(ListView)findViewById(R.id.lvnearbystops);
		lv.setOnItemClickListener(this);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");


		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)Customernearbystops.this;
		String q="viewnearbystops/?lati="+LocationService.lati+"&longi="+LocationService.logi;
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
				pid=new String[ja.length()];
				pname= new String[ja.length()];
				lati=new String[ja.length()];
				longi= new String[ja.length()];

				details= new String[ja.length()];


				for(int i=0;i<ja.length();i++)
				{
					pid[i]=ja.getJSONObject(i).getString("place_id");
					pname[i]=ja.getJSONObject(i).getString("place_name");
					lati[i]=ja.getJSONObject(i).getString("latitude");
					longi[i]=ja.getJSONObject(i).getString("longitude");

//
					details[i]="Place : "+pname[i];
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

		latis=lati[arg2];
		logis=longi[arg2];
		String url1 = "http://www.google.com/maps?saddr="+LocationService.lati+""+","+LocationService.logi+""+"&&daddr="+latis+","+logis;
		Intent in = new Intent(Intent.ACTION_VIEW, Uri.parse(url1));
		startActivity(in);
	}

}