package com.example.findmybussss;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.SharedPreferences;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class BusViewReservation extends Activity implements JsonResponse, OnItemClickListener {

	ListView lv;
	SharedPreferences sh;
	String logid;
	String rids;
	EditText e1;
	Spinner s1,s2;
	
	String[] rid,noofseats,name,fplace,tplace,ramount,rstatus,details;
	
	String bdate;
	
	 private DatePickerDialog fromDatePickerDialog;

	    private SimpleDateFormat dateFormatter;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_view_reservation);
		
		lv=(ListView)findViewById(R.id.lvreservation);
		e1=(EditText)findViewById(R.id.etsdate);
		e1=(EditText)findViewById(R.id.etsdate);

		lv.setOnItemClickListener(this);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		logid=sh.getString("logid", "");
		
		e1.setInputType(InputType.TYPE_NULL);
		e1.requestFocus();
		setDateTimeField();
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		
		
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)BusViewReservation.this;
		String q="viewreservation/?logid="+logid; 
		jr.execute(q);
		
	}
	

private void setDateTimeField() {
		
		e1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				fromDatePickerDialog.show();
			}
		});
		Calendar newCalendar = Calendar.getInstance();
		fromDatePickerDialog =new DatePickerDialog(this,new OnDateSetListener() {
			
			public void onDateSet(DatePicker view,int year, int monthOfYear, int dayOfMonth) {
				// TODO Auto-generated method stub
				Calendar newDate = Calendar.getInstance();
	            newDate.set(year, monthOfYear, dayOfMonth);
	            e1.setText(dateFormatter.format(newDate.getTime()));	
	            bdate=e1.getText().toString();
	            
	            JsonReq jr= new JsonReq();
	    		jr.json_response=(JsonResponse)BusViewReservation.this;
	    		String q="viewreservationb/?logid="+logid+"&date="+bdate; 
	    		jr.execute(q);
			}
		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
		
	}





	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		

		try{
			String status=jo.getString("status");
			Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
			if(status.equalsIgnoreCase("success"))
			{
				JSONArray ja=(JSONArray)jo.getJSONArray("data");
				rid=new String[ja.length()];
				noofseats= new String[ja.length()];
				name=new String[ja.length()];
				fplace= new String[ja.length()];
				tplace=new String[ja.length()];
				ramount= new String[ja.length()];
				rstatus= new String[ja.length()];
				details= new String[ja.length()];
				
				
				for(int i=0;i<ja.length();i++)
				{
					rid[i]=ja.getJSONObject(i).getString("reservation_id");
					noofseats[i]=ja.getJSONObject(i).getString("no_of_seats");
					name[i]=ja.getJSONObject(i).getString("name");
					fplace[i]=ja.getJSONObject(i).getString("fplace");
					tplace[i]=ja.getJSONObject(i).getString("tplace");
					ramount[i]=ja.getJSONObject(i).getString("reservation_amount");
					rstatus[i]=ja.getJSONObject(i).getString("reservation_status");
//					
					details[i]="Name : "+name[i]+"\nFrom : "+fplace[i]+"\nTo : "+tplace[i]+"\nNo Of Seats : "+noofseats[i]+"\nAmount : "+ramount[i]+"\nStatus : "+rstatus[i];
//					Toast.makeText(getApplicationContext(), details[i], Toast.LENGTH_LONG).show();
				}
				//driver_list.setAdapter(new ArrayAdapter<String>(getApplicationContext(),R.layout.spin,ConfirmRide.name_s));
				lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(), R.layout.custlistviewapp,details));
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
		rids=rid[arg2];
	}

}
