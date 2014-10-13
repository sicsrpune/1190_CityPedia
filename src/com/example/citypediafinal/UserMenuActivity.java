package com.example.citypediafinal;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

public class UserMenuActivity extends Activity {

	TextView notification;
	TextView profile;
	TextView trending;
	TextView logout;
	TextView history,changeCity,changePass;
    public static final String MyPREFERENCES = "MyPrefs" ;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_menu);
		
		profile = (TextView)findViewById(R.id.textProfile);
		changePass = (TextView)findViewById(R.id.textChangePass);
		logout = (TextView)findViewById(R.id.textLogout);
		changeCity = (TextView)findViewById(R.id.textChangeCity);

		changePass.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent obj = new Intent(getApplicationContext(),ChangePasswordActivity.class);
				startActivity(obj);
				finish();
				
			}
		});
		
		profile.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent obj = new Intent(getApplicationContext(),UpdateProfileActivity.class);
				startActivity(obj);
				finish();
				
			}
		});
		
		changeCity.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent obj = new Intent(getApplicationContext(),SelectCityActivity.class);
				startActivity(obj);
				finish();
				
			}
		});
		
		logout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				SharedPreferences prefLogin;
				prefLogin = getSharedPreferences("MyPrefs", 0);
				SharedPreferences settings = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
		        SharedPreferences.Editor editor = settings.edit();
				editor.remove("keyUname");
				editor.remove("keyUpass");
				editor.commit();
				Intent obj = new Intent(getApplicationContext(),LoginActivity.class);
				startActivity(obj);
				finish();
				
			}
		});	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.user_menu, menu);
		return true;
	}

}
