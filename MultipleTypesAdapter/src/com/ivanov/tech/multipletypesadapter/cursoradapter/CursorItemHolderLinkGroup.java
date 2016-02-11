package com.ivanov.tech.multipletypesadapter.cursoradapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.ivanov.tech.multipletypesadapter.ItemHolder;
import com.ivanov.tech.multipletypesadapter.R;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CursorItemHolderLinkGroup extends CursorItemHolder{

	private String TAG=CursorItemHolderLinkGroup.class.getSimpleName();
	
	TextView textview_name,textview_status,textview_label;
	ImageView imageview_icon;
	
	public CursorItemHolderLinkGroup(Context context, OnItemClickListener onitemclicklistener) {
		this.context=context;
		this.onitemclicklistener=onitemclicklistener;
	}
	
	@Override
	public ItemHolder<Cursor> createClone() {
		
		return new CursorItemHolderLinkGroup(context,onitemclicklistener);
	}

	@Override
	public View getView(View convertView, ViewGroup parent, Cursor cursor) {
				
		View view;
		
		if(convertView==null){
		
			LayoutInflater layoutinflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			view= layoutinflater.inflate(R.layout.details_item_link_group, parent, false);
		
			textview_name = (TextView) view.findViewById(R.id.details_item_link_group_name);
	        textview_status = (TextView) view.findViewById(R.id.details_item_link_group_status);
	        textview_label = (TextView) view.findViewById(R.id.details_item_link_group_label);
	        imageview_icon = (ImageView) view.findViewById(R.id.details_item_link_group_icon);
        
		}else{
			view=convertView;
		}
		
		
        
        JSONObject json;
		try {
			json = new JSONObject(CursorMultipleTypesAdapter.getValue(cursor));
			
			Log.d(TAG, "bindView TYPE_LINK_GROUP json="+json);
		
			if(!json.isNull("name"))
				textview_name.setText(json.getString("name"));
			else 
				textview_name.setText(json.getString(" "));
			
			if(!json.isNull("status"))
				textview_status.setText(json.getString("status"));
			else
				textview_status.setText(context.getResources().getStringArray(R.array.status_array)[0]);
			
			
			Glide.clear(imageview_icon);
			if(!json.isNull("url_icon"))
				Glide.with(context).load(json.getString("url_icon")).error(R.drawable.ic_no_icon).placeholder(R.drawable.ic_no_icon).into(imageview_icon);
			else imageview_icon.setImageResource(R.drawable.ic_no_icon);
			
		} catch (JSONException e) {
			Log.e(TAG, "bindView TYPE_LINK_USER JSONException e="+e);
		}
		
		
		return view;
	}

	@Override
	public boolean isEnabled() {
		
		return true;
	}

	
	

}
