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

public class ItemHolderButton extends CursorItemHolder{
	
	private static final String TAG = ItemHolderButton.class.getSimpleName();

	int layout_id, button_in_layout_id;
	
	Button button;	
	OnClickListener onclicklistener;
		
	protected ItemHolderButton(View itemView,Context context, OnClickListener onclicklistener) {
		super(itemView);
		
		this.context=context;
		this.onclicklistener=onclicklistener;
		
	}
	
	public ItemHolderButton(Context context, OnClickListener onclicklistener) {
		super(new View(context));
		
		this.context=context;
		this.onclicklistener=onclicklistener;		
		this.layout_id=R.layout.details_item_button;
		this.button_in_layout_id=R.id.details_item_button_button;	
	}
	
	public ItemHolderButton(Context context, int layout_id, int button_in_layout_id, OnClickListener onclicklistener) {
		super(new View(context));
		
		this.context=context;
		this.onclicklistener=onclicklistener;		
		this.layout_id=layout_id;
		this.button_in_layout_id=button_in_layout_id;
	}
	
	@Override
	public CursorItemHolder createClone(ViewGroup parent) {	
		
		//Log.d(TAG, "createClone");
		
		View view = LayoutInflater.from(parent.getContext())
                .inflate(layout_id, parent, false);
		
		ItemHolderButton itemholder=new ItemHolderButton(view,context,onclicklistener);
		
		itemholder.button = (Button) view.findViewById(button_in_layout_id);
        
		if(itemholder.onclicklistener!=null){	
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
			
			button.setTag(button.getId(), CursorMultipleTypesAdapter.getKey(cursor));						
									
		} catch (JSONException e) {
			Log.e(TAG, "bindView ItemHolderButton JSONException e="+e);
		}
	}


}
