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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemHolderCardProductShoppingCart extends CursorItemHolder{
	
	private static final String TAG = ItemHolderCardProductShoppingCart.class.getSimpleName();
	
	TextView 
		textview_title,textview_title_count,textview_text,textview_price, 
		textview_amount_value, textview_amount_unit, 
		textview_count_value;
	
	ImageView imageview_icon;
	View button_edit,button_remove;
	
	OnClickListener onclicklistener;
	
	protected ItemHolderCardProductShoppingCart(View itemView,Context context, OnClickListener onclicklistener) {
		super(itemView);
		
		this.context=context;
		this.onclicklistener=onclicklistener;
		
	}
	
	public ItemHolderCardProductShoppingCart(Context context, OnClickListener onclicklistener) {
		super(new View(context));
		
		this.context=context;
		this.onclicklistener=onclicklistener;		
	}
	
	@Override
	public CursorItemHolder createClone(ViewGroup parent) {	
		
		Log.d(TAG, "createClone");
		
		View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_shopping_cart, parent, false);
		
		ItemHolderCardProductShoppingCart itemholder=new ItemHolderCardProductShoppingCart(view,context,onclicklistener);
		
		itemholder.textview_title = (TextView) view.findViewById(R.id.product_shopping_cart_title);
		itemholder.textview_title_count = (TextView) view.findViewById(R.id.product_shopping_cart_title_count);
		
		itemholder.textview_text = (TextView) view.findViewById(R.id.product_shopping_cart_text);
		itemholder.textview_price = (TextView) view.findViewById(R.id.product_shopping_cart_price);
		
		itemholder.textview_amount_value = (TextView) view.findViewById(R.id.product_shopping_cart_amount_value);
		itemholder.textview_amount_unit = (TextView) view.findViewById(R.id.product_shopping_cart_amount_unit);
		itemholder.textview_count_value = (TextView) view.findViewById(R.id.product_shopping_cart_count_value);
		
		itemholder.imageview_icon = (ImageView) view.findViewById(R.id.product_shopping_cart_icon);
		itemholder.button_edit =  view.findViewById(R.id.product_shopping_cart_button_edit);
		itemholder.button_remove =  view.findViewById(R.id.product_shopping_cart_button_remove);
				
		if(itemholder.onclicklistener!=null){
			view.setOnClickListener(itemholder.onclicklistener);
			itemholder.button_edit.setOnClickListener(itemholder.onclicklistener);
			itemholder.button_remove.setOnClickListener(itemholder.onclicklistener);
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
			new BinderTextView(context).bindText(textview_title_count, json.getJSONObject("count"));
			
			new BinderTextView(context).bindText(textview_text, json.getJSONObject("text"));			
			new BinderTextView(context).bindText(textview_price, json.getJSONObject("price"));
			
			new BinderTextView(context).bindText(textview_amount_value, json.getJSONObject("amount"));
			new BinderTextView(context).bindText(textview_count_value, json.getJSONObject("count"));
			
			if(new BinderImageView(context).bind(imageview_icon, json.getJSONObject("icon"))){	
			}
			
			button_edit.setTag(button_edit.getId(), CursorMultipleTypesAdapter.getKey(cursor));
			
			itemView.setTag(itemView.getId(), CursorMultipleTypesAdapter.getKey(cursor));				
									
		} catch (JSONException e) {
			Log.e(TAG, "bindView JSONException e="+e);
		}
		
	}


}
