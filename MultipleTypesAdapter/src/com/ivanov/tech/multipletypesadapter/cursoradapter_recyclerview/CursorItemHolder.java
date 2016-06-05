package com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview;

import com.ivanov.tech.multipletypesadapter.ItemHolder;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.support.v7.widget.RecyclerView.ViewHolder;

public abstract class CursorItemHolder extends ViewHolder{
	
	public CursorItemHolder(View itemView) {
		super(itemView);
		// TODO Auto-generated constructor stub
	}
	
	public abstract void bind(Cursor cursor);
	
	public abstract CursorItemHolder createClone();
	
}
