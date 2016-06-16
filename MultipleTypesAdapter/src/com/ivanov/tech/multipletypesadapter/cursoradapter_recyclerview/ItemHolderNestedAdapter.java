package com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview;

import java.util.HashMap;
import java.util.Map.Entry;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.ivanov.tech.multipletypesadapter.R;
import com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview.CursorMultipleTypesAdapter;

import android.content.Context;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.os.AsyncTask;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

public class ItemHolderNestedAdapter extends CursorItemHolder{
	
	private static final String TAG = ItemHolderNestedAdapter.class.getSimpleName();
		
	public RecyclerView recyclerview;
	
	public CursorMultipleTypesAdapter adapter,parent_adapter;
    public int layout,recyclerview_id;
    public OnClickListener onclicklistener;
	
	protected ItemHolderNestedAdapter(View itemView,Context context, int layout, int recyclerview_id, CursorMultipleTypesAdapter parent_adapter, OnClickListener onclicklistener) {
		super(itemView);
		
		this.context=context;
		this.layout=layout;
		this.recyclerview_id=recyclerview_id;
		this.parent_adapter=parent_adapter;
		this.onclicklistener=onclicklistener;
		
	}
	
	public ItemHolderNestedAdapter(Context context, int layout, int recyclerview_id, CursorMultipleTypesAdapter parent_adapter, OnClickListener onclicklistener) {
		super(new View(context));
		
		this.context=context;
		this.layout=layout;
		this.recyclerview_id=recyclerview_id;
		this.parent_adapter=parent_adapter;
		this.onclicklistener=onclicklistener;		
	}
	
	@Override
	public CursorItemHolder createClone(ViewGroup parent) {	
		
		Log.d(TAG, "createClone");
		
		View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
		
		ItemHolderNestedAdapter itemholder=new ItemHolderNestedAdapter(view,context,layout,recyclerview_id,parent_adapter,onclicklistener);
		
		itemholder.recyclerview=(RecyclerView)view.findViewById(recyclerview_id);					
		itemholder.recyclerview.setLayoutManager(new LinearLayoutManager(context));
        
		itemholder.adapter=new CursorMultipleTypesAdapter(context,null);
		
		for(Entry<Integer,CursorItemHolder> entry : parent_adapter.hashmap.entrySet()) {
		    int key = entry.getKey();
		    CursorItemHolder value = entry.getValue();
		    
		    itemholder.adapter.addItemHolder(key, value);		    
		}	
		itemholder.recyclerview.setAdapter(itemholder.adapter);
		
		if(itemholder.onclicklistener!=null){
			view.setOnClickListener(itemholder.onclicklistener);
		}

		return itemholder;
	}

	@Override
	public void bindView(Cursor cursor) {
		adapter.changeCursor(null);
		itemView.setTag(itemView.getId(), CursorMultipleTypesAdapter.getKey(cursor));	
		
		new ProcessCursor(this).execute(cursor);
	}
	
	class ProcessCursor extends AsyncTask<Cursor, Integer, Cursor> {
	    
		ItemHolderNestedAdapter holder;
	    int position;
	    
	    public ProcessCursor(ItemHolderNestedAdapter holder){
	    	this.holder=holder;
	    }
		
	    @Override
	    protected void onPreExecute() {
	      super.onPreExecute();
	      holder.adapter.changeCursor(null);
	      position=holder.getAdapterPosition();
	    }

	    @Override
	    protected Cursor doInBackground(Cursor... params) {
	      
	    	MatrixCursor matrixcursor=new MatrixCursor(new String[]{adapter.COLUMN_ID, adapter.COLUMN_TYPE, adapter.COLUMN_KEY, adapter.COLUMN_VALUE});
	    	
	    	JSONArray jsonarray;
			try {
				jsonarray = new JSONArray(CursorMultipleTypesAdapter.getValue(params[0]));
				Log.d(TAG, "ProcessCursor doInBackground jsonarray="+jsonarray.toString());
				
				int _id=0;
				
				for(int i=0;i<jsonarray.length();i++){
					
					JSONObject item=jsonarray.getJSONObject(i);				    	
			    	matrixcursor.addRow(new Object[]{++_id,item.getInt("type"),item.getInt("key"),item.getJSONObject("value").toString()});
			    	
				}
				
				
			} catch (JSONException e) {
				Log.e(TAG, "ProcessCursor doInBackground JSONException e="+e);
			}
	    	
	      return matrixcursor;
	    }

	    @Override
	    protected void onPostExecute(Cursor result) {
	      super.onPostExecute(result);
	      if(holder.getAdapterPosition()==position){
	    	  holder.adapter.changeCursor(result);
	      }
	    }
	    
	  }
	
}
