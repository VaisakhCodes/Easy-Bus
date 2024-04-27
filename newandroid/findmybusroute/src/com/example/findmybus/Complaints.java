package com.example.findmybus;

import org.json.JSONArray;
import org.json.JSONObject;


import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Complaints extends Activity implements JsonResponse {

	EditText e1;
	Button b1;
	ListView l1;
	String complaint,logid;
	String[] complaints,replys,date,details;
	SharedPreferences sh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_complaints);
		
		e1=(EditText)findViewById(R.id.etcomplaint);
		b1=(Button)findViewById(R.id.btcomplaint);
		l1=(ListView)findViewById(R.id.lvcomplaint);
		
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse) Complaints.this;
		String q="viewcomplaints/?logid="+logid;
		q.replace("", "%20");
		jr.execute(q);
		
		
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				complaint=e1.getText().toString();
				
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse) Complaints.this;
				String q="complaints/?complaint="+complaint+"&logid="+logid;
				q.replace("", "%20");
				jr.execute(q);
				
				
			}
		});
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try
		{
			String method=jo.getString("method");
			Toast.makeText(getApplicationContext(), method, Toast.LENGTH_LONG).show();
			if(method.equalsIgnoreCase("complaints"))
			{
				try
				{
					String status=jo.getString("status");
					Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
					if(status.equalsIgnoreCase("success"))
					{
						Toast.makeText(getApplicationContext(), "Complaints Added", Toast.LENGTH_LONG).show();
						startActivity(new Intent(getApplicationContext(),Complaints.class));
					}
					
					else
					{
						Toast.makeText(getApplicationContext(), "Not Successfull", Toast.LENGTH_LONG).show();
					}
				
				}
				catch(Exception e)
				{
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "Hai"+e, Toast.LENGTH_LONG).show();
				}
			}
			else if(method.equalsIgnoreCase("viewcomplaints"))
			{
				try{
					String status=jo.getString("status");
					if(status.equalsIgnoreCase("success"))
					{
						JSONArray ja=(JSONArray)jo.getJSONArray("data");
						complaints=new String[ja.length()];
						replys= new String[ja.length()];
						date= new String[ja.length()];
						details= new String[ja.length()];
						
						
						for(int i=0;i<ja.length();i++)
						{
							complaints[i]=ja.getJSONObject(i).getString("description");
							
//							Toast.makeText(getApplicationContext(), ja.getJSONObject(i).getString("replay"), Toast.LENGTH_LONG).show();
							if(ja.getJSONObject(i).getString("reply").equals("NA"))
							{
								replys[i]="Not Replyed";
							}
							else
							{
								replys[i]=ja.getJSONObject(i).getString("reply");
							}
							
							date[i]=ja.getJSONObject(i).getString("complaint_date");
							details[i]="Complaint : "+complaints[i]+"\nReply : "+replys[i]+"\nDate : "+date[i];
						}
						//driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
						l1.setAdapter(new ArrayAdapter<String>(getApplicationContext(),  R.layout.custtext,details));
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
		catch(Exception e)
		{
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), "Hai"+e, Toast.LENGTH_LONG).show();
		}
		
		
	}
	
	public void onBackPressed() 
	{
		// TODO Auto-generated method stub
		super.onBackPressed();
		Intent b=new Intent(getApplicationContext(),CustomerHome.class);			
		startActivity(b);
	}

}
