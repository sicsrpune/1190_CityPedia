package com.example.citypediafinal;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ContentDisplayCustomAdapter extends ArrayAdapter<String> {
	private final Context context;
	private ArrayList<String> comment = new ArrayList<String>(); 
	 	

	
	public ContentDisplayCustomAdapter(Context context, ArrayList<String> comment){
		super(context, R.layout.listview_display_content, comment);

		
		this.context = context;
		this.comment = comment;
		
	}
	
	
	
	
	@Override
	public View getView(int position, View view, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.listview_display_content, null);
		final TextView  tview = (TextView) rowView.findViewById(R.id.textListComm);
		
		
		tview.setText(comment.get(position));
		
		//q1.setImageBitmap(pic.get(position));

		return rowView;
	}
	
	
	
}
