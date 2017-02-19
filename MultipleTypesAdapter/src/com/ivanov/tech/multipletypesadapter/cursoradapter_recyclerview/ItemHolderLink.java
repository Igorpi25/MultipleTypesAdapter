package com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview;

import org.json.JSONException;
import org.json.JSONObject;

import com.ivanov.tech.multipletypesadapter.BinderButton;
import com.ivanov.tech.multipletypesadapter.BinderImageView;
import com.ivanov.tech.multipletypesadapter.BinderTextView;
import com.ivanov.tech.multipletypesadapter.R;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderLink;
import com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview.CursorMultipleTypesAdapter;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemHolderLink extends CursorItemHolder{
	
	private static final String TAG = ItemHolderLink.class.getSimpleName();
	
	TextView textview_name,textview_status,textview_label;
	ImageView imageview_icon;
	Button button;
	
	OnClickListener onclicklistener;
	
	protected ItemHolderLink(View itemView,Context context, OnClickListener onclicklistener) {
		super(itemView);
		
		this.context=context;
		this.onclicklistener=onclicklistener;
		
	}
	
	public ItemHolderLink(Context context, OnClickListener onclicklistener) {
		super(new View(context));
		
		this.context=context;
		this.onclicklistener=onclicklistener;		
	}
	
	@Override
	public CursorItemHolder createClone(ViewGroup parent) {	
		
		//Log.d(TAG, "createClone");
		
		View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.details_item_link, parent, false);
		
		ItemHolderLink itemholder=new ItemHolderLink(view,context,onclicklistener);
		
		itemholder.textview_name = (TextView) view.findViewById(R.id.details_item_link_name);
		itemholder.textview_status = (TextView) view.findViewById(R.id.details_item_link_status);
		itemholder.textview_label = (TextView) view.findViewById(R.id.details_item_link_label);
		itemholder.imageview_icon = (ImageView) view.findViewById(R.id.details_item_link_icon);
		itemholder.button = (Button) view.findViewById(R.id.details_item_link_button);
        
		if(itemholder.onclicklistener!=null){
			view.setOnClickListener(itemholder.onclicklistener);			
			itemholder.button.setOnClickListener(itemholder.onclicklistener);
		}
		
		
		return itemholder;
	}

	@Override
	public void bindView(Cursor cursor) {
		JSONObject json;
		try {
			json = new JSONObject(CursorMultipleTypesAdapter.getValue(cursor));
			
			//Log.d(TAG, "bindView json="+json);
			
			new BinderTextView(context).bind(textview_name, json.getJSONObject("name"));
			
			new BinderTextView(context).bind(textview_status, json.getJSONObject("status"));
			
			new BinderTextView(context).bind(textview_label, json.getJSONObject("label"));
		
			if(new BinderButton(context,new JSONObject("{text_size:12, text_color:'"+R.color.color_selector_font+"'}")).bind(button, json.getJSONObject("button"))){		
				
				button.setTag(button.getId(), CursorMultipleTypesAdapter.getKey(cursor));				
				
			}			
				
			new BinderImageView(context).bind(imageview_icon, json.getJSONObject("icon"));
			
			itemView.setTag(R.layout.details_item_link, CursorMultipleTypesAdapter.getKey(cursor));				
									
		} catch (JSONException e) {
			Log.e(TAG, "bindView ItemHolderLink JSONException e="+e);
		}
	}


}
