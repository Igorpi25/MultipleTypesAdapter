package com.ivanov.tech.multipletypesadapter.cursoradapter;

import com.ivanov.tech.multipletypesadapter.ItemHolder;

import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public abstract class CursorItemHolder implements ItemHolder<Cursor>, OnItemClickListener{
	
	private String TAG=CursorItemHolder.class.getSimpleName();
	
	public Context context;
	public OnItemClickListener onitemclicklistener;	
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		if(onitemclicklistener!=null)onitemclicklistener.onItemClick(parent, view, position, id);
	}

	
}
