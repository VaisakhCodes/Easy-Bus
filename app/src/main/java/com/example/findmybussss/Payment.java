package com.example.findmybussss;

import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class Payment extends Activity implements JsonResponse
{

	
	EditText e1,ed_card_num,ed_cvv,ed_exp_date;
	Button b1;
	ListView l1;
	String logid,exp_date,amount,card_no,cvv;
	SharedPreferences sh;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		e1=(EditText)findViewById(R.id.ed_amount);
		b1=(Button)findViewById(R.id.bt_pay);

		ed_card_num = findViewById(R.id.ed_card_num);
		ed_cvv = findViewById(R.id.ed_cvv);
		ed_exp_date = findViewById(R.id.ed_exp_date);


		
		e1.setText(CustomerViewReserveds.amount);
		
		b1.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub



				amount = e1.getText().toString();
				card_no = ed_card_num.getText().toString();
				cvv = ed_cvv.getText().toString();
				exp_date = ed_exp_date.getText().toString();

				if (card_no.length() != 16) {
					ed_card_num.setError("Valid card number");
					ed_card_num.setFocusable(true);
				} else if (cvv.length() != 3) {
					ed_cvv.setError("Valid cvv");
					ed_cvv.setFocusable(true);
				} else if (exp_date.equalsIgnoreCase("")) {
					ed_exp_date.setError("Expiry date");
					ed_exp_date.setFocusable(true);
				}
				else {
					JsonReq jr = new JsonReq();
					jr.json_response = (JsonResponse) Payment.this;
					String q = "addpay/?rid=" + sh.getString("rids","") + "&amount=" + sh.getString("ramount","");
					q.replace("", "%20");
					jr.execute(q);
				}
			}
		});

		ed_exp_date.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				exp_date = ed_exp_date.getText().toString();
				if (exp_date.length() == 2 && !exp_date.contains("/")) {
					ed_exp_date.setText(exp_date + "/");
					ed_exp_date.setSelection(ed_exp_date.getText().length());
				}
			}
		});
		
		
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
				Toast.makeText(getApplicationContext(), "Paid", Toast.LENGTH_LONG).show();
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
