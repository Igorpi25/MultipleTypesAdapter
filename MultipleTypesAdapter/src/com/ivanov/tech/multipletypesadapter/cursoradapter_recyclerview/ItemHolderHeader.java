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

public class ItemHolderHeader extends CursorItemHolder{
	
	private static final String TAG = ItemHolderHeader.class.getSimpleName();
	
	TextView textview_key,textview_value,textview_label;
		
	protected ItemHolderHeader(View itemView,Context context) {
		super(itemView);
		
		this.context=context;
		
	}
	
	public ItemHolderHeader(Context context) {
		super(new View(context));
		
		this.context=context;
	}
	
	@Override
	public CursorItemHolder createClone(ViewGroup parent) {	
		
		//Log.d(TAG, "createClone");
		
		View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.details_item_header, parent, false);
		
		ItemHolderHeader itemholder=new ItemHolderHeader(view,context);
		
		itemholder.textview_key = (TextView) view.findViewById(R.id.details_item_header_key);
		itemholder.textview_value = (TextView) view.findViewById(R.id.details_item_header_value);
		itemholder.textview_label = (TextView) view.findViewById(R.id.details_item_header_label);
		
		
		return itemholder;
	}

	@Override
	public void bindView(Cursor cursor) {
		JSONObject json;
		try {
			json = new JSONObject(CursorMultipleTypesAdapter.getValue(cursor));
			
			//Log.d(TAG, "bindView json="+json);
			
			new BinderTextView(context).bind(textview_key, json.getJSONObject("key"));
			new BinderTextView(context).bind(textview_value, json.getJSONObject("value"));
			new BinderTextView(context).bind(textview_label, json.getJSONObject("label"));
		
		} catch (JSONException e) {
			Log.e(TAG, "bindView ItemHolderHeader JSONException e="+e);
		}
	}


}
