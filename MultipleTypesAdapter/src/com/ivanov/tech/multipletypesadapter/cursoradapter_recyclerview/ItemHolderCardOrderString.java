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

public class ItemHolderCardOrderString extends CursorItemHolder{
	
	private static final String TAG = ItemHolderCardOrderString.class.getSimpleName();
	
	TextView textview_title,textview_count,textview_total;
	
	OnClickListener onclicklistener;
	
	protected ItemHolderCardOrderString(View itemView,Context context, OnClickListener onclicklistener) {
		super(itemView);
		
		this.context=context;
		this.onclicklistener=onclicklistener;
		
	}
	
	public ItemHolderCardOrderString(Context context, OnClickListener onclicklistener) {
		super(new View(context));
		
		this.context=context;
		this.onclicklistener=onclicklistener;		
	}
	
	@Override
	public CursorItemHolder createClone(ViewGroup parent) {	
		
		Log.d(TAG, "createClone");
		
		View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.order_string, parent, false);
		
		ItemHolderCardOrderString itemholder=new ItemHolderCardOrderString(view,context,onclicklistener);
		
		itemholder.textview_title = (TextView) view.findViewById(R.id.order_string_title);
		itemholder.textview_count = (TextView) view.findViewById(R.id.order_string_count);
		itemholder.textview_total = (TextView) view.findViewById(R.id.order_string_total);
		
		if(itemholder.onclicklistener!=null){
			view.setOnClickListener(itemholder.onclicklistener);
		}
		
		return itemholder;
	}

	@Override
	public void bindView(Cursor cursor) {
		JSONObject json;
		try {
			json = new JSONObject(CursorMultipleTypesAdapter.getValue(cursor));
			
			Log.d(TAG, "bindView json="+json);
						
			new BinderTextView(context).bindText(textview_title, json.getJSONObject("title"));	
			new BinderTextView(context).bindText(textview_count, json.getJSONObject("count"));
			new BinderTextView(context).bindText(textview_total, json.getJSONObject("total"));
			
			itemView.setTag(itemView.getId(), CursorMultipleTypesAdapter.getKey(cursor));				
									
		} catch (JSONException e) {
			Log.e(TAG, "bindView JSONException e="+e);
		}
	}


}
