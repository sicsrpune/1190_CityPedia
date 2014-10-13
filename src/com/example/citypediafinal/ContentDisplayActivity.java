package com.example.citypediafinal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
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
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class ContentDisplayActivity extends BaseActivity1 {
	TextView txtTitle,txtPlace,txtLike,txtExp,txtUser,txtCount;
	Button btnLike,btnComment;
	EditText editComment;
	ListView lstComments;
	RatingBar rateStar;
	URL url;
	HttpPost httppost;
	ArrayList<NameValuePair> data;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
	String uname,content_id,user_id,category,sub_category,date,title,expe,place,city,ratings,likes,city_place,user,comment;
	ProgressDialog progress;
	ArrayList<String> comment_arr = new ArrayList<String>();
	private ListView listView;
    private ContentDisplayCustomAdapter adapter;
	ImageButton home;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_display);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		txtTitle = (TextView)findViewById(R.id.textName);
		txtPlace = (TextView)findViewById(R.id.textPlace);
		txtLike = (TextView)findViewById(R.id.textLike);
		txtExp = (TextView)findViewById(R.id.textExpe);
		txtUser = (TextView)findViewById(R.id.textSharedUser);
		txtCount = (TextView)findViewById(R.id.textCnt);
		btnLike = (Button)findViewById(R.id.btnLike);
		btnComment = (Button)findViewById(R.id.btnComment);
		listView = (ListView)findViewById(R.id.listViewComments);
		editComment = (EditText)findViewById(R.id.editComment);
		rateStar = (RatingBar)findViewById(R.id.ratingStar);
		editComment.clearFocus();
		getExperience();
		getComment();
		setTitle(title.toString());
		txtTitle.setText(title);
		txtPlace.setText(city_place);
		txtLike.setText(likes);
		txtExp.setText(expe);
		txtUser.setText("- "+user);
		rateStar.setRating(Float.parseFloat(ratings));
		
		txtTitle.setTextSize(txtTitle.getTextSize()+10);
		
		home = (ImageButton)findViewById(R.id.actionBarHome);

		home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intObj = new Intent(getApplicationContext(),DashboardActivity.class);
				startActivity(intObj);
				finish();				
			}
		});
		
		btnLike.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				setLike();
				
			}
		});
		
		btnComment.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				if(!editComment.getText().toString().equals("")){
					postComment();
				}
				else{
					Toast.makeText(getApplicationContext(),"Enter Comment To Post", Toast.LENGTH_LONG).show();
				}
				
			}
		});
		
	}
	
	public void getComment(){
		runOnUiThread(new Runnable() {
            public void run() {
		try{
			content_id = getIntent().getStringExtra("con_id").toString();
				url = new URL("http://saheleecreation.in/android_cp/getComment.php?content_id="+content_id);
				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
				connection.setDoInput(true);
				connection.setDoOutput(true);     
      
				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
				String line = "";
				while((line = reader.readLine()) != null){
			
				JSONArray object = new JSONArray(line);
				
				for(int len=0;len<object.length();len++){
					comment =  object.getJSONObject(len).getString("comment");
					comment_arr.add(comment);
				}
			} 

				adapter = new ContentDisplayCustomAdapter(getApplicationContext(), comment_arr);
 				listView.setAdapter(adapter);
 				
 				if(listView.getCount()==0){
					txtCount.setText("Be the first to comment...");
				}
				else{
					txtCount.setText("");
				}   					
		}
		
		catch (Exception e) {
				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
      }});
	}
	
	public void setLike(){
		runOnUiThread(new Runnable() {
            public void run() {
		try{
		content_id = getIntent().getStringExtra("con_id").toString();
		SharedPreferences prefUser;
		prefUser = getSharedPreferences("MyPrefs", 0);
		uname = prefUser.getString("keyUname", null);
		 httpclient=new DefaultHttpClient();
		 httppost = new HttpPost("http://saheleecreation.in/android_cp/setLikes.php?clid="+content_id+"&email="+uname);
		 data = new ArrayList<NameValuePair>();
        
         httppost.setEntity(new UrlEncodedFormEntity(data));
         response=httpclient.execute(httppost);

         ResponseHandler<String> responseHandler = new BasicResponseHandler();
         final String response = httpclient.execute(httppost, responseHandler);
         
        if(response.equalsIgnoreCase("success")){
        	Bundle cat = new Bundle();
			cat.putString("con_id", content_id);
			Intent intObj = new Intent(getApplicationContext(),ContentDisplayActivity.class);
			intObj.putExtras(cat);
			startActivity(intObj);            
			finish();
        }     
             
			
        }
		catch (Exception e) {
  				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
            }
        });

	}
	
	public void postComment(){
		runOnUiThread(new Runnable() {
            public void run() {
		try{
		content_id = getIntent().getStringExtra("con_id").toString();
		SharedPreferences prefUser;
		prefUser = getSharedPreferences("MyPrefs", 0);
		uname = prefUser.getString("keyUname", null);
		 httpclient=new DefaultHttpClient();
		 httppost = new HttpPost("http://saheleecreation.in/android_cp/postComment.php");
		 data = new ArrayList<NameValuePair>();
         data.add(new BasicNameValuePair("cid",content_id));
         data.add(new BasicNameValuePair("email",uname));
         data.add(new BasicNameValuePair("comment",editComment.getText().toString().trim()));

         
         httppost.setEntity(new UrlEncodedFormEntity(data));
         response=httpclient.execute(httppost);

         ResponseHandler<String> responseHandler = new BasicResponseHandler();
         final String response = httpclient.execute(httppost, responseHandler);
         
        if(response.equalsIgnoreCase("success")){
            	Bundle cat = new Bundle();
    			cat.putString("con_id", content_id);
    			Intent intObj = new Intent(getApplicationContext(),ContentDisplayActivity.class);
    			intObj.putExtras(cat);
    			startActivity(intObj);            
    			finish();    
    			Toast.makeText(getApplicationContext(), "Comment Posted...", Toast.LENGTH_LONG).show();
        }     
             
			
        }
		catch (Exception e) {
  				Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_LONG).show();
		}
            }
        });

	}
	
	public void getExperience(){
		runOnUiThread(new Runnable() {
            public void run() {
		
    		try{
    			content_id = getIntent().getStringExtra("con_id");
   				url = new URL("http://saheleecreation.in/android_cp/getExperience.php?content_id="+content_id);
   				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
   				connection.setDoInput(true);
   				connection.setDoOutput(true);     
            
   				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
   				String line = "";
   				while((line = reader.readLine()) != null){
    			
   					JSONObject object = new JSONObject(line);
					content_id =  object.getString("cid");
					user_id =  object.getString("uid");
					category =  object.getString("category");
					sub_category =  object.getString("sub_category");
					date =  object.getString("date");
					title =  object.getString("title");
					expe=  object.getString("expe");
					place=  object.getString("place");
					city=  object.getString("city");
					ratings=  object.getString("rating");
					likes=  object.getString("likes");
					user=  object.getString("user");
					city_place = place +" > "+city;
							    					
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
		getMenuInflater().inflate(R.menu.content_display, menu);
		return true;
	}

}
