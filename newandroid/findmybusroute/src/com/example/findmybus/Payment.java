package com.example.findmybus;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Payment extends Activity implements JsonResponse
{

	
	EditText e1;
	Button b1;
	ListView l1;
	String logid;
	SharedPreferences sh;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		
		e1=(EditText)findViewById(R.id.etamount);
		b1=(Button)findViewById(R.id.btpay);
		
		e1.setText(ViewReserved.amount);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse) Payment.this;
				String q="addpay/?rid="+ViewReserved.rids+"&amount="+ViewReserved.amount;
				q.replace("", "%20");
				jr.execute(q);
			}
		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.payment, menu);
		return true;
	}

	@Override
	public void response(JSONObject jo) {
		// TODO Auto-generated method stub
		
		try
		{
			String status=jo.getString("status");
			Toast.makeText(getApplicationContext(), status, Toast.LENGTH_LONG).show();
			if(status.equalsIgnoreCase("success"))
			{
				Toast.makeText(getApplicationContext(), "Payed", Toast.LENGTH_LONG).show();
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

}
