package com.ivanov.tech.multipletypesadapter;

import com.ivanov.tech.multipletypesadapter.demo.FragmentDemo;

import android.view.View;
import android.view.ViewGroup;

public interface ItemHolder<T> {
		
	public ItemHolder<T> createClone();
	
	public View getView(View convertView, ViewGroup parent, T data);
	
	boolean isEnabled();
}
