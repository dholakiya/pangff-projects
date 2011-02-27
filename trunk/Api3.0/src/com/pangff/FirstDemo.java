package com.pangff;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnDismissListener;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

public class FirstDemo extends Activity {
	Button searchButton = null;
	List<File> txtFileInFolderList = new ArrayList<File>();
	List<String> txtFileNameInFolderList= new ArrayList<String>();
	private Intent intent = new Intent("com.firstDemo.android.MUSIC");
	private SQLiteDatabase db;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        searchButton = (Button) this.findViewById(R.id.searchButton);
       
        //创建数据库
        db = DBUtil.createDateBase(this, "firstdemo");
        createTable(db);//如果不存在创建
        select();//查询历史记录,初始化历史阅读文件
        
        //查询目录按钮点击事件
        searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startSearchFileDialog();
			}
		});
    }
    
    
    @Override
	protected void onResume() {
		// TODO Auto-generated method stub
        //启动背景音乐
        startService(intent);

		super.onResume();
	}


	/**
     * 过滤文件
     * @param currentPath
     */
    private void filterFiles(File currentPath){
    	if(currentPath.isDirectory()){
    		for(int i=0;i<currentPath.listFiles().length;i++){
    			if(currentPath.listFiles()[i].isFile()&&currentPath.listFiles()[i].getName().endsWith(".txt")){
    				txtFileInFolderList.add(currentPath.listFiles()[i]);
    			}
    			
    		}
    	}
    }
    /**
     * 更新列表数据，以及articleMap
     */
    private void changPathListData(){
    	Shakespeare.articleMap= new HashMap<Integer,File>();
    	for(int i=0;i<txtFileInFolderList.size();i++){
    		Shakespeare.articleMap.put(i, txtFileInFolderList.get(i));
    		txtFileNameInFolderList.add(txtFileInFolderList.get(i).getName());
    	}
    }
    /**
     * 启动文件浏览对话框
     */
    private void startSearchFileDialog(){
    	Dialog searchFileDialog = new SearchFileDialog(FirstDemo.this);
    	searchFileDialog.show();

    	searchFileDialog.setOnDismissListener(new OnDismissListener(){

			public void onDismiss(DialogInterface dialog) {
				// TODO Auto-generated method stub
				if(Shakespeare.buttonId == R.id.okButton){
					refreshPathList();
				}
			}
    		
    	});
  	
    }
    
    private void refreshPathList(){
    	ArticleListFragment listFragment = (ArticleListFragment) this.getFragmentManager().findFragmentById(R.id.list);
    	File currentPath = Shakespeare.currentFilePath;
    	txtFileInFolderList = new ArrayList<File>();
    	txtFileNameInFolderList= new ArrayList<String>();
    	filterFiles(currentPath);
    	changPathListData();
    	listFragment.setListAdapter(new ArrayAdapter<String>(
    			this, 
    			android.R.layout.simple_list_item_activated_1, 
    			txtFileNameInFolderList
    			
    	));
    	TextView pathTitle = (TextView) this.findViewById(R.id.pathTitle);
    	pathTitle.setText(currentPath.getPath());
    	if(txtFileNameInFolderList.size()>0){
    		listFragment.showDetails(0);
    	}
      
    }
     
    @Override
	protected void onPause() {
		// TODO Auto-generated method stub
    	this.stopService(intent);
		super.onPause();
	}
    
    @Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
    	select();//先查询
    	if(Shakespeare.readHistoryFile.getName().equals("readme.txt")){//没有阅读记录就添加
    		insert();
    	}else{//否则修改
    		update();
    	}
    	db.close();
    	Log.v("!!!!!!!!!!", "!!!!!!!!!!@@@@"+Shakespeare.currentReadFile.getName());  
    	Log.v("!!!!!!!!!!", "!!!!!!!!!!onDestroy");   
		super.onPause();
	}
	/**
     * 建表
     * @param db
     */
    private void createTable(SQLiteDatabase db){
    	 String sql = "CREATE TABLE IF NOT EXISTS readinfo (read_id integer primary key autoincrement, book_path varchar(60))";
         DBUtil.exSQL(db, sql);
         Log.v("", "!!!!!!!!!!createTable"); 
        // db.close();
    }
    /**
     * 删表
     * @param db
     */
    private void deleteTable(SQLiteDatabase db){
	   	 String sql = "DROP TABLE IF EXISTS firstdemo";
	     DBUtil.exSQL(db, sql);
	     db.close();
    }
    /**
     * 插入当前阅读的文件
     * @param db
     */
    private void insert(){
    	String path = Shakespeare.currentReadFile.getPath();
    	String bindArgs[] = {path};
    	String sql = "insert into readinfo(book_path) values(?)";
    	DBUtil.exSQL(db,sql,bindArgs);
    	Log.v("", "!!!!!!!!!!insert"); 
    	//db.close();
    }
    /**
     * 修改当前阅读文件
     * @param db
     */
    private void update(){
    	String currentPath = Shakespeare.currentReadFile.getPath();
    	String historyPath = Shakespeare.readHistoryFile.getPath();
    	String bindArgs[] = {historyPath,currentPath};
    	String sql = "update readinfo set book_path=? where book_path=?";
    	DBUtil.exSQL(db,sql,bindArgs);
    	Log.v("", "!!!!!!!!!!update"); 
    	//db.close();
    }
    /**
     * 删除阅读记录,当文件被从sdcard上删除时，要删除其阅读记录
     */
    private void delete(){
    	String currentPath = Shakespeare.currentReadFile.getPath();
    	String bindArgs[] = {currentPath};
    	String sql = "delete from readinfo where book_path=?";
    	DBUtil.exSQL(db,sql,bindArgs);
    	Log.v("", "!!!!!!!!!!delete"); 
    	//db.close();
    }
    
    private void select(){
    	String sql = "select * from readinfo";
    	Cursor cursor= DBUtil.exSelectSQL(db, sql);
    	while (cursor.moveToNext()) {  
		    String path = cursor.getString(1);//获取第二列的值  
		    Shakespeare.readHistoryFile = new File(path);
		}  
    	cursor.close();  
    	Log.v("!!!!!!!!!!", "!!!!!!!!!!select"); 
		//db.close();
    }
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
//		// TODO Auto-generated method stub
//		if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { 
//            FirstDemo.this.finish();
//        } 
//		 Log.v("!!!!!!!!!!", "!!!!!!!!!!onKeyDown");   
		return super.onKeyDown(keyCode, event);
	}
    
    
}