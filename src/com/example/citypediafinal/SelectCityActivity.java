package com.example.citypediafinal;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class SelectCityActivity extends Activity {
	
	Spinner city;
	Button select;
	TextView txt;
	public static final String MyPREFERENCES = "MyPrefs" ;
	public static final String Name = "keyCity";
	int flag=0;
	SharedPreferences prefCity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_city);
	    this.setFinishOnTouchOutside(false);
	    this.setTitle("Choose Your City");
	    

		city = (Spinner)findViewById(R.id.spinnerCity);
		select = (Button)findViewById(R.id.btnSelectCity);
		

		
			
		select.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(city.getSelectedItem().toString().equals("Select City")){
					Toast.makeText(getApplicationContext(), "Please Choose Your City", Toast.LENGTH_SHORT).show();
					flag=1;
				}
				else{
					flag=0;
				}
				
				if(flag==0){
					SharedPreferences settings = getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
					SharedPreferences.Editor editor = settings.edit();
					editor.putString(Name,city.getSelectedItem().toString());
					editor.commit(); 
			    	finish();	
				}
				}
			
		});
		
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_city, menu);
		return true;
	}

}
