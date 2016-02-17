package com.ivanov.tech.multipletypesadapter.demo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorMultipleTypesAdapter;
import com.ivanov.tech.multipletypesadapter.ItemHolder;
import com.ivanov.tech.multipletypesadapter.R;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderImageView;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolder;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderButton;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderGridView;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderHeader;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderLink;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderText;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentDemo extends DialogFragment implements OnItemClickListener,OnClickListener{
	
	private static final String TAG = FragmentDemo.class.getSimpleName();    
    
	
	//По константе переданной в getType, адаптер определяет ItemHolder, который должен его обработать
	//Также используются в для обработки callback событий, приходящих от адаптера 
    protected static final int TYPE_LINK_USER = 0;
    protected static final int TYPE_LINK_USER_GOD = 1;
    protected static final int TYPE_LINK_USER_NOT_CLICKABLE = 2;
    
    protected static final int TYPE_LINK_GROUP =3;
    
    protected static final int TYPE_TEXT =4;
    protected static final int TYPE_TEXT_CLICKABLE =5;
    protected static final int TYPE_TEXT_UPLOADER =6;
    
    protected static final int TYPE_HEADER =7;
    
    protected static final int TYPE_BUTTON =8;  
    protected static final int TYPE_BUTTON_SMALL =9;  
    
    protected static final int TYPE_AVATAR =10;
    protected static final int TYPE_PREVIEW =11;
    protected static final int TYPE_GRIDVIEW =12;
	

    protected ListView listview;
    
    protected CursorMultipleTypesAdapter adapter=null;
    
    
    public static FragmentDemo newInstance() {
    	FragmentDemo f = new FragmentDemo();        
       
        return f;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_demo, container, false);
                
        Log.d(TAG,"onCreateView");
        
        listview=(ListView)view.findViewById(R.id.fragment_demo_listview);
        
        adapter=new CursorMultipleTypesAdapter(getActivity(),null,adapter.FLAG_AUTO_REQUERY);
        
        //Prepare map of types and set listeners for them. There are different ways in which you can define ItemHolder      
        adapter.addItemHolder(TYPE_LINK_USER, new CursorItemHolderLink(getActivity(),this,this));                
        adapter.addItemHolder(TYPE_LINK_USER_GOD, new CursorItemHolderLink(getActivity(),this,new OnClickListener(){

			@Override
			public void onClick(View v) {
				toast("FUCK YOU!");
			}
        	
        }));
        adapter.addItemHolder(TYPE_LINK_USER_NOT_CLICKABLE, new CursorItemHolderLink(getActivity(),this,this){
        	
        	@Override
        	public boolean isEnabled() {
        		return false;
        	}
        });
        
        adapter.addItemHolder(TYPE_LINK_GROUP, new CursorItemHolderLink(getActivity(),this,null));
        adapter.addItemHolder(TYPE_TEXT, new CursorItemHolderText(getActivity(),this));
        adapter.addItemHolder(TYPE_TEXT_CLICKABLE, new CursorItemHolderText(getActivity(),this){
        	@Override
        	public boolean isEnabled() {
        		return true;
        	}
        });
        adapter.addItemHolder(TYPE_TEXT_UPLOADER, new CursorItemHolderText(getActivity(),this){
        	@Override
        	public boolean isEnabled() {
        		return true;
        	}
        });
        
        adapter.addItemHolder(TYPE_HEADER, new CursorItemHolderHeader(getActivity(),this));
        
        adapter.addItemHolder(TYPE_BUTTON, new CursorItemHolderButton(getActivity(),this));
        adapter.addItemHolder(TYPE_BUTTON_SMALL, new CursorItemHolderButton(getActivity(),R.layout.details_item_button_small,R.id.details_item_button_small_button,this));
        
        adapter.addItemHolder(TYPE_AVATAR, new CursorItemHolderImageView(getActivity(),R.layout.details_item_avatar,R.id.details_item_avatar_imageview,this));
        adapter.addItemHolder(TYPE_PREVIEW, new CursorItemHolderImageView(getActivity(),R.layout.details_item_preview,R.id.details_item_preview_imageview,this));
        
        adapter.addItemHolder(TYPE_GRIDVIEW, new CursorItemHolderGridView(getActivity(),getGridViewCursor(),new OnItemClickListener(){

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				
				//You should define callback-listeners like so on
				toast("TYPE_GRIDVIEW position="+position+" key="+id);				
			}
        	
        }));
        
        
        
        listview.setAdapter(adapter);
        
        listview.setOnItemClickListener(adapter);
        
        adapter.changeCursor(createMergeCursor());
        
        return view;
    }
    
//------------Preparing cursor----------------------------
	
    //Merge cursor for testing the huge number of items
	protected Cursor createMergeCursor(){
    	
    	List<Cursor> cursors_list=new ArrayList<Cursor>();	
    	
    	int _id=1;
    	try{
    	//You can test with huge number of items. Just set i=100
    	for(int i=0;i<1;i++){
	    		cursors_list.add(getCursorForAdapter(_id));
    	}
    	}catch(JSONException e){
    		Log.e(TAG, "createMergeCursor JSONException e="+e);
    	}
    	
    	Cursor[] cursors_array=new Cursor[cursors_list.size()];
    	MergeCursor mergecursor=new MergeCursor(cursors_list.toArray(cursors_array));
    	
    	return mergecursor;    	
    }
    
	
    protected MatrixCursor getCursorForAdapter(int _id) throws JSONException{

    	MatrixCursor matrixcursor=new MatrixCursor(new String[]{adapter.COLUMN_ID, adapter.COLUMN_TYPE, adapter.COLUMN_KEY, adapter.COLUMN_VALUE});    	
    	
    	JSONObject json;    	

//-------------------------- ItemHolder Header ---------------------------------
    	    	
    	json=new JSONObject("{key:{visible:false}, value:{visible:false}, label:{text:'com.ivanov.tech.multipletypesadapter.demo'} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_HEADER,0,json.toString()});
    	
//----------------------- ItemHolder ImageView ----------------------------------
    	
    	json=new JSONObject("{key:{text:'Images'}, value:{visible:false}, label:{visible:false} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_HEADER,0,json.toString()});
    	    	
    	json=new JSONObject("{ imageview:{image_url:'https://pp.vk.me/c1682/u38531795/98572754/x_203d0029.jpg'} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_PREVIEW,102,json.toString()});

//-------------------------- ItemHolder GridView ---------------------------------
    	
    	json=new JSONObject("{key:{text:'Nested adapter'}, value:{visible:false}, label:{visible:false} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_HEADER,0,json.toString()});
    	
    	json=new JSONObject("{visible:true}");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_GRIDVIEW,0,json.toString()});    	

//-------------------------- ItemHolder Link ---------------------------------
    	      
    	json=new JSONObject("{key:{text:'Links'}, value:{visible:false}, label:{visible:false} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_HEADER,0,json.toString()});
    	
    	json=new JSONObject("{ name:{text:'Igor Ivanov'}, status:{text:'Android Developer'}, label:{ visible:false }, button:{tag:'link_user_button', text:'Accept'}, icon:{image_url:'https://pp.vk.me/c616830/v616830795/1121c/AwzilQ3NWLs.jpg'} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_USER,11,json.toString()});
        
    	json=new JSONObject("{name:{text:'Stepan Sotnikov'}, status:{text:'Server Admin'}, label:{ visible:false }, button:{tag:'link_user_button', text:'Add'}, icon:{image_url:'https://pp.vk.me/c316130/u3906727/d_80cd5ad1.jpg'} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_USER,12,json.toString()});
        
    	json=new JSONObject("{name:{text:'God'}, status:{text:'Dynamic type'}, label:{ visible:false }, button:{tag:'link_user_button', text:'Fuck'}, icon:{image_url:'https://cdn3.iconfinder.com/data/icons/gray-user-toolbar/512/holy-64.png'} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_USER_GOD,13,json.toString()});
        
    	json=new JSONObject("{name:{text:'Not Clickable'}, status:{text:'Default icon'}, label:{ visible:false }, button:{visible:false}, icon:{ } }");
    	matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_USER_NOT_CLICKABLE,14,json.toString()});
        
    	json=new JSONObject("{name:{text:'Space'}, status:{text:'66'}, label:{text:'Group link'}, button:{visible:false}, icon:{image_url:'https://vk.com/images/community_100.png'} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_GROUP,21,json.toString()});
 
//-------------------------- ItemHolder Text ---------------------------------
    	
    	json=new JSONObject("{key:{text:'Text'}, value:{visible:false}, label:{visible:false} } ");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_HEADER,0,json.toString()});
    	
    	json=new JSONObject("{key:{text:'email'}, value:{text:'igorpi25@gmail.com'}, icon:{ } }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_TEXT_CLICKABLE,31,json.toString()});
    	
    	json=new JSONObject("{key:{text:'phone'}, value:{text:'+79142966292'}, icon:{ } }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_TEXT_CLICKABLE,32,json.toString()});
    	
    	json=new JSONObject("{key:{ visible:false }, value:{text:'Not clickable, and no icon'}, icon:{visible:false} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_TEXT,33,json.toString()});
    	
    	json=new JSONObject("{value:{text:'but clickable'}, key:{text:'No icon'}, icon:{visible:false} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_TEXT_CLICKABLE,34,json.toString()});
    	
    	json=new JSONObject("{value:{text:'Upload photo'}, key:{ visible:false }, icon:{image_res:'"+android.R.drawable.ic_menu_upload+"'} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_TEXT_UPLOADER,35,json.toString()});

//-------------------------- ItemHolder Button ---------------------------------
    	    	    	
    	json=new JSONObject("{key:{text:'Buttons'}, value:{visible:false}, label:{visible:false} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_HEADER,0,json.toString()});
    	
    	json=new JSONObject("{button:{tag:'button_normal', text:'Send message'} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_BUTTON,41,json.toString()});
            	
    	json=new JSONObject("{tag:'button_negative', text:'Negative button' }");
    	json.put("background", R.drawable.drawable_button_dialog_negative);
    	json.put("text_color", R.color.color_white);
    	json.put("text_size_unit", TypedValue.COMPLEX_UNIT_SP);
    	json.put("text_size", 14.0f);
    	matrixcursor.addRow(new Object[]{++_id,TYPE_BUTTON,42,"{button: "+json.toString()+" }"});
    	
    	json=new JSONObject("{button:{tag:'button_small', text:'Small'} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_BUTTON_SMALL,43,json.toString()});
        
    	return matrixcursor;
    }
    
    //Используется как cursor для вложенного адаптера TYPE_GRIDVIEW
    protected MatrixCursor getGridViewCursor(){

    	MatrixCursor matrixcursor=new MatrixCursor(new String[]{adapter.COLUMN_ID, adapter.COLUMN_TYPE, adapter.COLUMN_KEY, adapter.COLUMN_VALUE});
    	
    	try{
    		
	    	int _id=0;
	    	
	    	JSONObject json;
	    	//This adapter can use TYPE_GRIDVIEW-type only. This is inside type of CursorItemHolderGridView-adapter
	    	
	  //-------------------------- ItemHolder Button ---------------------------------
	    	
	    	json=new JSONObject("{ name:{text:'Igor Ivanov'},  icon:{image_url:'https://pp.vk.me/c616830/v616830795/1121c/AwzilQ3NWLs.jpg'} }");    	
	    	matrixcursor.addRow(new Object[]{++_id,CursorItemHolderGridView.TYPE_GRIDVIEW_ITEM,11,json.toString()});
	        
	    	json=new JSONObject("{name:{text:'Stepan Sotnikov'}, icon:{image_url:'https://pp.vk.me/c316130/u3906727/d_80cd5ad1.jpg'} }");    	
	    	matrixcursor.addRow(new Object[]{++_id,CursorItemHolderGridView.TYPE_GRIDVIEW_ITEM,12,json.toString()});
	        
	    	json=new JSONObject("{name:{text:'God'}, icon:{image_url:'https://cdn3.iconfinder.com/data/icons/gray-user-toolbar/512/holy-64.png'} }");    	
	    	matrixcursor.addRow(new Object[]{++_id,CursorItemHolderGridView.TYPE_GRIDVIEW_ITEM,13,json.toString()});
	        
	    	json=new JSONObject("{name:{text:'Not Clickable'}, icon:{ } }");
	    	matrixcursor.addRow(new Object[]{++_id,CursorItemHolderGridView.TYPE_GRIDVIEW_ITEM,14,json.toString()});
	        
	    	json=new JSONObject("{name:{text:'Space'}, icon:{image_url:'https://vk.com/images/community_100.png'} }");    	
	    	matrixcursor.addRow(new Object[]{++_id,CursorItemHolderGridView.TYPE_GRIDVIEW_ITEM,21,json.toString()});
	    	
	    	json=new JSONObject("{name:{visible:false}, icon:{image_res:"+R.drawable.ic_add_user+"} }");    	
	    	matrixcursor.addRow(new Object[]{++_id,CursorItemHolderGridView.TYPE_GRIDVIEW_ITEM,22,json.toString()});
	    	
    	}catch(JSONException e){
    		Log.e(TAG, "createGridViewCursor JSONException e="+e);
    	}
    	
    	return matrixcursor;
    }
    
//--------------Adapter Callbacks----------------------
     
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		switch(adapter.getType(adapter.getCursor())){
		
		case TYPE_LINK_USER:{
			int user_id=adapter.getKey(adapter.getCursor());
			
			toast("TYPE_LINK_USER item clicked key="+user_id);
			
		}break;
		
		case TYPE_LINK_USER_GOD:{
			int group_id=adapter.getKey(adapter.getCursor());
			
			toast("GOD ITEM CLICKED");
			
		}break;
		
		case TYPE_LINK_GROUP:{
			int group_id=adapter.getKey(adapter.getCursor());
			
			toast("TYPE_LINK_GROUP item clicked key="+group_id);
			
		}break;
		
		case TYPE_TEXT:
		case TYPE_TEXT_CLICKABLE:{
			int key=adapter.getKey(adapter.getCursor());
			
			toast("TYPE_TEXT_CLICKABLE item clicked key="+key);
			
		}break;
		
		case TYPE_TEXT_UPLOADER:{
			int key=adapter.getKey(adapter.getCursor());
			
			toast("Uploading photo to server key="+key);
			
		}break;
		
		
		}
		
	}

	@Override
	public void onClick(View v) {
		
		if(v.getTag(R.id.details_item_link_button)!=null){
			int key=(Integer)v.getTag(R.id.details_item_link_button);
			
			toast("Button clicked tag="+v.getTag()+" key="+key);
			
		}
		
		if(v.getTag(R.id.details_item_button_button)!=null){
			int key=(Integer)v.getTag(R.id.details_item_button_button);
			
			toast("Button clicked tag="+v.getTag()+" key="+key);
			
		}
		
		if(v.getTag(R.id.details_item_avatar_imageview)!=null){
			int key=(Integer)v.getTag(R.id.details_item_avatar_imageview);
			
			toast("Avatar image clicked");
			
		}
	}
	
//-----------------------Utilites--------------------------
	
	private void toast(String msg){
		Log.d(TAG, msg);
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

}