package com.ivanov.tech.multipletypesadapter.demo;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview.CursorMultipleTypesAdapter;
import com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview.ItemHolderCardOrderItem;
import com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview.ItemHolderCardOrderString;
import com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview.ItemHolderCardPreview;
import com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview.ItemHolderCardProduct;
import com.ivanov.tech.multipletypesadapter.cursoradapter_recyclerview.ItemHolderCardProductShoppingCart;
import com.ivanov.tech.multipletypesadapter.R;

import android.database.Cursor;
import android.database.MatrixCursor;
import android.database.MergeCursor;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Toast;

public class FragmentDemoMD extends DialogFragment implements OnClickListener{
	
	private static final String TAG = FragmentDemoMD.class.getSimpleName();    
    
	
	//По константе переданной в getType, адаптер определяет ItemHolder, который должен его обработать
	//Также используются в для обработки callback событий, приходящих от адаптера 
    protected static final int TYPE_CARD_PREVIEW = 0;
    protected static final int TYPE_CARD_PRODUCT = 1;
    protected static final int TYPE_CARD_PRODUCT_VALUED = 2;
    protected static final int TYPE_CARD_ORDER_ITEM = 3;
    protected static final int TYPE_CARD_ORDER_STRING = 4;
	

    protected RecyclerView recyclerview;
    
    protected CursorMultipleTypesAdapter adapter=null;
    
    public static FragmentDemoMD newInstance() {
    	FragmentDemoMD f = new FragmentDemoMD();        
       
        return f;
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
    	View view = null;
        view = inflater.inflate(R.layout.fragment_demo_rv, container, false);
        
        recyclerview=(RecyclerView)view.findViewById(R.id.fragment_demo_rv_recyclerview);
        
        recyclerview.setLayoutManager(new LinearLayoutManager(recyclerview.getContext()));
        
        adapter=new CursorMultipleTypesAdapter(getActivity(),null);
      
        //Prepare map of types and set listeners for them. There are different ways in which you can define ItemHolder      
        adapter.addItemHolder(TYPE_CARD_PREVIEW, new ItemHolderCardPreview(getActivity(),this));                
        adapter.addItemHolder(TYPE_CARD_PRODUCT, new ItemHolderCardProduct(getActivity(),this));
        adapter.addItemHolder(TYPE_CARD_PRODUCT_VALUED, new ItemHolderCardProductShoppingCart(getActivity(),this));
        adapter.addItemHolder(TYPE_CARD_ORDER_ITEM, new ItemHolderCardOrderItem(getActivity(),this));
        adapter.addItemHolder(TYPE_CARD_ORDER_STRING, new ItemHolderCardOrderString(getActivity(),this));
             
        recyclerview.setAdapter(adapter);
        
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
    	for(int i=0;i<4;i++){
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

    	//---------Card Preview ------------
    	      
//    	json=new JSONObject("{title:{text:'Немюгюнский Хлебо-комбинат'}, text:{text:'"+getString(R.string.nhz_text)+"'}, image:{image_url:'http://eyakutia.com/wp-content/uploads/2012/04/yakutianhorserider_01.jpg'} }");    	
//    	matrixcursor.addRow(new Object[]{++_id,TYPE_CARD_PREVIEW,0,json.toString()});
    	
//    	json=new JSONObject("{title:{text:'БЕЛЫЙ хлеб с зернами кукурузы'},price:{text:'68 р.'}, text:{text:'"+getString(R.string.nhz_text)+"'}, icon:{image_url:'http://eyakutia.com/wp-content/uploads/2012/04/yakutianhorserider_01.jpg'} }");    	
//    	matrixcursor.addRow(new Object[]{++_id,TYPE_CARD_PRODUCT,1,json.toString()});
//    	   
//    	json=new JSONObject("{title:{text:'ЧЕРНЫЙ хлеб с кориандром'},price:{text:'238 р.'}, text:{text:'"+getString(R.string.nhz_text)+"'}, icon:{image_url:'http://eyakutia.com/wp-content/uploads/2012/04/yakutianhorserider_01.jpg'} }");    	
//    	matrixcursor.addRow(new Object[]{++_id,TYPE_CARD_PRODUCT,2,json.toString()});
//    	    	
//    	json=new JSONObject("{title:{text:'Булка \"Пышка\"'},price:{text:'100 р.'}, unit:{text:'шт.'}, count:{text:'x25'}, text:{text:'"+getString(R.string.nhz_text)+"'}, icon:{image_url:'http://eyakutia.com/wp-content/uploads/2012/04/yakutianhorserider_01.jpg'} }");    	
//    	matrixcursor.addRow(new Object[]{++_id,TYPE_CARD_PRODUCT_VALUED,3,json.toString()});
    	            	
    	
//    	json=new JSONObject("{title:{text:'БЕЛЫЙ хлеб пшеничный'},price:{text:'60 р.'}, unit:{text:'бух.'}, count:{text:'35'}, total:{text:'2100'}, text:{text:'"+getString(R.string.nhz_text)+"'}, icon:{image_url:'http://eyakutia.com/wp-content/uploads/2012/04/yakutianhorserider_01.jpg'} }");    	
//    	matrixcursor.addRow(new Object[]{++_id,TYPE_CARD_ORDER_ITEM,0,json.toString()});
//    	    	
//    	json=new JSONObject("{title:{text:'ЧЕРНЫЙ хлеб пшеничный с зернами кукурузы'},price:{text:'48 р.'}, unit:{text:'бух.'}, count:{text:'25'}, total:{text:'1200'},  text:{text:'"+getString(R.string.nhz_text)+"'}, icon:{image_url:'http://eyakutia.com/wp-content/uploads/2012/04/yakutianhorserider_01.jpg'} }");    	
//    	matrixcursor.addRow(new Object[]{++_id,TYPE_CARD_ORDER_ITEM,0,json.toString()});
//    	
//    	json=new JSONObject("{title:{text:'Булка \"Пышка\"'},price:{text:'70 р.'}, unit:{text:'бул.'}, count:{text:'4'}, total:{text:'280'}, text:{text:'"+getString(R.string.nhz_text)+"'}, icon:{image_url:'http://eyakutia.com/wp-content/uploads/2012/04/yakutianhorserider_01.jpg'} }");    	
//    	matrixcursor.addRow(new Object[]{++_id,TYPE_CARD_ORDER_ITEM,0,json.toString()});
    	    	
    	json=new JSONObject("{title:{text:'БЕЛЫЙ хлеб пшеничный'},price:{text:'60 р.'}, unit:{text:'бух.'}, count:{text:'35'}, total:{text:'2100'}, text:{text:'"+getString(R.string.nhz_text)+"'}, icon:{image_url:'http://eyakutia.com/wp-content/uploads/2012/04/yakutianhorserider_01.jpg'} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_CARD_ORDER_STRING,0,json.toString()});
    	    	
    	json=new JSONObject("{title:{text:'ЧЕРНЫЙ хлеб пшеничный с зернами кукурузы'},price:{text:'48 р.'}, unit:{text:'бух.'}, count:{text:'25'}, total:{text:'1200'},  text:{text:'"+getString(R.string.nhz_text)+"'}, icon:{image_url:'http://eyakutia.com/wp-content/uploads/2012/04/yakutianhorserider_01.jpg'} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_CARD_ORDER_STRING,0,json.toString()});
    	
    	json=new JSONObject("{title:{text:'Булка \"Пышка\"'},price:{text:'70 р.'}, unit:{text:'бул.'}, count:{text:'4'}, total:{text:'280'}, text:{text:'"+getString(R.string.nhz_text)+"'}, icon:{image_url:'http://eyakutia.com/wp-content/uploads/2012/04/yakutianhorserider_01.jpg'} }");    	
    	matrixcursor.addRow(new Object[]{++_id,TYPE_CARD_ORDER_STRING,0,json.toString()});
    	
    	return matrixcursor;   
    	
    }
    
//--------------Adapter Callbacks----------------------
     
	@Override
	public void onClick(View v) {
				
			
		toast("clicked");
			
	}
	
//-----------------------Utilites--------------------------
	
	private void toast(String msg){
		Log.d(TAG, msg);
		Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
	}

}