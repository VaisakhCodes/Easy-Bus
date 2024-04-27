package com.example.findmybus;


import org.json.JSONObject;

import android.R.string;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class Registration extends Activity implements JsonResponse
{
	
	EditText e1,e2,e3,e4,e5,e6,e7,e8,e9;
	Button b1;
    String firstname;
	String lastname;
	String gender,age;
	String contact;
	String email,username,password;
	RadioButton r1,r2;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		
		e1=(EditText)findViewById(R.id.etfirstname);
		e2=(EditText)findViewById(R.id.etlastname);
		e3=(EditText)findViewById(R.id.etage);
		e4=(EditText)findViewById(R.id.etemail);
		e5=(EditText)findViewById(R.id.etphone);
		e6=(EditText)findViewById(R.id.etusername);
		e7=(EditText)findViewById(R.id.etpassword);
		r1=(RadioButton)findViewById(R.id.radio0);
		r2=(RadioButton)findViewById(R.id.radio1);
		
		b1=(Button)findViewById(R.id.btsubmit);
		b1.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				firstname=e1.getText().toString();
				lastname=e2.getText().toString();
				age=e3.getText().toString();
				email=e4.getText().toString();
				contact=e5.getText().toString();
				username=e6.getText().toString();
				password=e7.getText().toString();
			
				if(r1.isChecked())
				{
					gender="Male";
				}
				else
				{
					gender="Female";
				}
				
//				Toast.makeText(getApplicationContext(),"FIRSTNAME="+firstname+"\nLASTNAME="+lastname+"\nHOUSENAME="+housename+"\nHOMETOWN="+HomeTown+"\nPINCODE="+Pincode+"\nEMAIL="+email+"\nCONTACT="+contact,Toast.LENGTH_LONG).show();
//				startActivity(new Intent(getApplicationContext(),Login.class));
				
				JsonReq jr= new JsonReq();
				jr.json_response=(JsonResponse) Registration.this;
				String q="register/?firstname="+firstname+"&lastname="+lastname+"&gender="+gender+"&age="+age+"&email="+email+"&contact="+contact+"&username="+username+"&password="+password;
				q.replace("", "%20");
				jr.execute(q);
				
				
			}
		});
		
		
	}

	
	  public void onBackPressed() 
			{
				// TODO Auto-generated method stub
				super.onBackPressed();
				Intent b=new Intent(getApplicationContext(),Login.class);			
				startActivity(b);
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
				Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_LONG).show();
				startActivity(new Intent(getApplicationContext(),Login.class));
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

