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

public class CursorItemHolderLinkVertical extends CursorItemHolder {

	private static final String TAG = CursorItemHolderLinkVertical.class.getSimpleName();
	
	
	TextView textview_name;
	ImageView imageview_icon;
	TextView textview_label;
	
	public CursorItemHolderLinkVertical(Context context, OnItemClickListener onitemclicklistener) {
		this.context=context;
		this.onitemclicklistener=onitemclicklistener;
	}
	
	public CursorItemHolderLinkVertical createClone(){	
		Log.d(TAG, "createClone");
		return new CursorItemHolderLinkVertical(context,onitemclicklistener);
	}
	
	@Override
	public View getView(View convertView, ViewGroup parent, Cursor cursor) {
				
		View view;
		
		if(convertView==null){
		
			LayoutInflater layoutinflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			view= layoutinflater.inflate(R.layout.details_item_grid_users_gridview_item, parent, false);
		
			textview_name = (TextView) view.findViewById(R.id.details_item_grid_users_gridview_item_textview);
			textview_label = (TextView) view.findViewById(R.id.details_item_grid_users_gridview_item_textview_label);
	        imageview_icon = (ImageView) view.findViewById(R.id.details_item_grid_users_gridview_item_imageview);
        
		}else{
			view=convertView;
		}
		
        JSONObject json;
		try {
			json = new JSONObject(CursorMultipleTypesAdapter.getValue(cursor));
			
			//Log.d(TAG, "getView TYPE_LINK_VERTICAL json="+json);
			
			if(new BinderTextView(context,new JSONObject("{text_color:'"+R.color.color_gray_medial+"'}")).bind(textview_name, json.getJSONObject("name"))){	
			}
			
			if(new BinderTextView(context,new JSONObject("{text_color:'"+R.color.color_green+"', visible:false}")).bind(textview_label, json.getJSONObject("label"))){	
			}
			
			if(new BinderImageView(context,new JSONObject("{image_res:'"+R.drawable.ic_no_icon+"'}")).bind(imageview_icon, json.getJSONObject("icon"))){			
			}										
										
		} catch (JSONException e) {
			Log.e(TAG, "getView TYPE_LINK_VERTICAL JSONException e="+e);
		}
		
		
		return view;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	

}
