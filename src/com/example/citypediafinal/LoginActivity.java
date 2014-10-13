package com.example.citypediafinal;

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
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



public class LoginActivity extends BaseActivity {

	TextView signup;
	ImageView abt;
	EditText uname,upass;
	Button login;
	HttpPost httppost;
    StringBuffer buffer;
    HttpResponse response;
    HttpClient httpclient;
    ArrayList<NameValuePair> data;
    ProgressDialog progress;
    public static final String MyPREFERENCES = "MyPrefs" ;
    ResponseHandler<String> responseHandler;
	SharedPreferences prefLogin;



	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);		
		setTitle("CityPedia Login");
		abt = (ImageView)findViewById(R.id.actionBarLogo);
		signup = (TextView)findViewById(R.id.textSignup);
		uname = (EditText)findViewById(R.id.editEmail);
		upass = (EditText)findViewById(R.id.editPassword);
		login = (Button)findViewById(R.id.btnLogin);
		setTitle("CityPedia Login");
		SharedPreferences prefChkLogin;

		
		prefChkLogin = getSharedPreferences("MyPrefs", 0);

		if(prefChkLogin.contains("keyUname")){
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
		}
	
		login.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				login();	
			}
		});
		
		abt.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intObj = new Intent(getApplicationContext(),AboutActivity.class);
				startActivity(intObj);
				
			}
		});
		
		
		signup.setOnClickListener(new OnClickListener() {

	        @Override
	        public void onClick(View v) {
	          Intent intObj = new Intent(getApplicationContext(),RegistrationActivity.class);
	          startActivity(intObj);
	        }
	    });
	}
	
	void login(){
		
        try{ 
              
            httpclient=new DefaultHttpClient();
			httppost = new HttpPost("http://saheleecreation.in/android_cp/login.php");
            data = new ArrayList<NameValuePair>();
            data.add(new BasicNameValuePair("uname",uname.getText().toString().trim()));  
            data.add(new BasicNameValuePair("upass",upass.getText().toString().trim()));
           
            httppost.setEntity(new UrlEncodedFormEntity(data));
            response=httpclient.execute(httppost);
            
            responseHandler = new BasicResponseHandler();
	          final String response = httpclient.execute(httppost, responseHandler);
	    	  
	    	  if(response.equalsIgnoreCase("success")){
	                
	    		  SharedPreferences settings = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
	    		  SharedPreferences.Editor editor = settings.edit();
	    		  editor.putString("keyUname",uname.getText().toString().trim());
	    		  editor.commit(); 
			      Toast.makeText(LoginActivity.this,"Welcome To Citypedia..!!", Toast.LENGTH_SHORT).show();
         
			      startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
			      finish();
	    	  }
	    	  else if(response.equalsIgnoreCase("block")){
	    		  showAlert1();                
	    	  }
	    	  else{
	    		  showAlert();
	    	  }    
                   
        }
        catch(Exception e){    
            System.out.println("Exception : " + e.getMessage());
        }
        	 
    }
	
    public void showAlert(){
        LoginActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("CityPedia");
                builder.setMessage("Incorrect Username or Password")  
                       .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
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
    
    public void showAlert1(){
        LoginActivity.this.runOnUiThread(new Runnable() {
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                builder.setTitle("CityPedia");
                builder.setMessage("You are blocked on CityPedia. Contact Administrator")  
                       
                       .setPositiveButton("Retry", new DialogInterface.OnClickListener() {
                           public void onClick(DialogInterface dialog, int id) {
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
    public void onBackPressed() {
    	Intent intent = new Intent(Intent.ACTION_MAIN);
    	intent.addCategory(Intent.CATEGORY_HOME);
    	intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	startActivity(intent);	
        super.onBackPressed();
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.login, menu);
		return true;
	}

}
