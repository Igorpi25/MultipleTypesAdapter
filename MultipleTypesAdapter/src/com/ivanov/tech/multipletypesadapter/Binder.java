package com.ivanov.tech.multipletypesadapter;

import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.view.View;
import android.widget.Button;

public abstract class Binder<T>{

	Context context;
	JSONObject json_default;
	
	public Binder(Context context) throws JSONException {
		this.context=context;
		this.json_default=createDefaultJson();
	}
	
	public Binder(Context context, JSONObject json_default) throws JSONException{		
		this(context);
		this.json_default=append(this.json_default,json_default);		
	}
	
	public boolean bind(T view, JSONObject json) throws JSONException{
				
		return process(view,append(this.json_default,json));
	}
	
	public static JSONObject append(JSONObject source, JSONObject appendix) throws JSONException{
		JSONObject result = new JSONObject(source.toString());
		
		for (Iterator<String> keys = appendix.keys();keys.hasNext();) {
			String key=keys.next();
			result.put(key, appendix.getString(key));
		}		
		
		return result;
	}
	
	protected abstract boolean process(T view, JSONObject json) throws JSONException;
	protected abstract JSONObject createDefaultJson() throws JSONException;
	
}

