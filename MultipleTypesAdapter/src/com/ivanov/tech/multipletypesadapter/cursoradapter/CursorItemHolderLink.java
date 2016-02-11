package com.ivanov.tech.multipletypesadapter.cursoradapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.ivanov.tech.multipletypesadapter.R;
import com.ivanov.tech.multipletypesadapter.demo.FragmentDemo;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CursorItemHolderLink extends CursorItemHolder {

	private static final String TAG = CursorItemHolderLink.class.getSimpleName();
	
	OnClickListener onclicklistener=null;
	
	TextView textview_name,textview_status,textview_label;
	ImageView imageview_icon;
	Button button;	
	
	public CursorItemHolderLink(Context context, OnItemClickListener onitemclicklistener,OnClickListener onclicklistener) {
		this.context=context;
		this.onitemclicklistener=onitemclicklistener;
		this.onclicklistener=onclicklistener;
	}
	
	public CursorItemHolderLink createClone(){	
		Log.d(TAG, "createClone");
		return new CursorItemHolderLink(context,onitemclicklistener,onclicklistener);
	}
	
	@Override
	public View getView(View convertView, ViewGroup parent, Cursor cursor) {
				
		View view;
		
		if(convertView==null){
		
			LayoutInflater layoutinflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			view= layoutinflater.inflate(R.layout.details_item_link, parent, false);
		
			textview_name = (TextView) view.findViewById(R.id.details_item_link_name);
	        textview_status = (TextView) view.findViewById(R.id.details_item_link_status);
	        textview_label = (TextView) view.findViewById(R.id.details_item_link_label);
	        imageview_icon = (ImageView) view.findViewById(R.id.details_item_link_icon);
	        button = (Button) view.findViewById(R.id.details_item_link_button);
        
		}else{
			view=convertView;
		}
		
        JSONObject json;
		try {
			json = new JSONObject(CursorMultipleTypesAdapter.getValue(cursor));
			
			Log.d(TAG, "getView TYPE_LINK_USER json="+json+"imageview_icon.id");
			
			if(!json.isNull("name"))
				textview_name.setText(json.getString("name"));
			else 
				textview_name.setText(json.getString(" "));
			
			if(!json.isNull("status")){
				textview_status.setVisibility(View.VISIBLE);
				textview_status.setText(json.getString("status"));				
			}else{
				textview_status.setVisibility(View.GONE);			
			}
			
			if(!json.isNull("label")){
				textview_label.setVisibility(View.VISIBLE);
				textview_label.setText(json.getString("label"));				
			}else{
				textview_label.setVisibility(View.GONE);			
			}
			
			
			if(!json.isNull("button")){
				button.setVisibility(View.VISIBLE);
				button.setText(json.getString("button_text"));
				button.setTag(json.getString("button"));
				button.setTag(button.getId(), CursorMultipleTypesAdapter.getKey(cursor));	
				
				if(onclicklistener!=null)
					button.setOnClickListener(onclicklistener);
				
			}else{
				button.setVisibility(View.GONE);
			}
						
			Glide.clear(imageview_icon);
			if(!json.isNull("url_icon"))
				Glide.with(context).load(json.getString("url_icon")).error(R.drawable.ic_no_icon).placeholder(R.drawable.ic_no_icon).into(imageview_icon);
			else
				imageview_icon.setImageResource(R.drawable.ic_no_icon);
										
		} catch (JSONException e) {
			Log.e(TAG, "getView TYPE_LINK_USER JSONException e="+e);
		}
		
		
		return view;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	

}
