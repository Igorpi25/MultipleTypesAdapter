package com.ivanov.tech.multipletypesadapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderLink;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorMultipleTypesAdapter;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class BinderTextView extends Binder<TextView> {

	private static final String TAG = BinderTextView.class.getSimpleName();
	
	public BinderTextView(Context context) throws JSONException {
		super(context);		
	}
	
	public BinderTextView(Context context, JSONObject json_default) throws JSONException {
		super(context,json_default);		
	}

	@Override
	protected boolean process(TextView textview, JSONObject json) throws JSONException{
		
		//Log.d(TAG, "process json="+json);
		
		if(json.getBoolean("visible")){
			//Log.d(TAG, "process visible=true");			
			
			textview.setVisibility(View.VISIBLE);
			if(!json.isNull("tag"))
				textview.setTag(json.getString("tag"));
			else 
				textview.setTag(null);
			
			textview.setText(json.getString("text"));		
			textview.setTextColor(context.getResources().getColorStateList(json.getInt("text_color")));
			textview.setTextSize(json.getInt("text_size_unit"),(float)json.getDouble("text_size"));
			
			return true;
		}else{
			textview.setVisibility(View.GONE);			
		}		
		return false;
	}
	
	@Override
	protected JSONObject createDefaultJson() throws JSONException {
		JSONObject json=new JSONObject();
		json.put("visible", true);
		
		json.put("text", "sdsd");
		json.put("text_color", R.color.color_text_black);
		json.put("text_size", 14);
		json.put("text_size_unit", TypedValue.COMPLEX_UNIT_SP);
		
		return json;
	}

	public BinderTextView bindText(TextView textview, JSONObject json) throws JSONException{
		textview.setText(json.getString("text"));
		return this;
	}
}
