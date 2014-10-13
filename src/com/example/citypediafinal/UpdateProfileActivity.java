package com.example.citypediafinal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class UpdateProfileActivity extends BaseActivity1 {
	EditText fname,lname,bdate,address,contact,state;
	TextView chpwd;
	RadioGroup gender;
	RadioButton male,female;
	Spinner city;
	Button update;
	URL url;
	HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    ArrayList<NameValuePair> data;
	String uname,txtfname,txtlname,txtdob,txtaddress,txtcity,txtstate,txtcontact,txtgender;
	int flag=0;
	Pattern ptrText,ptrDate,ptrEmail,ptrContact;
	Matcher ptrMatcher;
	ImageButton home;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_update_profile);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		fname = (EditText)findViewById(R.id.editFname);
		lname = (EditText)findViewById(R.id.editLname);
		bdate = (EditText)findViewById(R.id.editDob);
		address = (EditText)findViewById(R.id.editAddress);
		contact = (EditText)findViewById(R.id.editContact);
		gender = (RadioGroup)findViewById(R.id.radioGroupGender);
		male = (RadioButton)findViewById(R.id.radioMale);
		female = (RadioButton)findViewById(R.id.radioFemale);
		city = (Spinner)findViewById(R.id.spinnerUserCity);
		state = (EditText)findViewById(R.id.editState);
		update = (Button)findViewById(R.id.btnUpdate);
		chpwd = (TextView)findViewById(R.id.textChangePwd);
		SharedPreferences prefUser;
		prefUser = getSharedPreferences("MyPrefs", 0);
		uname = prefUser.getString("keyUname", null);
		getUserData();
		ArrayAdapter myAdap = (ArrayAdapter) city.getAdapter(); //cast to an ArrayAdapter
		int spinnerPosition = myAdap.getPosition(txtcity);
		city.setSelection(spinnerPosition);
		fname.setText(txtfname);
		lname.setText(txtlname);
		bdate.setText(txtdob);
		address.setText(txtaddress);
		state.setText(txtstate);
		contact.setText(txtcontact);
		if(txtgender.equals("male")){
			gender.check(R.id.radioMale);
		}
		else if(txtgender.equals("female")){
			gender.check(R.id.radioFemale);
		}
		
		home = (ImageButton)findViewById(R.id.actionBarHome);

		home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intObj = new Intent(getApplicationContext(),DashboardActivity.class);
				startActivity(intObj);
				finish();				
			}
		});
		
		chpwd.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent obj = new Intent(getApplicationContext(),ChangePasswordActivity.class);
				startActivity(obj);
			}
		});
		
		ptrText = Pattern.compile("^\\p{L}+(?:\\p{L}+)*$");
		ptrEmail = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$");
		ptrDate = Pattern.compile("^((19|20)\\d{2})-(0[1-9]|1[0-2])-(0[1-9]|1\\d|2\\d|3[01])$");
		ptrContact = Pattern.compile("\\d{10}");
		update.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				if(fname.getText().toString()=="" || 
						lname.getText().toString()=="" ||
						bdate.getText().toString()=="" ||
						address.getText().toString()=="" ||
						state.getText().toString()=="" ||
						city.getSelectedItem().toString()=="Select City" ||
						contact.getText().toString()==""){
						flag=1;
				}
				
				ptrMatcher = ptrContact.matcher(contact.getText().toString());                  
				if(contact.getText().toString().equals("") || !ptrMatcher.find()){
						 flag=1;
						 contact.setText("");
						 contact.setHint("Contact Number Is Incorrect");
						 contact.setHintTextColor(Color.RED);	    		 		
				}
				else{
						flag=0;
				}			
				
				
				if(flag==0){
					setUserData();
				}
				else{
		        	 Toast.makeText(getApplicationContext(),"Please Enter Correct Data", Toast.LENGTH_SHORT).show();
				}
				
				
			}
		});
		
		fname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
				ptrMatcher = ptrText.matcher(fname.getText().toString());                  
				 if(fname.getText().toString().equals("") || !ptrMatcher.find()){
					 flag=1;
		    		 fname.setText("");
					 fname.setHint("First Name Is Incorrect");
		    		 fname.setHintTextColor(Color.RED);	    		 		
				 }
				 else{
					 flag=0;
				 }						
			}}
		});
		
		lname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
				ptrMatcher = ptrText.matcher(lname.getText().toString());                  
				 if(lname.getText().toString().equals("") || !ptrMatcher.find()){
					 flag=1;
		    		 lname.setText("");
					 lname.setHint("Last Name Is Incorrect");
		    		 lname.setHintTextColor(Color.RED);	    		 		
				 }
				 else{
					 flag=0;
				 }						
			}}
		});
		
		bdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				ptrMatcher = ptrDate.matcher(bdate.getText().toString());                  
				if(!hasFocus){
				 if(bdate.getText().toString().equals("") || !ptrMatcher.find()){
					 flag=1;
					 bdate.setText("");
					 bdate.setHint("Wrong Date : Eg. 1992-25-10");
					 bdate.setHintTextColor(Color.RED);	    		 		
				 }
				 else{
					 flag=0;
				 }						
			}}
		});
		
		state.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
				ptrMatcher = ptrText.matcher(state.getText().toString());                  
				 if(state.getText().toString().equals("") || !ptrMatcher.find()){
					 flag=1;
					 state.setText("");
					 state.setHint("State Name Is Incorrect");
					 state.setHintTextColor(Color.RED);	    		 		
				 }
				 else{
					 flag=0;
				 }						
			}}
		});
		
		contact.setOnFocusChangeListener(new View.OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				if(!hasFocus){
				ptrMatcher = ptrContact.matcher(contact.getText().toString());                  
				 if(contact.getText().toString().equals("") || !ptrMatcher.find()){
					 flag=1;
					 contact.setText("");
					 contact.setHint("Contact Number Is Incorrect");
					 contact.setHintTextColor(Color.RED);	    		 		
				 }
				 else{
					 flag=0;
				 }						
			}}
		});	
	}
	
	public void setUserData(){
		runOnUiThread(new Runnable() {
            public void run() {
		try{
			
		 httpclient=new DefaultHttpClient();
		 httppost = new HttpPost("http://saheleecreation.in/android_cp/updateProfile.php?function=setdata&email="+uname);
		 data = new ArrayList<NameValuePair>();
         data.add(new BasicNameValuePair("fname",fname.getText().toString().trim()));  
         data.add(new BasicNameValuePair("lname",lname.getText().toString().trim()));
         data.add(new BasicNameValuePair("dob",bdate.getText().toString().trim()));
         data.add(new BasicNameValuePair("address",address.getText().toString().trim()));
         data.add(new BasicNameValuePair("state",state.getText().toString().trim()));
         data.add(new BasicNameValuePair("contact",contact.getText().toString().trim()));
         if(gender.getCheckedRadioButtonId()== R.id.radioMale){
        	 data.add(new BasicNameValuePair("gender","male"));
         }
         else if(gender.getCheckedRadioButtonId()== R.id.radioFemale){
        	 data.add(new BasicNameValuePair("gender","female"));
         }
         data.add(new BasicNameValuePair("city",city.getSelectedItem().toString().trim()));

         httppost.setEntity(new UrlEncodedFormEntity(data));
         response=httpclient.execute(httppost);

         ResponseHandler<String> responseHandler = new BasicResponseHandler();
         final String response = httpclient.execute(httppost, responseHandler);
         
         
          
        if(response.equalsIgnoreCase("success")){
        	 Toast.makeText(getApplicationContext(),"Update Successfull", Toast.LENGTH_SHORT).show();
        }     
             
			startActivity(new Intent(getApplicationContext(), UpdateProfileActivity.class));
            finish();
        }
		catch (Exception e) {
  				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
            }
        });

		
	}
	
	public void getUserData(){
		runOnUiThread(new Runnable() {
            public void run() {
		
    		try{
    			
   				url = new URL("http://saheleecreation.in/android_cp/updateProfile.php?function=getdata&email="+uname);
   				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
   				connection.setDoInput(true);
   				connection.setDoOutput(true);     
            
   				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
   				String line = "";
   				while((line = reader.readLine()) != null){
    			
   					JSONObject object = new JSONObject(line);
					txtfname =  object.getString("fname");
					txtlname =  object.getString("lname");
					txtgender =  object.getString("gender");
					txtdob =  object.getString("dob");
					txtaddress =  object.getString("address");
					txtcity =  object.getString("city");
					txtstate =  object.getString("state");
					txtcontact =  object.getString("contact");		    					
   				}
    		}
   				catch (Exception e) {
   	   				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
   				}
            }});
    		
	}		
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.update_profile, menu);
		return true;
	}

}
