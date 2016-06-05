package com.ivanov.tech.multipletypesadapter.cursoradapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.ivanov.tech.multipletypesadapter.BinderButton;
import com.ivanov.tech.multipletypesadapter.R;
import com.ivanov.tech.multipletypesadapter.demo.FragmentDemo;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class CursorItemHolderButton extends CursorItemHolder {

	private static final String TAG = CursorItemHolderButton.class.getSimpleName();
	
	OnClickListener onclicklistener=null;
	
	int layout_id, button_in_layout_id;
	
	Button button;	
	
	public CursorItemHolderButton(Context context, OnClickListener onclicklistener) {
		this.context=context;
		this.onclicklistener=onclicklistener;		
		this.layout_id=R.layout.details_item_button;
		this.button_in_layout_id=R.id.details_item_button_button;
	}
	
	public CursorItemHolderButton(Context context, int layout_id, int button_in_layout_id, OnClickListener onclicklistener) {
		this.context=context;
		this.onclicklistener=onclicklistener;		
		this.layout_id=layout_id;
		this.button_in_layout_id=button_in_layout_id;
	}
	
	public CursorItemHolderButton createClone(){	
		Log.d(TAG, "createClone");
		return new CursorItemHolderButton(context,layout_id,button_in_layout_id,onclicklistener);
	}
	
	@Override
	public View getView(View convertView, ViewGroup parent, Cursor cursor) {
				
		View view;
		
		if(convertView==null){
		
			LayoutInflater layoutinflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			view= layoutinflater.inflate(layout_id, parent, false);
		
	        button = (Button) view.findViewById(button_in_layout_id);
        
		}else{
			view=convertView;
		}
		
        JSONObject json;
		try {
			json = new JSONObject(CursorMultipleTypesAdapter.getValue(cursor));

			//Log.d(TAG, "getView TYPE_TEXT json="+json);			
			
			if(new BinderButton(context).bind(button, json.getJSONObject("button"))){
				button.setTag(button.getId(), CursorMultipleTypesAdapter.getKey(cursor));				
				if(onclicklistener!=null)
					button.setOnClickListener(onclicklistener);
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
