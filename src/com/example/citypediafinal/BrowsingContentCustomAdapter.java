package com.example.citypediafinal;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class BrowsingContentCustomAdapter extends ArrayAdapter<String> {
	private final Context context;
	private ArrayList<String> title = new ArrayList<String>(); 
	private ArrayList<String> city = new ArrayList<String>(); 
	ArrayList<String> id = new ArrayList<String>();
	//ArrayList<Bitmap> pic = new ArrayList<Bitmap>();
	
	

	
	public BrowsingContentCustomAdapter(Context context, ArrayList<String> id, ArrayList<String> title, ArrayList<String> city){
		super(context, R.layout.listview_browsing_content, id);

		
		this.context = context;
		this.id = id;
		this.title=title;
		this.city = city;
	//	this.pic = pic;
		
		
	}
	
	
	
	
	@Override
	public View getView(int position, View view, ViewGroup parent){
		LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View rowView = inflater.inflate(R.layout.listview_browsing_content, null);
		final TextView  tview = (TextView) rowView.findViewById(R.id.textViewBr1);
		final TextView  tview1 = (TextView) rowView.findViewById(R.id.textViewBr2);
		final TextView  tview2 = (TextView) rowView.findViewById(R.id.content_id);
		//final QuickContactBadge q1 = (QuickContactBadge) rowView.findViewById(R.id.con_pic);
		tview.setText("");
		tview1.setText("");
		tview2.setText("");
		tview.setText(title.get(position));
		tview1.setText(city.get(position));
		tview2.setText(id.get(position));
		//q1.setImageBitmap(pic.get(position));

		return rowView;
	}
	
	
	
}
