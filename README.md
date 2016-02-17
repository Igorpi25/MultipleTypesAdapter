MultipleTypesAdapter
====================

Архитектурное решение, которое позвляет использовать один адаптер с несколькими типами Item внутри одного ListView. Данное решение позволяет в будущем новые виды Item без изменения кода адаптера. Это актуально если адаптер используется в наследуемых фрагментах.

Вы можете ввести новые типы Item, не изменяя класса адаптера. Также вы можете ввести OnItemClickListener, как для новых типов, так и уже существующих. Кроме того вы можете реализвать OnClickListener, и другие типы callback-ов для любого View.

Кастомизация приветствуется и одобряется, на всех уровнях архитектуры.

Пример кода
-----------

```java
...
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderButton;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderHeader;
import com.ivanov.tech.multipletypesadapter.cursoradapter.CursorItemHolderLink;
...
import android.database.Cursor;
import android.database.MatrixCursor;
...

public class FragmentDemo extends DialogFragment implements OnItemClickListener,OnClickListener{
    
    protected static final int TYPE_HEADER =0;
    protected static final int TYPE_LINK_USER = 1;
    protected static final int TYPE_BUTTON =2;
    
    protected ListView listview;
    protected CursorMultipleTypesAdapter adapter=null;
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = null;
        view = inflater.inflate(R.layout.fragment_demo, container, false);
        
        listview=(ListView)view.findViewById(R.id.fragment_demo_listview);
        
        adapter=new CursorMultipleTypesAdapter(getActivity(),null,adapter.FLAG_AUTO_REQUERY);
        adapter.addItemHolder(TYPE_LINK_USER, new CursorItemHolderLink(getActivity(),this,this)); 
        adapter.addItemHolder(TYPE_HEADER, new CursorItemHolderHeader(getActivity(),this));
        adapter.addItemHolder(TYPE_BUTTON, new CursorItemHolderButton(getActivity(),this));
        
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(adapter);
        
        adapter.changeCursor(getCursorForAdapter());
        
        return view;
    }
    
    protected MatrixCursor getCursorForAdapter(int _id){

        MatrixCursor matrixcursor=new MatrixCursor(new String[]{adapter.COLUMN_ID, adapter.COLUMN_TYPE, adapter.COLUMN_KEY, adapter.COLUMN_VALUE});    	
        
        JSONObject json;    	
        try{
            //Header
            json=new JSONObject("{key:{text:'Contacts:'}, value:{visible:false}, label:{visible:false} }");    	
            matrixcursor.addRow(new Object[]{++_id,TYPE_HEADER,0,json.toString()});
            
            //Users
            json=new JSONObject("{ name:{text:'Igor Ivanov'}, status:{text:'Android Developer'}, label:{ visible:false }, button:{tag:'link_user_button', text:'Accept'}, icon:{image_url:'https://pp.vk.me/c616830/v616830795/1121c/AwzilQ3NWLs.jpg'} }");    	
            matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_USER,11,json.toString()});
            
            json=new JSONObject("{name:{text:'Stepan Sotnikov'}, status:{text:'Server Admin'}, label:{ visible:false }, button:{tag:'link_user_button', text:'Add'}, icon:{image_url:'https://pp.vk.me/c316130/u3906727/d_80cd5ad1.jpg'} }");    	
            matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_USER,12,json.toString()});
            
            //Button "Add"
            json=new JSONObject("{button:{tag:'button_normal', text:'Add'} }");    	
            matrixcursor.addRow(new Object[]{++_id,TYPE_BUTTON,41,json.toString()});
        }cathc(JSONException e){
            //Log.e("FragmentDemo","onCreateView JSONException createMergeCursor e="+e)
        }
        return matrixcursor;
    }
    
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(adapter.getType(adapter.getCursor())){
            case TYPE_LINK_USER:{
                int user_id=adapter.getKey(adapter.getCursor());
                //Item with key=(11 or 12) has been clicked
            }break;
        }
    }
    
    @Override
    public void onClick(View v) {
        
      if(v.getTag(R.id.details_item_link_button)!=null){
          int key=(Integer)v.getTag(R.id.details_item_link_button);
          int tag=(String)v.getTag();
          //Button with tag="link_user_button" and item's key=(11 or 12) has been clicked
      }
      if(v.getTag(R.id.details_item_button_button)!=null){
          int key=(Integer)v.getTag(R.id.details_item_button_button);
          int tag=(String)v.getTag();
          //Button with tag="button_normal" and item's key=41 has been clicked
      }
      
    }
    
}
```

(Скриншот FragmentDemo)

Оптимизация
-----------
Реализован паттерн ViewHolder, для каждого типа Item

JSON-View биндеры
------------
Еще одно архитектурное решение, позволяющее сократить код и делать жизнь программиста веселее. Вы можете определить View-биндер однажды, и использовать его всю жизнь.

Предположим я создал Item-тип, внутри которого есть один ImageView, три TextView, и один Button:

(обрезанный скрин Link User)

для него используются(биндится) такой JSON:
```json
{ 
  "icon":{  "image_url": "https://pp.vk.me/c616830/v616830795/1121c/AwzilQ3NWLs.jpg" } 
  "name": { "text": "Igor Ivanov" }, 
  "status": { "text": "Android Developer" }, 
  "label": { "visible": false }, 
  "button":{ "tag": "link_user_button", "text": "Accept"}, 
}
```
Фрагмент кода CursorItemHolderLink:
```java
...
@Override
public View getView(View convertView, ViewGroup parent, Cursor cursor) {
				
		View view;
		
		if(convertView==null){
			LayoutInflater layoutinflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view= layoutinflater.inflate(R.layout.details_item_link, parent, false);
			
			textview_name = (TextView) view.findViewById(R.id.details_item_link_name);
			textview_status = (TextView) view.findViewById(R.id.details_item_link_status);
			textview_label = (TextView) view.findViewById(R.id.details_item_link_label);
			imageview_icon = (ImageView) view.findViewById(R.id.details_item_link_icon);
			button = (Button) view.findViewById(R.id.details_item_link_button);
		}else{
			view=convertView;
		}
		
		JSONObject json = new JSONObject(CursorMultipleTypesAdapter.getValue(cursor));
			
		new BinderImageView(context).bind(imageview_icon, json.getJSONObject("icon"));
		
		new BinderTextView(context).bind(textview_name, json.getJSONObject("name"));
		new BinderTextView(context).bind(textview_status, json.getJSONObject("status"));
		new BinderTextView(context).bind(textview_label, json.getJSONObject("label"));
	  
		if( new BinderButton(context,new JSONObject("{text_size:12, text_color:'"+R.color.color_selector_font+"'}")).bind(button, json.getJSONObject("button")) ){	
			  button.setTag(button.getId(), CursorMultipleTypesAdapter.getKey(cursor));				
			  if(onclicklistener!=null)
			  button.setOnClickListener(onclicklistener);
		}			
			
		return view;
}
...
```

Обратите внимание на  `BinderImageView`, `BinderTextView`, `BinderButton` - это классы View-биндеров. Они берут параметры переданного в `bind(...)` json-объекта и биндят к свойствам View объекта. У каждого биндер-класса есть свои свойства и параметры: 

Например, у BinderTextView следующие параметры:
* `text` - соответствует тексту TextView
* `text_size` - размер шрифта
* `text_size_unit` - величина в которых записано `text_size` (`COMPLEX_UNIT_SP`, `COMPLEX_UNIT_DP`, и т.п.)

Вы можете определить новые параметры, или новый View-биндер для вашего кастомного View-класса.





