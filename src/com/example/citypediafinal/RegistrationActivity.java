package com.example.citypediafinal;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegistrationActivity extends BaseActivity {
	

	Button regi;
	EditText txtFname,txtLname,txtUname,txtPass,txtCpass,txtDob;
	String fname,lname,uname,upass,dob,cpass;
	DefaultHttpClient httpclient;
	HttpPost httppost;
	ArrayList<NameValuePair> data;
	HttpResponse response;
	InputStream is=null;
	String result=null;
	String line=null;
	int code,flag=0;
    ResponseHandler<String> responseHandler;
	Pattern ptrText,ptrDate,ptrEmail;
	Matcher ptrMatcher;
    public static final String MyPREFERENCES = "MyPrefs" ;

	
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_registration);
		setTitle("Registration");
		

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(policy);
		
		
		txtFname = (EditText)findViewById(R.id.editFname);
		txtLname = (EditText)findViewById(R.id.editLname);
		txtUname = (EditText)findViewById(R.id.editEmail);
		txtPass = (EditText)findViewById(R.id.editPassword);
		txtCpass = (EditText)findViewById(R.id.editConfirmPassword);
		txtDob = (EditText)findViewById(R.id.editDob);
		
		ptrText = Pattern.compile("^\\p{L}+(?:\\p{L}+)*$");
		ptrEmail = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$");
		ptrDate = Pattern.compile("^((19|20)\\d{2})-(0[1-9]|1[0-2])-(0[1-9]|1\\d|2\\d|3[01])$");
		
		
		
		regi = (Button)findViewById(R.id.btnRegister);
		regi.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				
				
							
				if(txtFname.getText().toString().equals("") 
						|| txtLname.getText().toString().equals("")
						|| txtUname.getText().toString().equals("")
						|| txtPass.getText().toString().equals("")
						|| txtDob.getText().toString().equals("")){
					flag=1;	
				}
				else if(txtCpass.getText().toString().equals("") || !txtCpass.getText().toString().equals(txtPass.getText().toString())){
					 flag=1;
		    		 txtCpass.setText("");
		    		 txtCpass.setHint("Both Passwords Must Match ");
		    		 txtCpass.setHintTextColor(Color.RED);
				}
				else
				{
					flag=0;
				}
				
				
				
		        if(flag == 0){
		        	
	                
		       
							
						 	try{
						 		
						 		fname = txtFname.getText().toString();
								lname = txtLname.getText().toString();
								uname = txtUname.getText().toString();
								upass = txtPass.getText().toString();
								cpass = txtCpass.getText().toString();
								dob = txtDob.getText().toString();
								
								data = new ArrayList<NameValuePair>();
				                data.add(new BasicNameValuePair("fname", fname));
				                data.add(new BasicNameValuePair("lname", lname));
				                data.add(new BasicNameValuePair("uname", uname));
				                data.add(new BasicNameValuePair("upass", upass));
				                data.add(new BasicNameValuePair("dob", dob));
				                
						 		httpclient = new DefaultHttpClient();
								httppost = new HttpPost("http://saheleecreation.in/android_cp/registration.php");
					            
				                httppost.setEntity(new UrlEncodedFormEntity(data));
				                response = httpclient.execute(httppost);
				                
				               responseHandler = new BasicResponseHandler();
				  	          	final String response = httpclient.execute(httppost, responseHandler);
				               
				  	          
				  	          	  SharedPreferences settings = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
					    		  SharedPreferences.Editor editor = settings.edit();
					    		  editor.putString("keyUname",uname);
					    		  editor.commit(); 
							      Toast.makeText(getApplicationContext(),"Welcome To Citypedia..!!", Toast.LENGTH_SHORT).show();
				         
							      startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
							      finish(); 
			            }       
						 	catch(Exception e)
						 	{	
						 		Toast.makeText(getApplicationContext(), "Network Error. Please Check Your Internet Connection", Toast.LENGTH_LONG).show();
						 	}							
		        }
		        else{
			 		Toast.makeText(getApplicationContext(), "Please Enter Correct Data", Toast.LENGTH_LONG).show();
				}
		}});
		
		
			
		
	txtFname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
			ptrMatcher = ptrText.matcher(txtFname.getText().toString());                  
			 if(txtFname.getText().toString().equals("") || !ptrMatcher.find()){
				 flag=1;
	    		 txtFname.setText("");
				 txtFname.setHint("First Name Is Incorrect");
	    		 txtFname.setHintTextColor(Color.RED);	    		 		
			 }
			 else{
				 flag=0;
			 }						
		}}
	});
	
	txtLname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
			ptrMatcher = ptrText.matcher(txtLname.getText().toString());                  
			 if(txtLname.getText().toString().equals("") || !ptrMatcher.find()){
				 flag=1;
	    		 txtLname.setText("");
				 txtLname.setHint("Last Name Is Incorrect");
	    		 txtLname.setHintTextColor(Color.RED);	    		 		
			 }
			 else{
				 flag=0;
			 }						
		}}
	});
	
	
	txtPass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
			                 
			 if(txtPass.getText().toString().equals("") || txtPass.getText().toString().length() < 7){
				 flag=1;
	    		 txtPass.setText("");
				 txtPass.setHint("Minimum 7 Character For Password ");
	    		 txtPass.setHintTextColor(Color.RED);	    		 		
			 }
			 else{
				 flag=0;
			 }						
		}}
	});
	
	txtCpass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
			 if(txtCpass.getText().toString().equals("") || !txtCpass.getText().toString().equals(txtPass.getText().toString())){
				 flag=1;
	    		 txtCpass.setText("");
	    		 txtCpass.setHint("Both Passwords Must Match ");
	    		 txtCpass.setHintTextColor(Color.RED);	    		 		
			 }
			 else{
				 flag=0;
			 }						
		}}
	});
	
	txtUname.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			ptrMatcher = ptrEmail.matcher(txtUname.getText().toString());                  
			if(!hasFocus){
			 if(txtUname.getText().toString().equals("") || !ptrMatcher.find()){
				 flag=1;
	    		 txtUname.setText("");
	    		 txtUname.setHint("Email Is Incorrect");
	    		 txtUname.setHintTextColor(Color.RED);	    		 		
			 }
			 else{
				 flag=0;
			 }						
		}}
	});
	
	
	txtDob.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			ptrMatcher = ptrDate.matcher(txtDob.getText().toString());                  
			if(!hasFocus){
			 if(txtDob.getText().toString().equals("") || !ptrMatcher.find()){
				 flag=1;
	    		 txtDob.setText("");
	    		 txtDob.setHint("Wrong Date : Eg. 1992-25-10");
	    		 txtDob.setHintTextColor(Color.RED);	    		 		
			 }
			 else{
				 flag=0;
			 }						
		}}
	});
	

	
	
		
		
		}

	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.registration, menu);
		return true;
	}

}
