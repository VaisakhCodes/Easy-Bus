package com.example.findmybussss;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.PreciseDataConnectionState;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONObject;

public class BusHome extends Activity implements JsonResponse {

	Button b1,b2,b3,b4,b5,b6;


	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;
	SharedPreferences sh;
	public static String check;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_home);
		sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



		//////////////////////////////////////////////////////////////////
		// ShakeDetector initialization
		mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
		mAccelerometer = mSensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		mShakeDetector = new ShakeDetector();

		mShakeDetector.setOnShakeListener(new ShakeDetector.OnShakeListener() {

			public void onShake(int count) {
				// TODO Auto-generated method stub

				if(count>3)
				{
					Toast.makeText(getApplicationContext(), "Accident Detected"+count, Toast.LENGTH_LONG).show();
					JsonReq jr= new JsonReq();
					jr.json_response=(JsonResponse)BusHome.this;
					String q="/accidentdetect?lattitude="+LocationService.lati+"&longitude="+LocationService.logi+"&log_id=" + Login.logid;
					jr.execute(q);
					//Toast.makeText(getApplicationContext(), "value"+q, Toast.LENGTH_LONG).show();

				}
			}
		});

		///////////////////////////////////////////////		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		check=sh.getString("checkb", "");
		
		b1=(Button)findViewById(R.id.btviewtrip);
		b2=(Button)findViewById(R.id.btreservation);
		b3=(Button)findViewById(R.id.btlogout);
		b4=(Button)findViewById(R.id.btstart);
		b5=(Button)findViewById(R.id.btscanqr);
		b6=(Button)findViewById(R.id.btsendnoti);

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
		b5.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),AndroidBarcodeQrExample.class));

			}
		});

		b6.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				startActivity(new Intent(getApplicationContext(),Sendnotification.class));

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
	public void onResume() {
		super.onResume();
		// Add the following line to register the Session Manager Listener onResume
		mSensorManager.registerListener(mShakeDetector, mAccelerometer,	SensorManager.SENSOR_DELAY_UI);
	}

	@Override
	public void onPause() {
		// Add the following line to unregister the Sensor Manager onPause
		mSensorManager.unregisterListener(mShakeDetector);
		super.onPause();
	}


	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		try {
			String status=jo.getString("status");
			Log.d("pearl",status);
			if(status.equalsIgnoreCase("success")){
				Toast.makeText(getApplicationContext(), "Accident Detect", Toast.LENGTH_LONG).show();
//					 startActivity(new Intent(getApplicationContext(),Userfeedback.class));
			}
			else
			{
//					startActivity(new Intent(getApplicationContext(),User_Regsiter.class));
				Toast.makeText(getApplicationContext(), " failed.TRY AGAIN!!", Toast.LENGTH_LONG).show();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
	}
	
	 public void onBackPressed() 
		{
			// TODO Auto-generated method stub
			super.onBackPressed();
			Intent b=new Intent(getApplicationContext(),BusHome.class);			
			startActivity(b);
		}

}
