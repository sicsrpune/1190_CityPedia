package com.example.citypediafinal;

import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ChangePasswordActivity extends Activity {

	URL url;
	HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    ArrayList<NameValuePair> data;
	EditText editOldpass,editNewpass,editConfpass;
	String oldpass,newspass,confpass,uname;
	Button btn_changepwd,btn_cancel;
	int flag=0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_change_password);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		SharedPreferences prefCity;
		editOldpass = (EditText)findViewById(R.id.editOldPassword);
		editNewpass = (EditText)findViewById(R.id.editNewPassword);
		editConfpass = (EditText)findViewById(R.id.editCNewPassword);
		oldpass = editOldpass.getText().toString();
		newspass = editNewpass.getText().toString();
		confpass = editConfpass.getText().toString();
		btn_changepwd = (Button)findViewById(R.id.btnChPwd);
		btn_cancel = (Button)findViewById(R.id.btnchCancel);

		prefCity = getSharedPreferences("MyPrefs", 0);
		uname = prefCity.getString("keyUname", null);
		
		btn_cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
		
		btn_changepwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(editNewpass.getText().toString().equals("")||editOldpass.getText().toString().equals("")||editConfpass.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "Please Enter All The Data", Toast.LENGTH_LONG).show();
					flag=1;
				}
				else if(!editNewpass.getText().toString().equals(editConfpass.getText().toString())){
					Toast.makeText(getApplicationContext(), "Both Passwords Must Match", Toast.LENGTH_LONG).show();
					flag=1;
				}
			    else if(editNewpass.getText().toString().length() < 7){
					Toast.makeText(getApplicationContext(), "Password length should be minimum 7 characters", Toast.LENGTH_LONG).show();
					flag=1;
				}
			    else{
			    	flag=0;
			    }
				
				if(flag==0){
					changePassword();
				}
			}
		});
		
	}
	
	public void changePassword(){
		runOnUiThread(new Runnable() {
            public void run() {
		try{
		SharedPreferences prefCity;
		prefCity = getSharedPreferences("MyPrefs", 0);
		uname = prefCity.getString("keyUname", null);
		 httpclient=new DefaultHttpClient();
		 httppost = new HttpPost("http://saheleecreation.in/android_cp/changePassword.php");
		 data = new ArrayList<NameValuePair>();
         data.add(new BasicNameValuePair("oldpass",editOldpass.getText().toString().trim()));  
         data.add(new BasicNameValuePair("newpass",editNewpass.getText().toString().trim()));
         data.add(new BasicNameValuePair("confpass",editConfpass.getText().toString().trim()));
         data.add(new BasicNameValuePair("uname",uname.trim()));


         httppost.setEntity(new UrlEncodedFormEntity(data));
         response=httpclient.execute(httppost);

         ResponseHandler<String> responseHandler = new BasicResponseHandler();
         final String response = httpclient.execute(httppost, responseHandler);
          
        if(response.equalsIgnoreCase("success")){
        	 Toast.makeText(getApplicationContext(),"Password Successfully Changed", Toast.LENGTH_SHORT).show();
        	 finish();
        }
        else if(response.equalsIgnoreCase("error")){
        	Toast.makeText(getApplicationContext(),"Please Try Again", Toast.LENGTH_SHORT).show();
        }
        else if(response.equalsIgnoreCase("wrongpass")){
        	Toast.makeText(getApplicationContext(),"Wrong Old Password", Toast.LENGTH_SHORT).show();

        }
        }
		catch (Exception e) {
  				Toast.makeText(getApplicationContext(), "Network Error..Try after some time. "+e.toString(), Toast.LENGTH_LONG).show();
		}
            }
        });

		
	}

	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.change_password, menu);
		return true;
	}

}
