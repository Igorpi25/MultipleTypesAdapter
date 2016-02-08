package com.ivanov.tech.multipletypesadapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

public interface ItemHolder<Data> {
	
	public ItemHolder<Data> getInstance(Context context,Object listener);
	
	public View getView(View convertView, ViewGroup parent, Data data);
	
	public void onItemClick(View view,int position, long id, Data data);
	
	boolean isEnabled();
}
