package com.pangff;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SearchFileDialog extends Dialog{

	protected SearchFileDialog(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	@Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        //生成动态数组，加入数据   
      
        setContentView(R.layout.searchdialog); 
        this.setTitle("浏览目录");


        ListView list = (ListView) findViewById(R.id.pathList);  
        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();   
        File file = android.os.Environment.getExternalStorageDirectory(); 
        String files[] = file.list();
        for(int i=0;i<files.length-5;i++)   
        {   
             HashMap<String, Object> map = new HashMap<String, Object>();   
             map.put("ItemImage", R.drawable.folder);//图像资源的ID   
             map.put("ItemTitle", files[i]);   
            listItem.add(map);   
        }   
     //生成适配器的Item和动态数组对应的元素   
         SimpleAdapter listItemAdapter = new SimpleAdapter(this.getContext(),listItem,//数据源    
            R.layout.path_list_items,//ListItem的XML实现   
            //动态数组与ImageItem对应的子项           
             new String[] {"ItemImage","ItemTitle"},    
             //ImageItem的XML文件里面的一个ImageView,两个TextView ID   
            new int[] {R.id.ItemImage,R.id.ItemTitle}   
        ); 
         list.setAdapter(listItemAdapter);
    }

	@Override
	public View onCreatePanelView(int featureId) {
		// TODO Auto-generated method stub
		return super.onCreatePanelView(featureId);
	} 


}
