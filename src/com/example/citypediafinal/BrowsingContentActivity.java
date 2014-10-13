package com.example.citypediafinal;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;

import android.app.ActionBar;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class BrowsingContentActivity extends BaseActivity1  {
	private ListView listView;
    private BrowsingContentCustomAdapter adapter;
    private String catagory;
    HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    ArrayList<NameValuePair> data;
    ProgressDialog dialog = null;
    String id,title,place,city,tmptitle,tmpcity,tmpplace,tmpid,subcategory;
    URL url;
	ArrayList<String> id_arr = new ArrayList<String>();     
	ArrayList<String> title_arr = new ArrayList<String>(); 
	ArrayList<String> city_arr = new ArrayList<String>(); 	
	ArrayList<Bitmap> pic_arr = new ArrayList<Bitmap>(); 
	ImageView imgFoodZone,imgHangout,imgEvent,imgShopping,imgOrg,imgOther;
	ArrayAdapter<CharSequence> adp_subcat;
	Spinner subcat;
	TextView itemCount;
	Bitmap src;
	ImageButton home;
	

	@Override
	public void onBackPressed() {
		Toast.makeText(getApplicationContext(), "Press Home Button To See Dasboard", Toast.LENGTH_SHORT).show();
	}
    
    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_browsing_content);
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy); 
		catagory = getIntent().getStringExtra("catagory");
		listView = (ListView) findViewById(R.id.browsingListview);
		subcat = (Spinner)findViewById(R.id.spinner_subcatagory);
		itemCount = (TextView)findViewById(R.id.textNoContent);
		imgFoodZone = (ImageView)findViewById(R.id.ftFoodZone);
		imgHangout = (ImageView)findViewById(R.id.ftHangout);
		imgShopping = (ImageView)findViewById(R.id.ftShopping);
		imgEvent = (ImageView)findViewById(R.id.ftEvent);
		imgOrg = (ImageView)findViewById(R.id.ftOrg);
		imgOther = (ImageView)findViewById(R.id.ftOther);
		home = (ImageButton)findViewById(R.id.actionBarHome);
		id="";
		city= "";
		title="";
		SharedPreferences prefCity;
		prefCity = getSharedPreferences("MyPrefs", 0);
		setSubCatagory();
		getContent();
		getActionBar().setTitle("Browse Experience");

		home.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Intent intObj = new Intent(getApplicationContext(),DashboardActivity.class);
				startActivity(intObj);
				finish();
								
			}
		});

		
		subcat.setOnItemSelectedListener(new OnItemSelectedListener() {

	        public void onItemSelected(AdapterView<?> parentView,
	                View selectedItemView, int position, long id) {
	        	if(adapter.getCount()!=0){
	        		adapter.clear();
	        	}
				getContent();
	        }
	        public void onNothingSelected(AdapterView<?> arg0) {// do nothing
	        }
	    });
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			  @Override
			  public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

			    Object o = listView.getItemAtPosition(position);
			    Bundle cat = new Bundle();
				cat.putString("con_id", o.toString());
				Intent intObj = new Intent(getApplicationContext(),ContentDisplayActivity.class);
				intObj.putExtras(cat);
				startActivity(intObj);
			  }
			});
		
		
		imgFoodZone.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle cat = new Bundle();
				cat.putString("catagory", "Food%20Zone");
				Intent intent = new Intent(getApplicationContext(), BrowsingContentActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(cat);
				startActivity(intent);
								
			}
		});
		
		imgHangout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle cat = new Bundle();
				cat.putString("catagory", "Hangout");
				Intent intent = new Intent(getApplicationContext(), BrowsingContentActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(cat);
				startActivity(intent);
								
			}
		});
		
		imgShopping.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle cat = new Bundle();
				cat.putString("catagory", "Shopping");
				Intent intent = new Intent(getApplicationContext(), BrowsingContentActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(cat);
				startActivity(intent);
								
			}
		});
		
		imgEvent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle cat = new Bundle();
				cat.putString("catagory", "Event");
				Intent intent = new Intent(getApplicationContext(), BrowsingContentActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(cat);
				startActivity(intent);
								
			}
		});
		
		imgOrg.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle cat = new Bundle();
				cat.putString("catagory", "Organization");
				Intent intent = new Intent(getApplicationContext(), BrowsingContentActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(cat);
				startActivity(intent);
								
			}
		});

		imgOther.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				Bundle cat = new Bundle();
				cat.putString("catagory", "Other");
				Intent intent = new Intent(getApplicationContext(), BrowsingContentActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				intent.putExtras(cat);
				startActivity(intent);
								
			}
		});
		
		
		    }
		
    	public void getContent(){
    		
    		try{
    			id_arr.clear();
    			city_arr.clear();
    			title_arr.clear();
    			SharedPreferences prefCity;
    			prefCity = getSharedPreferences("MyPrefs", 0);
    			subcategory = subcat.getSelectedItem().toString().replace(" ", "%20");
   				url = new URL("http://saheleecreation.in/android_cp/browseContent.php?catagory="+catagory+"&subcat="+subcategory+"&city="+prefCity.getString("keyCity", null));
   				HttpURLConnection connection = (HttpURLConnection)url.openConnection();
   				connection.setDoInput(true);
   				connection.setDoOutput(true);     
            
   				BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
   				String line = "";
   				while((line = reader.readLine()) != null){
    			
    				JSONArray object = new JSONArray(line);
    				
    				for(int len=0;len<object.length();len++){
    					tmpid =  object.getJSONObject(len).getString("id");
    					id_arr.add(tmpid);
    				}
    				
    				for(int len=0;len<object.length();len++){
    					tmpcity =  object.getJSONObject(len).getString("city");
    					tmpplace =  object.getJSONObject(len).getString("place");
    					city_arr.add(tmpplace+" > "+tmpcity); 
    				}
    				
    				for(int len=0;len<object.length();len++){
    					tmptitle =  object.getJSONObject(len).getString("title");
    					title_arr.add(tmptitle); 
    				}
    				
    					
   				}
   				adapter = new BrowsingContentCustomAdapter(getApplicationContext(), id_arr,title_arr,city_arr);
				listView.setAdapter(adapter);
				
				if(listView.getCount()==0){
					itemCount.setText("No Content To Display");
				}
				else{
					itemCount.setText("");
				}   				   				
    		}	
    		catch (Exception e) {
   				Toast.makeText(getApplicationContext(),"No Internet Connection...Try Again.", Toast.LENGTH_LONG).show();
    		}	
    	}
        	
    	public void getImage(){
    		try{ 			
    			URL url = new URL("http://saheleecreation.in/CityPediaNewSKV/user/uploads/"+tmpid);
    			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    			connection.setDoInput(true);
    			connection.connect();
    			InputStream input = connection.getInputStream();
    			src = BitmapFactory.decodeStream(input);
    			}
    			catch (Exception e) {
    				// TODO: handle exception
    			}
    	}
    		   
		 public void setSubCatagory(){
				if(catagory.equals("Hangout")){
						adp_subcat = ArrayAdapter.createFromResource(
				        this, R.array.hangout, android.R.layout.simple_spinner_item);		
				}
				else if(catagory.equals("Food%20Zone")){
					adp_subcat = ArrayAdapter.createFromResource(
					this, R.array.foodzone, android.R.layout.simple_spinner_item);
				}
				else if(catagory.equals("Shopping")){
					adp_subcat = ArrayAdapter.createFromResource(
					this, R.array.shopping, android.R.layout.simple_spinner_item);
				}
				else if(catagory.equals("Event")){
					adp_subcat = ArrayAdapter.createFromResource(
					this, R.array.event, android.R.layout.simple_spinner_item);
				}
				else if(catagory.equals("Organization")){
					adp_subcat = ArrayAdapter.createFromResource(
					this, R.array.organisation, android.R.layout.simple_spinner_item);
				}
				else if(catagory.equals("Other")){
					adp_subcat = ArrayAdapter.createFromResource(
					this, R.array.other, android.R.layout.simple_spinner_item);
				}
				else{
					adp_subcat = ArrayAdapter.createFromResource(
				    this, R.array.other, android.R.layout.simple_spinner_item);
				}
				
				adp_subcat.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				subcat.setAdapter(adp_subcat);
			}			
  
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.browsing_content, menu);
		return true;
	}

}
