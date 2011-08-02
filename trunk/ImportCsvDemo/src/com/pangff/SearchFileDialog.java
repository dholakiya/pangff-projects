package com.pangff;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.AdapterView.OnItemClickListener;

public class SearchFileDialog extends Dialog{
	private ImportCsvDemoActivity importCsvDemoActivity;
	private List<HashMap<String, Object>> listItem = null;
	private List<File> filePathList =null;
	private ListView pathList = null;
	private Button okButton = null;
	private Button backButton = null;
	private Button cancelButton = null;
	//当前选中目录
	private File currentPath = null ;
	//当前选中目录
	private File currentFile = null ;
	protected SearchFileDialog(Context context) {
		super(context);
		importCsvDemoActivity = (ImportCsvDemoActivity) context;
		// TODO Auto-generated constructor stub
	}
	
	@Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
      
        setContentView(R.layout.searchdialog); 
        this.setTitle("浏览目录：/mnt/sdcard/");//对话框默认标题

        pathList = (ListView) findViewById(R.id.pathList); //ListView
        
        File file = android.os.Environment.getExternalStorageDirectory();//获得sdcard目录 
        File files[] = file.listFiles();
        
        filePathList = new ArrayList<File>();
        this.fileFilter(files);//文件过滤
        this.initPathListDate();//目录列表数据初始化
        /**
         * 列表项点击事件
         */
         pathList.setOnItemClickListener (new OnItemClickListener() {
        	 
             public void onItemClick (AdapterView<?> arg0, View arg1, int arg2,
                     long arg3) {
                 // TODO Auto-generated method stub
            	pathClick(arg2);
             }
         });
         
         okButton = (Button) this.findViewById(R.id.okButton);
         backButton = (Button) this.findViewById(R.id.backButton);
         cancelButton = (Button) this.findViewById(R.id.cancelButton);
         okButton.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onOkButtonClick();
			}
         });
         backButton.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackButtonClick();
			}
         });
         cancelButton.setOnClickListener(new Button.OnClickListener(){
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onCancelButtonClick();
			}
         });
    }
	
	/**
	 * 点击了确定按钮
	 */
	private void onOkButtonClick(){
		InputStreamReader reader;
		try {
			reader  = new InputStreamReader(new FileInputStream(currentPath), "gb2312");
			importCsvDemoActivity.doImport(reader);
			reader.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dismiss();
	}
	/**
	 * 点击了取消按钮
	 */
	private void onCancelButtonClick(){
		this.dismiss();
	}
	/**
	 * 点击了返回按钮
	 */
	private void onBackButtonClick(){
		if(currentPath!=null){
			File file = currentPath.getParentFile();
			//只能返回到sdcard目录
			if(file!=null&&!file.getName().equals("sdcard")){
				File files[] = file.getParentFile().listFiles();
				this.fileFilter(files);
				this.initPathListDate();
				currentPath = file;
				this.setTitle("文件浏览："+file.getPath());
			}
		}
	}
	/**
	 * 保证该文件是个目录，并且该目录下有文件才能加入
	 * @param filePath
	 */
	private void fileFilter(File filePath[]){
		/**
		 * 先用list量试探
		 */
		List<File> list = new ArrayList<File>();
		
		for(int i=0;i<filePath.length;i++){
       	 //if(filePath[i].isDirectory()&&filePath[i].list()!=null&&filePath[i].list().length>0){
       		list.add(filePath[i]);
       	 //}
        }
		/**
		 * 如果list有数据filePathList再new
		 */
		if(list.size()>0){
			filePathList = new ArrayList<File>();
		}
		for(int i=0;i<list.size();i++){
       		 filePathList.add(list.get(i));
        }
	}
	/**
	 * 路径列表单击事件
	 * @param index
	 */
	private void pathClick(int index) {
		// TODO Auto-generated method stub
		File childFilePath = filePathList.get(index);
		if(childFilePath.isDirectory()){
			File filePath[] = childFilePath.listFiles();
			this.fileFilter(filePath);
			this.initPathListDate();
		}else{
			currentFile = childFilePath;
			this.setTitle("文件浏览："+currentFile.getPath());
		}
		currentPath = childFilePath;
		this.setTitle("文件浏览："+childFilePath.getPath());
	} 
	/**
	 *初始化路径列表数据
	 */
	private void initPathListDate(){
		listItem = new ArrayList<HashMap<String, Object>>(); 
	 	for(int i=0;i<filePathList.size();i++){   
             HashMap<String, Object> map = new HashMap<String, Object>();   
             map.put("ItemImage", R.drawable.folder);//图像资源的ID   
             map.put("ItemTitle", filePathList.get(i).getPath());   
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
        pathList.setAdapter(listItemAdapter);
	}
	


}
