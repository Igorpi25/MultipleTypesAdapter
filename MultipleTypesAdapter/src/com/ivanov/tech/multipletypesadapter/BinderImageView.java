package com.ivanov.tech.multipletypesadapter;

import org.json.JSONException;
import org.json.JSONObject;

import com.bumptech.glide.Glide;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderLink;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorMultipleTypesAdapter;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BinderImageView extends Binder<ImageView> {

	private static final String TAG = BinderImageView.class.getSimpleName();
	
	public BinderImageView(Context context) throws JSONException {
		super(context);		
	}
	
	public BinderImageView(Context context, JSONObject json_default) throws JSONException {
		super(context,json_default);		
	}

	@Override
	protected boolean process(ImageView imageview, JSONObject json) throws JSONException{
		//Log.d(TAG, "process json="+json);
		if(json.getBoolean("visible")){
			//Log.d(TAG, "process visible=true");
			
			imageview.setVisibility(View.VISIBLE);
			
			if(!json.isNull("tag"))
				imageview.setTag(json.getString("tag"));
			else 
				imageview.setTag(null);
			
			imageview.setBackgroundResource(json.getInt("background"));									
			int image_res=json.getInt("image_res");		
			
			Glide.clear(imageview);
			if(!json.isNull("image_url")){				
				Glide.with(context).load(json.getString("image_url")).error(image_res).placeholder(image_res).into(imageview);			
			} else {
				imageview.setImageResource(image_res);
			}
			
			return true;
		}else{
			imageview.setVisibility(View.GONE);
		}		
		return false;
	}
	
	@Override
	protected JSONObject createDefaultJson() throws JSONException {
		JSONObject json=new JSONObject();
		json.put("visible", true);
		json.put("background", R.color.color_transparent);		
		json.put("image_res", R.drawable.ic_no_icon);
		
		return json;
	}

}
