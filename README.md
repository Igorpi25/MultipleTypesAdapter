MultipleTypesAdapter
====================

Архитектурное решение, которое позволяет использовать несколько типов отображаемых элементов, внутри одного адаптера. Данное решение позволяет в будущем добавлять новые типы, без изменения существующего адаптера. 

Динамическая кастомизация:

<img src="Screenshots/screenshot_demo_1.png" width="200">&nbsp;&nbsp;&nbsp;<img src="Screenshots/screenshot_demo_2.png" width="200">

Биндинг ImageView из URL используя [Glid][9]. Вложенный адаптер GridView внутри адаптера ListView:

<img src="Screenshots/screenshot_demo_3.png" width="200">

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
        
//---------------Preparing types wich will be used in adapter----------------------------

        adapter.addItemHolder(TYPE_LINK_USER, new CursorItemHolderLink(getActivity(),this,this)); 
        adapter.addItemHolder(TYPE_HEADER, new CursorItemHolderHeader(getActivity(),this));
        adapter.addItemHolder(TYPE_BUTTON, new CursorItemHolderButton(getActivity(),this));
        
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(adapter);
        
        adapter.changeCursor(createCursorForAdapter());
        
        return view;
    }
    
    
    protected MatrixCursor createCursorForAdapter(int _id){

        MatrixCursor matrixcursor=new MatrixCursor(new String[]{adapter.COLUMN_ID, adapter.COLUMN_TYPE, adapter.COLUMN_KEY, adapter.COLUMN_VALUE});    	
        
        JSONObject json;    	
        try{
            //Header
            json=new JSONObject("{key:{text:'Contacts'}, value:{visible:false}, label:{visible:false} }");
            matrixcursor.addRow(new Object[]{++_id,TYPE_HEADER,0,json.toString()});
            
            //Users
            json=new JSONObject("{ name:{text:'Igor Ivanov'}, status:{text:'Android Developer'}, label:{ visible:false }, button:{visible:false, tag:'link_user_button', text:'Show'}, icon:{image_url:'https://pp.vk.me/c616830/v616830795/1121c/AwzilQ3NWLs.jpg'} }");
            matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_USER,11,json.toString()});
            
            json=new JSONObject("{name:{text:'Stepan Sotnikov'}, status:{text:'Server Admin'}, label:{ visible:true, text:'(new)' }, button:{tag:'link_user_button', text:'Accept', background: "+R.drawable.drawable_button_dialog_positive+", text_color: "+R.color.color_white+"}, icon:{image_url:'https://pp.vk.me/c316130/u3906727/d_80cd5ad1.jpg'} }");
            matrixcursor.addRow(new Object[]{++_id,TYPE_LINK_USER,12,json.toString()});
            
            //Button "Add"
            json=new JSONObject("{button:{tag:'link_user_button', text:'Add', background: "+R.drawable.drawable_button_dialog_alter+", text_color: "+R.color.color_white+"} }");
            matrixcursor.addRow(new Object[]{++_id,TYPE_BUTTON_SMALL,43,json.toString()});
        }cathc(JSONException e){
            //Log.e("FragmentDemo","onCreateView JSONException createMergeCursor e="+e)
        }
        return matrixcursor;
    }
    
//---------------------------Callbacks-------------------------  

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

<img src="Screenshots/screenshot_code_example.png" width="200">

Оптимизация
-----------
Реализован паттерн ViewHolder, для каждого типа Item

JSON-View биндеры
-----------------
Решение позволяющее сократить код и делать жизнь программиста веселее. Нужно для того, чтобы однажды определив View-биндер, использовать его всю жизнь.

Предположим я создал Item-тип, в layout-е которого есть один ImageView, три TextView, и один Button:

<img src="Screenshots/screenshot_binder.png" width="200">

для него используются такой JSON:
```json
{ 
  "icon":{  "image_url": "https://pp.vk.me/c616830/v616830795/1121c/AwzilQ3NWLs.jpg" }, 
  "name": { "text": "Igor Ivanov" }, 
  "status": { "text": "Android Developer" }, 
  "label": { "visible": true }, 
  "button":{ "tag": "link_user_button", "text": "Accept"}
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
		
		//Берем JSON из курсора
		JSONObject json = new JSONObject(CursorMultipleTypesAdapter.getValue(cursor));
		
		//Читаем параметры ImageView
		new BinderImageView(context).bind(imageview_icon, json.getJSONObject("icon"));
		
		//Читаем параметры 
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

`BinderImageView`, `BinderTextView`, `BinderButton` - это классы View-биндеров. Они берут параметры переданного в `bind(...)` json-объекта и биндят к соответствующим свойствам View. У каждого биндер-класса есть свои свойства и параметры: 

Например, у BinderTextView:
* `text` - соответствует тексту TextView
* `text_size` - размер шрифта
* `text_size_unit` - величина в которых задана `text_size` (`COMPLEX_UNIT_SP`, `COMPLEX_UNIT_DP`, и т.п.)
* `text_color` -  цвет текста. Нужно передать id color-ресурса.

Свойства BinderButton:
* `tag` - строка переданная вот тут будет присвоена к tag. Используется в обработчике OnClickListener, чтобы выяснить источник колбэка
* `background` - ресурс-id фонового drawable. Тут можно использовать color, drawable, selector
* `text_color` - цвет текста. Нужно передать id color-ресурса
* `text` - соответствует тексту Button

Свойства ImageView:
* `tag` - строка переданная вот тут будет присвоена к tag. Используется в обработчике OnClickListener, чтобы выяснить источник колбэка. ImageView может быть кликабельна
* `background` - ресурс-id фонового drawable. Тут можно использовать color, drawable, selector
* `image_res` - ресурс-id изображения
* `image_url` - url-адрес изображения. Если не задано или url-адрес неправильный, то будет отображен drawable указанный в `image_res`

Вы можете добавить нужные свойства, или изменить название существующего свойства. Также вы можете задать другой способ биндинга. Аналогично вы можете создать биндер для собственного кастомного View

**Значение по умолчанию:**
Чтобы избежать дублирования кода, биндер использует значения по умолчанию. Если в `json` объекте отсутствует значение для требуемого свойства View, то используется значение по умолчанию. Значение по умолчанию задается двумя путями:

1. Значение по умолчанию, заданное при создании класса биндера. Для этого нужно реализовать абстрактную функцию `createDefaultJson()` класса `Binder`
2. Значение переданное в конструкторе при создании объекта биндера. Для этого `json`-объект передается во втором параметре конструктора. 

ВНИМАНИЕ! Значение по умолчанию в конструкторе, перекрывает значение по умолчанию заданный через функцию `createDefaultJson()`. А значение переданное в качестве параметра для фукнкции `binder`, перекрывает значение в конструкторе.

Пример биндера со значениями по умолчанию:
```java
public class BinderButton extends Binder<Button> {

	...
	
	@Override
	protected JSONObject createDefaultJson(){
		return new JSONObject("{visible: true, text: 'asds', text_size: '16'}");
	}
}

...
Buttton button1=null;
Buttton button2=null;
Buttton button3=null;

jsonA=new JSONObject("{text_size:36}");
jsonB=new JSONObject("{text: 'Add contact'}");

json_holder=new JSONObject("{tag:'button_normal', text:'Send message', text_size: 12}"); 

new BinderButton(context).bind(button1, jsonA);
new BinderButton(context,json_holder).bind(button2, jsonA);
new BinderButton(context,json_holder).bind(button3, jsonB);
```

Конечный `json` для:<br>
`button1` будет таким: `{visible:true, text:'asds', text_size: '36'}`<br>
`button2` будет таким: `{tag:'button_normal', visible:true, text:'Send message', text_size: '36'}`<br>
`button3` будет таким: `{tag:'button_normal', visible:true, text:'Add contact', text_size: '12'}`

<img src="Screenshots/screenshot_binder_default.png" width="200">

События при клике
------------------
Архитектура позволяет выводить обработку колбэк-событий, интерфейсов OnClickListener и OnItemClickListener во фрагмент. Это упрощает структуру кода, и дает удивительную гибкость.

Вся логика может храниться в одном месте - в классе фрагмента:
```java
public class FragmentDemo extends DialogFragment implements OnItemClickListener,OnClickListener{
    
    protected static final int TYPE_LINK_USER = 1;
    ...
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       	...
        adapter.addItemHolder(TYPE_LINK_USER, new CursorItemHolderLink(mContext, FragmentDemo.this, FragmentDemo.this)); 
        ...
        return view;
    }
    
//---------------------------Callbacks-------------------------  

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(adapter.getType(adapter.getCursor())){
            case TYPE_LINK_USER:{
                int user_id=adapter.getKey(adapter.getCursor());
                //Item clicked
            }break;
        }
    }
    
    @Override
    public void onClick(View v) {
        
      if(v.getTag(R.id.details_item_link_button)!=null){
          int key=(Integer)v.getTag(R.id.details_item_link_button);
          int tag=(String)v.getTag();
          //Button within item clicked
      }
      
    }
    
}
```

Для одного класса `ItemHolder` можно задать несколько типов колбэков. Рассмотрим пример кастомизации `CursorItemHolderLink`:
```java
public class FragmentDemo extends DialogFragment implements OnItemClickListener,OnClickListener{
    
    protected static final int TYPE_LINK_USER = 1;
    protected static final int TYPE_LINK_GROUP = 2;
    protected static final int TYPE_LINK_ADMIN = 3;
    
    ...
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       	...
        adapter.addItemHolder(TYPE_LINK_USER, new CursorItemHolderLink(mContext, FragmentDemo.this, FragmentDemo.this));
        adapter.addItemHolder(TYPE_LINK_GROUP, new CursorItemHolderLink(mContext, FragmentDemo.this, FragmentDemo.this));
        
        //Кастомизируем поведение на кнопку 
        adapter.addItemHolder(TYPE_LINK_ADMIN, new CursorItemHolderLink(mContext, FragmentDemo.this, new OnClickListener(){
        	
        	@Override
		public void onClick(View v) {
			toast("You can't remove admin from contacts. Only God can do it");
		}
        })); 
        ...
        return view;
    }
    ...
//---------------------------Callbacks-------------------------  

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch(adapter.getType(adapter.getCursor())){
            case TYPE_LINK_USER:{
                int user_id=adapter.getKey(adapter.getCursor());
                //Item user has been clicked
            }break;
            
            case TYPE_LINK_GROUP:{
                int group_id=adapter.getKey(adapter.getCursor());
                //Item group has been clicked
            }break;
        }
    }
    
    @Override
    public void onClick(View v) {
        
      if(v.getTag(R.id.details_item_link_button)!=null){
          int key=(Integer)v.getTag(R.id.details_item_link_button);
          int tag=(String)v.getTag();
          //Button inside item clicked
      }
      
    }
    
}
```

**Реализация нестандартного колбэка** ничем не отличается от реализации `onClickListener`. Посмотрите исходные коды

Используемые библиотеки
-----------------------

* [Volley][2]
* [Glid][9] - для загрузки изображений из интернета, и кэширования
 
Запуск демонстрационного приложения:
------------------------------------
* Добавьте проект в Workspace
* Выполните Project->Clean
* Можно запустить


Пример использования библиотеки вы можете видеть в проекте [Profile][3]

[2]: https://github.com/mcxiaoke/android-volley
[9]: https://github.com/bumptech/glide
[3]: https://github.com/Igorpi25/Profile
