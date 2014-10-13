package com.example.citypediafinal;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DashboardActivity extends Activity {

	Button shareExp;
	ImageButton userMenu;
	TextView userfullname,experience;
	ImageView btnFoodzone,btnHangout,btnShopping,btnEvent,btnOrg,btnOther;
	URL url;
	String uname,fullname,exp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		SharedPreferences prefCity;

		userfullname = (TextView)findViewById(R.id.textUser);
		experience = (TextView)findViewById(R.id.textExp);
		btnFoodzone = (ImageView)findViewById(R.id.btnFoodZone);
		btnHangout = (ImageView)findViewById(R.id.btnHangout);
		btnShopping = (ImageView)findViewById(R.id.btnShopping);
		btnEvent = (ImageView)findViewById(R.id.btnEvent);
		btnOrg = (ImageView)findViewById(R.id.btnOrg);
		btnOther = (ImageView)findViewById(R.id.btnOther);
		prefCity = getSharedPreferences("MyPrefs", 0);
		uname = prefCity.getString("keyUname", null);
		userfullname.setTextSize(userfullname.getTextSize()+10);
		getUserInfo();
		setTitle(userfullname.getText().toString()+"'s Dashboard");

		
		
		

		if(!prefCity.contains("keyCity")){
			Intent startSelectCity = new Intent(getApplicationContext(),SelectCityActivity.class);
			startActivity(startSelectCity);
		}
		
		if(!prefCity.contains("keyUname")){
			Intent startLogin = new Intent(getApplicationContext(),LoginActivity.class);
			startActivity(startLogin);
		}
				
		shareExp =(Button)findViewById(R.id.btnShare);
		userMenu = (ImageButton)findViewById(R.id.btnUserMenu);
		
		userMenu.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent obj = new Intent(getApplicationContext(),UserMenuActivity.class);
				startActivity(obj);
				
			}
		});
		
		shareExp.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intObj = new Intent(getApplicationContext(),ShareExpActivity.class);
				startActivity(intObj);
				finish();
				
			}
		});
		
		btnFoodzone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle cat = new Bundle();
				cat.putString("catagory", "Food%20Zone");
				Intent intObj = new Intent(getApplicationContext(),BrowsingContentActivity.class);
				intObj.putExtras(cat);
				startActivity(intObj);
				finish();
								
			}
		});
		
		btnHangout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle cat = new Bundle();
				cat.putString("catagory", "Hangout");
				Intent intObj = new Intent(getApplicationContext(),BrowsingContentActivity.class);
				intObj.putExtras(cat);
				startActivity(intObj);
				finish();
			}
		});
		
		btnShopping.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle cat = new Bundle();
				cat.putString("catagory", "Shopping");
				Intent intObj = new Intent(getApplicationContext(),BrowsingContentActivity.class);
				intObj.putExtras(cat);
				startActivity(intObj);
				finish();	
			}
		});
		
		btnEvent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle cat = new Bundle();
				cat.putString("catagory", "Event");
				Intent intObj = new Intent(getApplicationContext(),BrowsingContentActivity.class);
				intObj.putExtras(cat);
				startActivity(intObj);
				finish();	
			}
		});
		
		btnOrg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle cat = new Bundle();
				cat.putString("catagory", "Organization");
				Intent intObj = new Intent(getApplicationContext(),BrowsingContentActivity.class);
				intObj.putExtras(cat);
				startActivity(intObj);
				finish();		
			}
		});

		btnOther.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle cat = new Bundle();
				cat.putString("catagory", "Other");
				Intent intObj = new Intent(getApplicationContext(),BrowsingContentActivity.class);
				intObj.putExtras(cat);
				startActivity(intObj);
				finish();
								
			}
		});
		
				
	}
	
	public void getUserInfo(){
		
		try{
			SharedPreferences prefCity;
			prefCity = getSharedPreferences("MyPrefs", 0);
			uname = prefCity.getString("keyUname", null);
				  
        
				JSONObject json = null;
		        String str = "";
				HttpResponse response;
		        HttpClient myClient = new DefaultHttpClient();
		        HttpPost myConnection = new HttpPost("http://saheleecreation.in/android_cp/dashboard.php?email="+uname);
		         
		        try {
		            response = myClient.execute(myConnection);
		            str = EntityUtils.toString(response.getEntity(), "UTF-8");
		             
		        } catch (Exception e) {
		        
		        }
		         
				
				JSONArray jArray = new JSONArray(str);
	            json = jArray.getJSONObject(0);
	             
				fullname = json.getString("fullname");
				exp = json.getString("experience");
				userfullname.setText(fullname);
				experience.setText(exp+" Experiences");
				
						    					
				
		}
				catch (Exception e) {
	   				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
	   				Log.e("test", e.toString());
	   				e.printStackTrace();
	   			}
	}
			
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.dashboard, menu);
		return true;
	}

}
