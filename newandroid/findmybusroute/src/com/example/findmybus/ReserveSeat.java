package com.example.findmybus;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONObject;

import android.R.integer;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class ReserveSeat extends Activity implements JsonResponse
{

	Button b2;
	EditText e1,e2,e3,e4,e5,e6;
	Integer seats;
	Integer totals;
	SharedPreferences sh;
	String lid;
	String bdate;
	
	 private DatePickerDialog fromDatePickerDialog;

	    private SimpleDateFormat dateFormatter;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reserve_seat);
		
		e1=(EditText)findViewById(R.id.etbus);
		e2=(EditText)findViewById(R.id.etdate);
		e3=(EditText)findViewById(R.id.etaseats);
		e4=(EditText)findViewById(R.id.etfare);
		e5=(EditText)findViewById(R.id.ettseat);
		e6=(EditText)findViewById(R.id.ettotal);
		
		b2=(Button)findViewById(R.id.btreserve);
		
		e1.setText(SearchRoutes.bus);
		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		lid=sh.getString("logid", "");
		
		e2.setInputType(InputType.TYPE_NULL);
		e2.requestFocus();
		setDateTimeField();
		dateFormatter = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		
		JsonReq jr= new JsonReq();
		jr.json_response=(JsonResponse)ReserveSeat.this;
		String q="viewfares/?fplace="+SearchRoutes.splaceids+"&tplace="+SearchRoutes.eplaceids+"&tid="+SearchRoutes.tripids;
		jr.execute(q);
	
		
		b2.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				final CharSequence[] items = {"Reserve", "Cancel"};

		        AlertDialog.Builder builder = new AlertDialog.Builder(ReserveSeat.this);
		        builder.setTitle("Your have book a Bus "+SearchRoutes.bus+"\nNo of ");
		        builder.setItems(items, new DialogInterface.OnClickListener() 
		        {
		            @Override
		            public void onClick(DialogInterface dialog, int item) {

		                if (items[item].equals("Reserve")) 
		                {
		                	JsonReq jr= new JsonReq();
		    				jr.json_response=(JsonResponse)ReserveSeat.this;
		    				String q="addtoreservation/?noofseats="+e5.getText().toString().trim()+"&lid="+lid+"&tid="+SearchRoutes.tripids+"&fromid="+SearchRoutes.splaceids+"&toid="+SearchRoutes.eplaceids+"&total="+e6.getText().toString().trim()+"&date="+e2.getText().toString();
		    				jr.execute(q);

		                }
		                 else if (items[item].equals("Cancel")) {
		                    dialog.dismiss();
		                }
		            }
		        });
		        builder.show();
				
				
				
				
				
			}
		});
		
		e5.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
				// TODO Auto-generated method stub
				String count = e5.getText().toString().trim();
				
				if (count.length() > 0){
					if(Integer.parseInt(count)<= Integer.parseInt(e3.getText().toString()))
					{
						int r=Integer.parseInt(e4.getText().toString().trim());
						int q=Integer.parseInt(count);
						int tot = r*q;
						e6.setText(tot+"");
						b2.setEnabled(true);
					}
					else
					{
						e5.setError("Only "+e3.getText().toString()+" left");
						b2.setEnabled(false);
					}
				}
			}
		});
		
	}



private void setDateTimeField() {
		
		e2.setOnClickListener(new View.OnClickListener() {
			
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
	            e2.setText(dateFormatter.format(newDate.getTime()));	
	            bdate=e2.getText().toString();
	            
	            JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse)ReserveSeat.this;
				String q="viewseat/?tid="+SearchRoutes.tripids+"&bid="+SearchRoutes.busids+"&date="+e2.getText().toString();
				jr.execute(q);
			}
		},newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.reserve_seat, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try{
			String method=jo.getString("method");
			if(method.equalsIgnoreCase("viewseat"))
			{
			try{
				String status=jo.getString("status");
				if(status.equalsIgnoreCase("success"))
				{
					JSONArray ja=(JSONArray)jo.getJSONArray("data");
//					Toast.makeText(getApplicationContext(),"Haiisss" +ja.getJSONObject(0).getString("total") , Toast.LENGTH_LONG).show();
//					Toast.makeText(getApplicationContext(),"Haiisssss" + SearchRoutes.seats, Toast.LENGTH_LONG).show();
					
					if(ja.getJSONObject(0).getString("total").equals("null"))
					{
						seats=0;
					}
					else
					{
						seats=Integer.parseInt(ja.getJSONObject(0).getString("total"));
					}
					
					totals=Integer.parseInt(SearchRoutes.seats)-seats;
					e3.setText(totals+"");
					
					
				}
						
			}
			catch(Exception e){
				e.printStackTrace();
				Toast.makeText(getApplicationContext(), "haii"+e, Toast.LENGTH_LONG).show();
			}
			}
		
			else if(method.equalsIgnoreCase("addtoreservation"))
			{
				try
				{
					String status=jo.getString("status");
					Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
					if(status.equalsIgnoreCase("success"))
					{
						Toast.makeText(getApplicationContext(), "Reserved", Toast.LENGTH_LONG).show();
						startActivity(new Intent(getApplicationContext(),CustomerHome.class));
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
			if(method.equalsIgnoreCase("viewfares"))
			{
				try{
					String status=jo.getString("status");
					if(status.equalsIgnoreCase("success"))
					{
						Toast.makeText(getApplicationContext(),jo.getString("rate"), Toast.LENGTH_LONG).show();
						e4.setText(jo.getString("rate"));
						e4.setEnabled(false);
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

}
