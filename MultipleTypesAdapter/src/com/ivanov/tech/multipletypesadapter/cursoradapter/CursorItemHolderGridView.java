package com.ivanov.tech.multipletypesadapter.cursoradapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.ivanov.tech.multipletypesadapter.BinderButton;
import com.ivanov.tech.multipletypesadapter.R;
import com.ivanov.tech.multipletypesadapter.demo.FragmentDemo;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CursorItemHolderGridView extends CursorItemHolder {

	private static final String TAG = CursorItemHolderGridView.class.getSimpleName();
	
	int layout_id, gridview_in_layout_id;
	
	public static int TYPE_GRIDVIEW_ITEM=0;
	
    protected CursorMultipleTypesAdapter adapter=null;
    Cursor cursor;
	
	GridView gridview;	
	
	
	public CursorItemHolderGridView(Context context,Cursor cursor, OnItemClickListener onitemclicklistener) {
		this.context=context;
		this.cursor=cursor;
		this.onitemclicklistener=onitemclicklistener;
		this.layout_id=R.layout.details_item_grid_users;
		this.gridview_in_layout_id=R.id.details_item_grid_users_gridview;
	}
	
	public CursorItemHolderGridView(Context context, Cursor cursor, int layout_id, int button_in_layout_id, OnItemClickListener onitemclicklistener) {
		this.context=context;	
		this.cursor=cursor;
		this.onitemclicklistener=onitemclicklistener;
		this.layout_id=layout_id;
		this.gridview_in_layout_id=button_in_layout_id;
	}
	
	public CursorItemHolderGridView createClone(){	
		Log.d(TAG, "createClone");
		return new CursorItemHolderGridView(context,cursor,layout_id,gridview_in_layout_id,onitemclicklistener);
	}
	
	@Override
	public View getView(View convertView, ViewGroup parent, Cursor cursor) {
				
		View view;
		
		if(convertView==null){
		
			LayoutInflater layoutinflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			
			view= layoutinflater.inflate(layout_id, parent, false);
		
			gridview = (GridView) view.findViewById(gridview_in_layout_id);
			
		}else{
			view=convertView;
		}
		
		Log.d(TAG, "getView TYPE_GRIDVIEW");			
		
		int width = convertDpToPixels((76*4),context);
        int height = convertDpToPixels(93*(this.cursor.getCount()/4),context);
                
        if(this.cursor.getCount()%4 == 1)
        	height=height+convertDpToPixels(93,context);
        if(this.cursor.getCount()%4 > 1) 
        	height=height+convertDpToPixels(93,context);
        
        LinearLayout.LayoutParams gridLayoutParams = new LinearLayout.LayoutParams(width, height);
		
		gridview.setLayoutParams(gridLayoutParams);
		
		adapter=new CursorMultipleTypesAdapter(context,this.cursor,adapter.FLAG_AUTO_REQUERY);
	        
		//Prepare map of types and set listeners for them        
		adapter.addItemHolder(TYPE_GRIDVIEW_ITEM, new CursorItemHolderLinkVertical(context,this));    
	        
	    gridview.setAdapter(adapter);	        
	    gridview.setOnItemClickListener(adapter);
	        
		return view;
	}

	@Override
	public boolean isEnabled() {
		return false;
	}
	
//-------------Utils----------------------
    
    public static int convertDpToPixels(float dp, Context context){
        Resources resources = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp, 
                resources.getDisplayMetrics()
        );
    }
	

}