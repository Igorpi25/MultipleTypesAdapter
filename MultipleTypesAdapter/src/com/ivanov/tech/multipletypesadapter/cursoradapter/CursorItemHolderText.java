package com.ivanov.tech.multipletypesadapter.cursoradapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.ivanov.tech.multipletypesadapter.BinderImageView;
import com.ivanov.tech.multipletypesadapter.BinderTextView;
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
		//Log.d(TAG, "createClone");
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
			
			//Log.d(TAG, "getView TYPE_TEXT json="+json);
			
			if(new BinderTextView(context).bind(textview_key, json.getJSONObject("key"))){	
			}
			
			if(new BinderTextView(context).bind(textview_value, json.getJSONObject("value"))){
			}
			
			
			if(new BinderImageView(context,new JSONObject("{image_res:'"+R.drawable.ic_item_more+"'}")).bind(imageview_icon, json.getJSONObject("icon"))){			
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
