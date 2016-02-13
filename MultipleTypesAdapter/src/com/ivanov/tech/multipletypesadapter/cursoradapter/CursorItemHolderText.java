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

public class CursorItemHolderText extends CursorItemHolder {

	private static final String TAG = CursorItemHolderText.class.getSimpleName();
	
	
	TextView textview_key,textview_value;
	ImageView imageview_icon;
	
	public CursorItemHolderText(Context context, OnItemClickListener onitemclicklistener) {
		this.context=context;
		this.onitemclicklistener=onitemclicklistener;
	}
	
	public CursorItemHolderText createClone(){	
		Log.d(TAG, "createClone");
		return new CursorItemHolderText(context,onitemclicklistener);
	}
	
	@Override
	public View getView(View convertView, ViewGroup parent, Cursor cursor) {
				
		View view;
		
		if(convertView==null){
		
			LayoutInflater layoutinflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			view= layoutinflater.inflate(R.layout.details_item_text, parent, false);
		
			textview_key = (TextView) view.findViewById(R.id.details_item_text_key);
	        textview_value = (TextView) view.findViewById(R.id.details_item_text_value);
	        imageview_icon = (ImageView) view.findViewById(R.id.details_item_text_icon);
        
		}else{
			view=convertView;
		}
		
        JSONObject json;
		try {
			json = new JSONObject(CursorMultipleTypesAdapter.getValue(cursor));
			
			Log.d(TAG, "getView TYPE_TEXT json="+json);
			
			if(!json.isNull("key")){
				textview_key.setVisibility(View.VISIBLE);
				textview_key.setText(json.getString("key"));
			}else{ 
				textview_key.setVisibility(View.GONE);
			}
			
			if(!json.isNull("value")){
				textview_value.setVisibility(View.VISIBLE);
				textview_value.setText(json.getString("value"));
			}else{ 
				textview_value.setVisibility(View.GONE);
			}
			
			
			if( (!json.isNull("icon")) && (json.getBoolean("icon")) ){
				imageview_icon.setVisibility(View.VISIBLE);
			}else{
				imageview_icon.setVisibility(View.GONE);
			}
			
			int icon_id=R.drawable.ic_item_more;
			if(!json.isNull("res_icon")){
				icon_id=json.getInt("res_icon");
			}
			
			Glide.clear(imageview_icon);
			if(!json.isNull("url_icon")){				
				Glide.with(context).load(json.getString("url_icon")).error(icon_id).placeholder(icon_id).into(imageview_icon);			
			} else if(!json.isNull("res_icon")){
				imageview_icon.setImageResource(icon_id);
			}
										
										
		} catch (JSONException e) {
			Log.e(TAG, "getView TYPE_TEXT JSONException e="+e);
		}
		
		
		return view;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
	

}
