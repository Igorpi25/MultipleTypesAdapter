package com.ivanov.tech.multipletypesadapter.cursoradapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.ivanov.tech.multipletypesadapter.BinderTextView;
import com.ivanov.tech.multipletypesadapter.R;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

public class CursorItemHolderHeader extends CursorItemHolder {

	private static final String TAG = CursorItemHolderHeader.class.getSimpleName();
	
	
	TextView textview_key,textview_value,textview_label;
	
	public CursorItemHolderHeader(Context context, OnItemClickListener onitemclicklistener) {
		this.context=context;
		this.onitemclicklistener=onitemclicklistener;
	}
	
	public CursorItemHolderHeader createClone(){	
		//Log.d(TAG, "createClone");
		return new CursorItemHolderHeader(context,onitemclicklistener);
	}
	
	@Override
	public View getView(View convertView, ViewGroup parent, Cursor cursor) {
				
		View view;
		
		if(convertView==null){
		
			LayoutInflater layoutinflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			view= layoutinflater.inflate(R.layout.details_item_header, parent, false);
		
			textview_key = (TextView) view.findViewById(R.id.details_item_header_key);
	        textview_value = (TextView) view.findViewById(R.id.details_item_header_value);
	        textview_label = (TextView) view.findViewById(R.id.details_item_header_label);
        
		}else{
			view=convertView;
		}
		
        JSONObject json;
		try {
			json = new JSONObject(CursorMultipleTypesAdapter.getValue(cursor));
			
			//Log.d(TAG, "getView TYPE_TEXT json="+json);
			
			JSONObject json_default=new JSONObject("{text_size:12}");
			json_default.put("text_color", R.color.color_gray);
			
			if(new BinderTextView(context,json_default).bind(textview_key, json.getJSONObject("key"))){
			}
			
			if(new BinderTextView(context,json_default).bind(textview_value, json.getJSONObject("value"))){	
			}
			
			if(new BinderTextView(context,json_default).bind(textview_label, json.getJSONObject("label"))){	
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
