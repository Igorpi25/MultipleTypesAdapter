package com.ivanov.tech.multipletypesadapter.demo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorMultipleTypesAdapter;
import com.ivanov.tech.multipletypesadapter.R;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderLinkGroup;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderLinkUser;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.Toast;

public class FragmentDemo extends DialogFragment implements OnItemClickListener,OnClickListener{
	
	private static final String TAG = FragmentDemo.class.getSimpleName();    
    
    protected static final int TYPE_LINK_USER = 0;
    protected static final int TYPE_LINK_USER_GOD = 1;
    protected static final int TYPE_LINK_USER_NOT_CLICKABLE = 2;
    

    protected static final int TYPE_LINK_GROUP =3;
    
	

    protected ListView listview;
    
    protected CursorMultipleTypesAdapter adapter=null;
    
    
    public static FragmentDemo newInstance() {
    	FragmentDemo f = new FragmentDemo();        
       
        return f;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_details, container, false);
                
        Log.d(TAG,"onCreateView");
        
        listview=(ListView)view.findViewById(R.id.fragment_details_listview);
        
        adapter=new CursorMultipleTypesAdapter(getActivity(),null,adapter.FLAG_AUTO_REQUERY);
        
        //Prepare map of types and set listeners for them
        adapter.addItemHolder(TYPE_LINK_USER, new CursorItemHolderLinkUser(getActivity(),this,this));                
        adapter.addItemHolder(TYPE_LINK_USER_GOD, new CursorItemHolderLinkUser(getActivity(),this,new OnClickListener(){

			@Override
			public void onClick(View v) {
				toast("FUCK YOU!");
			}
        	
        }));
        adapter.addItemHolder(TYPE_LINK_USER_NOT_CLICKABLE, new CursorItemHolderLinkUser(getActivity(),this,this){
        	
        	@Override
        	public boolean isEnabled() {
        		return false;
        	}
        });
        
        adapter.addItemHolder(TYPE_LINK_GROUP, new CursorItemHolderLinkGroup(getActivity(),this));
        
        listview.setAdapter(adapter);
        
        listview.setOnItemClickListener(adapter);
        
        adapter.changeCursor(createMergeCursor());
        
        return view;
    }
    
//------------Preparing cursor----------------------------
	
	protected Cursor createMergeCursor(){
    	
    	List<Cursor> cursors_list=new ArrayList<Cursor>();	
    	
    	int _id=1;
    	try{
    	for(int i=0;i<1;i++){
	    		cursors_list.add(getUsersMatrixCursor(_id));
	    		//cursors_list.add(getGroupsMatrixCursor(_id));
    	}
    	}catch(JSONException e){
    		Log.e(TAG, "createMergeCursor JSONException e="+e);
    	}
    	
    	Cursor[] cursors_array=new Cursor[cursors_list.size()];
    	MergeCursor mergecursor=new MergeCursor(cursors_list.toArray(cursors_array));
    	
    	return mergecursor;    	
    }
    
    protected MatrixCursor getUsersMatrixCursor(int _id) throws JSONException{

    	MatrixCursor matrixcursor=new MatrixCursor(new String[]{adapter.COLUMN_ID, adapter.COLUMN_TYPE, adapter.COLUMN_KEY, adapter.COLUMN_VALUE});    	
    	
    	JSONObject json;
    	
    	json=new JSONObject("{name:'Igor Ivanov', status:'Android Developer', button:'link_user_button', button_text:'Accept', url_icon: 'https://pp.vk.me/c616830/v616830795/1121c/AwzilQ3NWLs.jpg'}");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_USER,11,json.toString()});
        
    	json=new JSONObject("{name:'Stapan Sotnikov', status:'Server Admin', button:'link_user_button', button_text:'Add', url_icon: 'https://pp.vk.me/c316130/u3906727/d_80cd5ad1.jpg'}");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_USER,12,json.toString()});
        
    	json=new JSONObject("{name:'God', status:'Dynamic type', button:'link_user_button', button_text:'Fuck', url_icon: 'https://cdn3.iconfinder.com/data/icons/gray-user-toolbar/512/holy-64.png'}");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_USER_GOD,13,json.toString()});
        
    	json=new JSONObject("{name:'Not Clickable', status:'Dynamic type', url_icon:'https://vk.com/images/deactivated_100.png'}");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_USER_NOT_CLICKABLE,14,json.toString()});
        
    	json=new JSONObject("{name:'Space', status:'Free developers', label:'66', url_icon:'https://vk.com/images/community_100.png'}");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_GROUP,15,json.toString()});
        
    	    	
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
		
		case TYPE_LINK_GROUP:{
			int group_id=adapter.getKey(adapter.getCursor());
			
			toast("TYPE_LINK_GROUP item clicked key="+group_id);
			
		}break;
		
		}
		
	}

	@Override
	public void onClick(View v) {
		
		if(v.getTag(R.id.details_item_link_user_button)!=null){
			int key=(Integer)v.getTag(R.id.details_item_link_user_button);
			
			toast("Button clicked tag="+v.getTag()+" key="+key);
			
		}
	}
	
	private void toast(String msg){
		Log.d(TAG, msg);
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

}
