package com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview;

import com.ivanov.tech.multipletypesadapter.ItemHolder;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.support.v7.widget.RecyclerView.ViewHolder;

public abstract class CursorItemHolder extends ViewHolder{
	
	public CursorItemHolder(View itemView) {
		super(itemView);
	}

	public Context context;
	
	public abstract void bindView(Cursor cursor);
	
	public abstract CursorItemHolder createClone(ViewGroup parent);
	
}
