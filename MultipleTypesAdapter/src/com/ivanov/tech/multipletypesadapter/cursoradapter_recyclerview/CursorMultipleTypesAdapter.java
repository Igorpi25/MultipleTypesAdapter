package com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview;

import java.util.HashMap;

import com.ivanov.tech.multipletypesadapter.ItemHolder;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

public class CursorMultipleTypesAdapter extends CursorRecyclerViewAdapter<CursorItemHolder> {

	private static final String TAG = CursorMultipleTypesAdapter.class.getSimpleName();	
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_KEY = "key";
	public static final String COLUMN_VALUE = "value";  	
	
	public HashMap<Integer,CursorItemHolder> hashmap=new HashMap<Integer,CursorItemHolder>();
		
	public CursorMultipleTypesAdapter(Context context, Cursor cursor) {
		super(context, cursor);
		// TODO Auto-generated constructor stub
	}
	
	public void addItemHolder(int type,CursorItemHolder viewholder){
		hashmap.put(type, viewholder);
	}

	@Override
	public void onBindViewHolder(CursorItemHolder itemHolder, Cursor cursor) {
		Log.d(TAG, "onBindViewHolder");	
		itemHolder.bindView(cursor);
	}
	
	@Override
	public CursorItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		Log.d(TAG, "onCreateViewHolder");		
		return hashmap.get(viewType).createClone(parent);
	}
	
	@Override
	public int getItemViewType(int position){
		getCursor().moveToPosition(position);		
		return getType(getCursor());
	}
	
	//-------------Cursor Getters Utilities----------------------
	
	public static int getType(Cursor cursor){
    	return cursor.getInt(cursor.getColumnIndex(COLUMN_TYPE));
    }
    
    public static int getId(Cursor cursor){
    	return cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
    }
    
    public static int getKey(Cursor cursor){
    	return cursor.getInt(cursor.getColumnIndex(COLUMN_KEY));
    }
    
    public static String getValue(Cursor cursor){
    	return cursor.getString(cursor.getColumnIndex(COLUMN_VALUE));
    }


	
}
