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

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.RatingBar.OnRatingBarChangeListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class ShareExpActivity extends BaseActivity1 {
	
	Spinner spinCatagory,spinSubCatagory;
	EditText editTitle,editDate,editExp,editPlace;
	TextView txtPic,txtCity;
	RatingBar rateStar;
	Button btnUpload,btnShare;
	int flag = 0,rate=1,RESULT_LOAD_IMAGE=1;
	Pattern ptrText,ptrDate,ptrEmail;
	Matcher ptrMatcher;
	String catagory,subcatagory,title,date,exp,place,city,star="1",picture,uname;
	DefaultHttpClient httpclient;
	HttpPost httppost;
	ArrayList<NameValuePair> data;
	HttpResponse response;
	InputStream is=null;
	String result=null;
	String line=null;
	ImageButton home;

	private SharedPreferences prefCity,prefLogin;
	
	ArrayAdapter<CharSequence> adapter;


	@Override
	public void onBackPressed() {
		Toast.makeText(getApplicationContext(), "Press Home Button To See Dasboard", Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_share_exp);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build(); 
		StrictMode.setThreadPolicy(policy);
		
		spinCatagory = (Spinner)findViewById(R.id.spinner_category);
		spinSubCatagory = (Spinner)findViewById(R.id.Spinner_subCategory);
		editTitle = (EditText)findViewById(R.id.editTitle);
		editDate = (EditText)findViewById(R.id.editDOV);
		editExp = (EditText)findViewById(R.id.editExperiance);
		editPlace = (EditText)findViewById(R.id.editPlace);
		txtCity = (TextView)findViewById(R.id.textCity);
		txtPic = (TextView)findViewById(R.id.textImageName);
		rateStar = (RatingBar)findViewById(R.id.rating);
		btnUpload = (Button)findViewById(R.id.btnImageUpload);
		btnShare = (Button)findViewById(R.id.btnShareExp);
		
		spinCatagory.setPrompt("Select Catagory");
		
		ptrText = Pattern.compile("^\\p{L}+(?: \\p{L}+)*$");
		ptrEmail = Pattern.compile("^[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,6}$");
		ptrDate = Pattern.compile("^((19|20)\\d{2})-(0[1-9]|1[0-2])-(0[1-9]|1\\d|2\\d|3[01])$");
		
		prefCity = getApplicationContext().getSharedPreferences("MyPrefs", 0);
		prefLogin = getApplicationContext().getSharedPreferences("MyPrefs", 0);

		if(prefCity.contains("keyCity")){
			city = prefCity.getString("keyCity", "null");
			txtCity.setText(city);
		}
		
		if(prefLogin.contains("keyUname")){
			uname = prefLogin.getString("keyUname", "null");
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
		
		rateStar.setOnRatingBarChangeListener(new OnRatingBarChangeListener() {
			
			@Override
			public void onRatingChanged(RatingBar ratingBar, float rating,
					boolean fromUser) {
				rate = (int)rating;
				star = String.valueOf(rate);
			}
		});
	
		btnUpload.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent();
	            intent.setType("image/*");
	            intent.setAction(Intent.ACTION_GET_CONTENT);
	            startActivityForResult(Intent.createChooser(intent, "Complete action using"), 1); 				
			}
		});
		
		btnShare.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				ptrMatcher = ptrText.matcher(editPlace.getText().toString());   

				if(spinCatagory.getSelectedItem().toString().equals("Select Catagory")){
					flag = 1;
					Toast.makeText(getApplicationContext(), "Please Enter Catagory", Toast.LENGTH_LONG).show();
					spinCatagory.setBackgroundColor(Color.RED);
				}
				
					if(editTitle.getText().toString().equals("") 
							|| editDate.getText().toString().equals("")
							|| editExp.getText().toString().equals("")
							|| editPlace.getText().toString().equals("")
							|| txtCity.getText().toString().equals("")){
						flag=1;
					}
					else if(editPlace.getText().toString().equals("") || !ptrMatcher.find()){
						 flag=1;
						 editPlace.setText("");
						 editPlace.setHint("Place Is Incorrect");
						 editPlace.setHintTextColor(Color.RED);	    		 		
					 }
					
					
					if(flag==0){
						try{
					 		
					 		catagory = spinCatagory.getSelectedItem().toString();
							subcatagory = spinSubCatagory.getSelectedItem().toString();
							title = editTitle.getText().toString();
							date = editDate.getText().toString();
							exp = editExp.getText().toString();
							place = editPlace.getText().toString();
							picture = txtPic.getText().toString();
							
							
							data = new ArrayList<NameValuePair>();
			                data.add(new BasicNameValuePair("catagory", catagory));
			                data.add(new BasicNameValuePair("subcatagory", subcatagory));
			                data.add(new BasicNameValuePair("title", title));
			                data.add(new BasicNameValuePair("date", date));
			                data.add(new BasicNameValuePair("exp", exp));
			                data.add(new BasicNameValuePair("place", place));
			                data.add(new BasicNameValuePair("city", city));
			                data.add(new BasicNameValuePair("star", star));
			                data.add(new BasicNameValuePair("picture", picture));
			                data.add(new BasicNameValuePair("uname", uname));

			                
					 		httpclient = new DefaultHttpClient();
							httppost = new HttpPost("http://saheleecreation.in/android_cp/shareExperience.php");
				            Log.e("testing", "kk");
			                httppost.setEntity(new UrlEncodedFormEntity(data));
			                response = httpclient.execute(httppost);
			                
			                ResponseHandler<String> responseHandler = new BasicResponseHandler();
			                final String response = httpclient.execute(httppost, responseHandler);

			                if(response.equalsIgnoreCase("success")){               
			                	showAlert();
			                }
			                else
			                {
						 		Toast.makeText(getApplicationContext(), "Error Publishing Experience...Try Again", Toast.LENGTH_LONG).show();
			                }
					 		
			            }       
					 	catch(Exception e)
					 	{	
					 		Toast.makeText(getApplicationContext(), "Network Error. Please Check Your Internet Connection"+e.toString(), Toast.LENGTH_LONG).show();
					 	}							
	        }
	        else{
		 		Toast.makeText(getApplicationContext(), "Please Enter Correct Data", Toast.LENGTH_LONG).show();
			}		
		}
		});
		
	
	
	editTitle.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
			 if(editTitle.getText().toString().equals("")){
				 flag=1;
	    		 editTitle.setText("");
	    		 editTitle.setHint("Title Is Incorrect");
	    		 editTitle.setHintTextColor(Color.RED);	    		 		
			 }
			 else{
				 flag=0;
			 }						
		}}
	});
	
	editDate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			ptrMatcher = ptrDate.matcher(editDate.getText().toString());                  
			if(!hasFocus){
			 if(editDate.getText().toString().equals("") || !ptrMatcher.find()){
				 flag=1;
				 editDate.setText("");
				 editDate.setHint("Wrong Date : Eg. 1992-12-31");
				 editDate.setHintTextColor(Color.RED);	    		 		
			 }
			 else{
				 flag=0;
			 }						
		}}
	});
	
	editExp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
			 if(editExp.getText().toString().equals("")){
				 flag=1;
				 editExp.setText("");
				 editExp.setHint("Experience Is Incorrect");
				 editExp.setHintTextColor(Color.RED);	    		 		
			 }
			 else{
				 flag=0;
			 }						
		}}
	});
	
	editPlace.setOnFocusChangeListener(new View.OnFocusChangeListener() {
		
		@Override
		public void onFocusChange(View v, boolean hasFocus) {
			if(!hasFocus){
			ptrMatcher = ptrText.matcher(editPlace.getText().toString());                  
			 if(editPlace.getText().toString().equals("") || !ptrMatcher.find()){
				 flag=1;
				 editPlace.setText("");
				 editPlace.setHint("Place Is Incorrect");
				 editPlace.setHintTextColor(Color.RED);	    		 		
			 }
			 else{
				 flag=0;
			 }						
		}}
	});
	
	spinCatagory.setOnItemSelectedListener(new OnItemSelectedListener() {

        public void onItemSelected(AdapterView<?> parentView,
                View selectedItemView, int position, long id) {
			if(!spinCatagory.getSelectedItem().toString().equals("Select Catagory")){
				spinCatagory.setBackgroundResource(android.R.drawable.btn_dropdown);
			}
			setSubCatagory();
        }
        public void onNothingSelected(AdapterView<?> arg0) {// do nothing
        }
    });
	}
	
	public void setSubCatagory(){
		if(spinCatagory.getSelectedItem().toString().equals("Hangout")){
				adapter = ArrayAdapter.createFromResource(
		        this, R.array.hangout, android.R.layout.simple_spinner_item);		
		}
		else if(spinCatagory.getSelectedItem().toString().equals("Food Zone")){
			adapter = ArrayAdapter.createFromResource(
			this, R.array.foodzone, android.R.layout.simple_spinner_item);
		}
		else if(spinCatagory.getSelectedItem().toString().equals("Shopping")){
			adapter = ArrayAdapter.createFromResource(
			this, R.array.shopping, android.R.layout.simple_spinner_item);
		}
		else if(spinCatagory.getSelectedItem().toString().equals("Event")){
			adapter = ArrayAdapter.createFromResource(
			this, R.array.event, android.R.layout.simple_spinner_item);
		}
		else if(spinCatagory.getSelectedItem().toString().equals("Organisation")){
			adapter = ArrayAdapter.createFromResource(
			this, R.array.organisation, android.R.layout.simple_spinner_item);
		}
		else if(spinCatagory.getSelectedItem().toString().equals("Other")){
			adapter = ArrayAdapter.createFromResource(
			this, R.array.other, android.R.layout.simple_spinner_item);
		}
		else{
			adapter = ArrayAdapter.createFromResource(
		    this, R.array.other, android.R.layout.simple_spinner_item);
		}
		
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinSubCatagory.setAdapter(adapter);
	}
	
	
	public void showAlert(){
        ShareExpActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(ShareExpActivity.this);
                builder.setTitle("CityPedia");
                builder.setMessage("Experience Successfully Shared...")  
                       .setPositiveButton("Share New", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
                        	    editTitle.setText("");
   								editDate.setText("");
   								editExp.setText("");
   								editPlace.setText("");
   								rateStar.setRating(0);
                           }
                       })
                       
                       .setNegativeButton("Exit", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						finish();
					}
				});
                
                AlertDialog alert = builder.create();
                alert.show();               
            }
        });
    }
	 
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.share_exp, menu);
		return true;
	}

}
