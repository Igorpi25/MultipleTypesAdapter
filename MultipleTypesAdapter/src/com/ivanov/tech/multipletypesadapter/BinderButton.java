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

public class BinderButton extends Binder<Button> {

	private static final String TAG = BinderButton.class.getSimpleName();
	
	public BinderButton(Context context) throws JSONException {
		super(context);		
	}
	
	public BinderButton(Context context, JSONObject json_default) throws JSONException {
		super(context,json_default);		
	}

	@Override
	protected boolean process(Button button, JSONObject json) throws JSONException{
		
		//Log.d(TAG, "process json="+json);
		
		if(json.getBoolean("visible")){
			//Log.d(TAG, "process visible=true");
			
			button.setVisibility(View.VISIBLE);
			button.setTag(json.getString("tag"));	
			
			button.setBackgroundResource(json.getInt("background"));
			button.setText(json.getString("text"));		
			button.setTextColor(context.getResources().getColorStateList(json.getInt("text_color")));
			button.setTextSize(json.getInt("text_size_unit"),(float)json.getDouble("text_size"));
			
			return true;
		}else{
			button.setVisibility(View.GONE);			
		}		
		return false;
	}
	
	@Override
	protected JSONObject createDefaultJson() throws JSONException {
		JSONObject json=new JSONObject();
		json.put("visible", true);
		json.put("background", R.drawable.drawable_button_dialog_normal);
		json.put("text", "sdsd");
		json.put("text_color", R.color.color_selector_font);
		json.put("text_size", 16);
		json.put("text_size_unit", TypedValue.COMPLEX_UNIT_SP);
		
		return json;
	}

}
