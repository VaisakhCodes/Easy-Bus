package com.example.findmybus;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class BusHome extends Activity {

	Button b1,b2,b3,b4;
	SharedPreferences sh;
	public static String check;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_home);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		check=sh.getString("checkb", "");
		
		b1=(Button)findViewById(R.id.btviewtrip);
		b2=(Button)findViewById(R.id.btreservation);
		b3=(Button)findViewById(R.id.btlogout);
		b4=(Button)findViewById(R.id.btstart);
		Toast.makeText(getApplicationContext(), "check"+check, Toast.LENGTH_LONG).show();
		if(check.equals("True"))
		{
			b4.setText("End Trip");
		}
		else
		{
			b4.setText("Start Trip");
		}
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),BusViewTrips.class));
				
			}
		});
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),BusViewReservation.class));
				
			}
		});
		
		b3.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				startActivity(new Intent(getApplicationContext(),Login.class));
				
			}
		});
		
		b4.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(check.equals("True"))
				{
					SharedPreferences.Editor ed = sh.edit();
	                ed.putString("checkb", "False");
	                ed.commit();
	                Login.checkb="False";
				}
				else
				{
					SharedPreferences.Editor ed = sh.edit();
	                ed.putString("checkb", "True");
	                ed.commit();
	                Login.checkb="True";
				}
				startActivity(new Intent(getApplicationContext(),BusHome.class));
				
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.bus_home, menu);
		return true;
	}
	
	 public void onBackPressed() 
		{
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent b=new Intent(getApplicationContext(),BusHome.class);			
			startActivity(b);
		}

}
