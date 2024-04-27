package com.example.findmybussss;



import android.os.Bundle;
import android.preference.PreferenceManager;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class IPsetting extends Activity {
	EditText ed_ip;
	Button bt_ip;
	public static String ip;
	SharedPreferences sh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ipsetting);

		sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

		ed_ip = (EditText) findViewById(R.id.editText1);
		bt_ip = (Button) findViewById(R.id.button1);

		ed_ip.setText(sh.getString("ip","192.168."));

		bt_ip.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub


				ip=ed_ip.getText().toString();
				if (ip.equals("")) {
					ed_ip.setError("Enter IP address");
					ed_ip.setFocusable(true);

				}else {
					Editor ed= sh.edit();
					ed.putString("ip",ip);
					ed.commit();
					startActivity(new Intent(getApplicationContext(),Login.class));
				}
			}


		});


	}



    public void onBackPressed()
    {
        // TODO Auto-generated method stub
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Exit  :")
                .setMessage("Are you sure you want to exit..?")
                .setPositiveButton("Yes",new DialogInterface.OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface arg0, int arg1)
                    {
                        // TODO Auto-generated method stub
                        Intent i=new Intent(Intent.ACTION_MAIN);
                        i.addCategory(Intent.CATEGORY_HOME);
                        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(i);
                        finish();
                    }
                }).setNegativeButton("No",null).show();

    }

//    public static class Sendnotification extends AppCompatActivity implements  JsonResponse{
//        EditText e1;
//        Button b1;
//
//        SharedPreferences sh;
//        String notification;
//
//
//
//        @Override
//        protected void onCreate(Bundle savedInstanceState) {
//            super.onCreate(savedInstanceState);
//
//            setContentView(R.layout.activity_sendnotification);
//
//            sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//
//            e1=(EditText) findViewById(R.id.editTextTextPersonName);
//
//            b1=(Button) findViewById(R.id.button);
//
//            b1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    notification=e1.getText().toString();
//                    if (notification.equalsIgnoreCase("")){
//                        e1.setError("ENTER THE NOTIFICATION");
//                        e1.setFocusable(true);
//                    }else {
//                        JsonReq JR= new JsonReq();
//                        JR.json_response=(JsonResponse)Sendnotification.this;
//                        String q="notification/?notification=" + notification + "&conductor_id=" + sh.getString("logid","") ;
//                        JR.execute(q);
//                    }
//                }
//            });
//
//
//        }
//
//        @Override
//        public void response(JSONObject jo) {
//            try {
//                String status = jo.getString("status");
//                Log.d("method",status);
//
//                if (status.equalsIgnoreCase("success")){
//                    Toast.makeText(this, "Notification send success", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(getApplicationContext(),BusHome.class));
//                }
//
//
//            }
//
//            catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
