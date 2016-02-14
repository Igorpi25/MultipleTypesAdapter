package com.ivanov.tech.multipletypesadapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorMultipleTypesAdapter;

import android.content.Context;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;

public class BinderButton extends Binder<Button> {

	public BinderButton(Context context) throws JSONException {
		super(context);		
	}
	
	public BinderButton(Context context, JSONObject json_default) throws JSONException {
		super(context,json_default);		
	}

	@Override
	protected boolean process(Button button, JSONObject json) throws JSONException{
		
		
		if(!json.isNull("button")){
			button.setVisibility(View.VISIBLE);	
			
			button.setText(json.getString("button_text"));						
			button.setBackgroundResource(json.getInt("button_background"));
			button.setTextColor(context.getResources().getColorStateList(json.getInt("button_text_color")));
			button.setTextSize(json.getInt("button_text_size_unit"),(float)json.getDouble("button_text_size"));
			
			return true;
		}else{
			button.setVisibility(View.GONE);
			
		}		
		return false;
	}
	
	@Override
	protected JSONObject createDefaultJson() throws JSONException {
		JSONObject json=new JSONObject();
		json.put("button_background", R.drawable.drawable_button_dialog_normal);
		json.put("button_text_color", R.color.color_selector_font);
		json.put("button_text_size", 16);
		json.put("button_text_size_unit", TypedValue.COMPLEX_UNIT_SP);
		
		return json;
	}

}
