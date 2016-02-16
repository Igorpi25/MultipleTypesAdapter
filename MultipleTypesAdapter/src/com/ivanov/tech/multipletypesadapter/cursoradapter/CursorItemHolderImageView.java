package com.ivanov.tech.multipletypesadapter.cursoradapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.ivanov.tech.multipletypesadapter.BinderButton;
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

public class CursorItemHolderImageView extends CursorItemHolder {

	private static final String TAG = CursorItemHolderImageView.class.getSimpleName();

	int layout_id, imageview_in_layout_id;
	
	OnClickListener onclicklistener=null;
	
	ImageView imageview;
	
	public CursorItemHolderImageView(Context context, OnClickListener onclicklistener) {
		this.context=context;
		this.onitemclicklistener=onitemclicklistener;
		this.onclicklistener=onclicklistener;
		this.layout_id=R.layout.details_item_avatar;
		this.imageview_in_layout_id=R.id.details_item_avatar_imageview;
	}
	
	public CursorItemHolderImageView(Context context, int layout_id, int button_in_layout_id, OnClickListener onclicklistener) {
		this.context=context;
		this.onclicklistener=onclicklistener;		
		this.layout_id=layout_id;
		this.imageview_in_layout_id=button_in_layout_id;
	}
	
	public CursorItemHolderImageView createClone(){	
		//Log.d(TAG, "createClone");
		return new CursorItemHolderImageView(context,layout_id,imageview_in_layout_id,onclicklistener);
	}
	
	@Override
	public View getView(View convertView, ViewGroup parent, Cursor cursor) {
				
		View view;
		
		if(convertView==null){
			
			LayoutInflater layoutinflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			view= layoutinflater.inflate(layout_id, parent, false);
		
	        imageview = (ImageView) view.findViewById(imageview_in_layout_id);
        
		}else{
			view=convertView;
		}
		
        JSONObject json;
		try {
			json = new JSONObject(CursorMultipleTypesAdapter.getValue(cursor));
			
			//Log.d(TAG, "getView TYPE_LINK_USER json="+json+"imageview_icon.id");
			
			if(new BinderImageView(context).bind(imageview, json.getJSONObject("imageview"))){
				imageview.setTag(imageview.getId(), CursorMultipleTypesAdapter.getKey(cursor));				
				if(onclicklistener!=null)
					imageview.setOnClickListener(onclicklistener);
			}
										
		} catch (JSONException e) {
			Log.e(TAG, "getView TYPE_LINK_USER JSONException e="+e);
		}
		
		
		return view;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
	

}
