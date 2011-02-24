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
import android.os.Bundle;
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
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        searchButton = (Button) this.findViewById(R.id.searchButton);
       
        searchButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startSearchFileDialog();
			}
		});
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
}