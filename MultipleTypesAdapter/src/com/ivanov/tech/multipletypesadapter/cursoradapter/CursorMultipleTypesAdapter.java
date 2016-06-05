package com.ivanov.tech.multipletypesadapter.cursoradapter;

import java.util.HashMap;
import com.ivanov.tech.multipletypesadapter.ItemHolder;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;


public class CursorMultipleTypesAdapter extends CursorAdapter implements OnItemClickListener{
	
	private static final String TAG = CursorMultipleTypesAdapter.class.getSimpleName();	
	
	public static final String COLUMN_ID = "_id";
	public static final String COLUMN_TYPE = "type";
	public static final String COLUMN_KEY = "key";
	public static final String COLUMN_VALUE = "value";  	
	
	protected HashMap<Integer,ItemHolder> hashmap=new HashMap<Integer,ItemHolder>();
	
	public CursorMultipleTypesAdapter(Context context, Cursor c, int flags) {
		super(context, c, flags);
	}
	
	public void addItemHolder(int type,ItemHolder itemholder){
		hashmap.put(type, itemholder);
	}

	@Override
	public void bindView(View view, Context context, Cursor cursor) {
		ItemHolder holder=(ItemHolder)view.getTag();
		view=holder.getView(view, null, cursor);
	}

	@Override
	public View newView(Context context, Cursor cursor, ViewGroup viewgroup) {
		
		ItemHolder holder=hashmap.get(getType(cursor)).createClone();
		View view=holder.getView(null, viewgroup, cursor);
		view.setTag(holder);		
		
		return view;
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view,
			int position, long id) {
		
		getCursor().moveToPosition(position);		
		
		OnItemClickListener onitemclicklistener=(OnItemClickListener)view.getTag();
		onitemclicklistener.onItemClick(parent, view, position, id);		
	}
	
	@Override
	public boolean isEnabled(int position){
		return hashmap.get(getItemViewType(position)).isEnabled();		
	}
	
	@Override
	public int getItemViewType(int position){
		Cursor cursor=this.getCursor();
		cursor.moveToPosition(position);
		return getType(cursor);
	}
	
	@Override
	public int getViewTypeCount(){			
		return hashmap.size();
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