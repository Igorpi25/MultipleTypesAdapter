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

public class ItemHolderCardProduct extends CursorItemHolder{
	
	private static final String TAG = ItemHolderCardProduct.class.getSimpleName();
	
	TextView textview_title,textview_text,textview_price;
	ImageView imageview_icon;
	Button button_order;
	
	OnClickListener onclicklistener;
	
	protected ItemHolderCardProduct(View itemView,Context context, OnClickListener onclicklistener) {
		super(itemView);
		
		this.context=context;
		this.onclicklistener=onclicklistener;
		
	}
	
	public ItemHolderCardProduct(Context context, OnClickListener onclicklistener) {
		super(new View(context));
		
		this.context=context;
		this.onclicklistener=onclicklistener;		
	}
	
	@Override
	public CursorItemHolder createClone(ViewGroup parent) {	
		
		Log.d(TAG, "createClone");
		
		View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product, parent, false);
		
		ItemHolderCardProduct itemholder=new ItemHolderCardProduct(view,context,onclicklistener);
		
		itemholder.textview_title = (TextView) view.findViewById(R.id.product_title);
		itemholder.textview_text = (TextView) view.findViewById(R.id.product_text);
		itemholder.textview_price = (TextView) view.findViewById(R.id.product_price);
		itemholder.imageview_icon = (ImageView) view.findViewById(R.id.product_icon);
		itemholder.button_order = (Button) view.findViewById(R.id.product_button_order);
		
		if(itemholder.onclicklistener!=null){
			view.setOnClickListener(itemholder.onclicklistener);
			itemholder.button_order.setOnClickListener(itemholder.onclicklistener);
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
			new BinderTextView(context).bindText(textview_text, json.getJSONObject("text"));			
			new BinderTextView(context).bindText(textview_price, json.getJSONObject("price"));
			
			if(new BinderImageView(context).bind(imageview_icon, json.getJSONObject("icon"))){	
			}
			
			button_order.setTag(button_order.getId(), CursorMultipleTypesAdapter.getKey(cursor));
			
			itemView.setTag(itemView.getId(), CursorMultipleTypesAdapter.getKey(cursor));				
									
		} catch (JSONException e) {
			Log.e(TAG, "bindView JSONException e="+e);
		}
	}


}
