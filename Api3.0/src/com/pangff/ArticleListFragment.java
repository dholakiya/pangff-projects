package com.pangff;

import java.io.File;
import java.io.FileWriter;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArticleListFragment  extends ListFragment{
	
	  boolean mDualPane;//标示是否是横向布局，右侧reader视图是否可见
      int mCurCheckPosition = 0;//鼠标所选的标题索引
      int mShownCheckPosition = -1;//当前reader视图显示的内容索引
      String mCurCheckFileName = "";
      String mShownCheckFileName = "";
     
      @Override
      //当该fragment所属activity创建时
      public void onActivityCreated(Bundle savedInstanceState) {
    	 
          super.onActivityCreated(savedInstanceState);
         
          List<String> articleNameList = new ArrayList<String>();
        //  getResources().getAssets().openFd("").
          File articlePath = this.getActivity().getDir("data",  Context.MODE_WORLD_WRITEABLE);
          File readMeFile = new File(articlePath.getPath(),"readme.txt");
         
         
          try {
        	  if(!readMeFile.exists()){ 
               	readMeFile.getParentFile().mkdirs(); 
               	readMeFile.createNewFile();
                writeFile(readMeFile,true,Shakespeare.readmeContent);
              }
        	 
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 
  		  
  		  /**
  		   * 首先要从数据库查出来上次阅读文件
  		   * 若不存在，标记初始阅读的文件
  		   */
			 Log.v("!!!!!!!!!!", "!!!!!!!!!!readHistoryFile@@"+Shakespeare.readHistoryFile.getName());
  		  if(Shakespeare.readHistoryFile!=null){
  			Shakespeare.currentReadFile=Shakespeare.readHistoryFile;
  		  }else{
  			Shakespeare.currentReadFile=readMeFile;
  		  }
  		  Log.v("!!!!!!!!!!", "!!!!!!!!!!currentReadFile"+Shakespeare.currentReadFile.getName()); 
  		  if(Shakespeare.currentReadFile.getParent()!=null){
  			 File files[] =  Shakespeare.currentReadFile.getParentFile().listFiles();
  			 for(int i=0;i< files.length;i++){
    	 		  articleNameList.add(files[i].getName());
    		  	  Shakespeare.articleMap.put(i,files[i]);
    		  	  if(Shakespeare.currentReadFile.getPath().equals(files[i].getPath())
    		  			  &&Shakespeare.currentReadFile.getName().equals(files[i].getName())){
    		  		mCurCheckPosition = i;
    		  	  }
    		  }
  		  }
 
          // Populate list with our static array of titles.
          setListAdapter(new ArrayAdapter<String>(getActivity(),
                  R.layout.simple_list_item_activated_1, articleNameList));

          // Check to see if we have a frame in which to embed the details
          // fragment directly in the containing UI.
          View detailsFrame = getActivity().findViewById(R.id.viewer);
          mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

          if (savedInstanceState != null) {
              // Restore last state for checked position.
              mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
              mShownCheckPosition = savedInstanceState.getInt("shownChoice", -1);
          }
          
           
          if (mDualPane) {
              // In dual-pane mode, the list view highlights the selected item.
              getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
              // Make sure our UI is in the correct state.
              showDetails(mCurCheckPosition);
          }
         
      }
      
      
      
      
      private void  writeFile(File file, boolean append,String content)   {
    	             try    {
    	               FileWriter filewriter  =   new  FileWriter(file, true);
    	               // 删除原有文件的内容 
    	              // java.io.RandomAccessFile initFile =   new  java.io.RandomAccessFile(file.getPath(), " rw " );
    	              // initFile.setLength( 0 );
    	                // 写入新的文件内容 
    	               filewriter.write(content);
    	               filewriter.close();
    	               filewriter.flush();
    	            }   catch  (Exception e)   {
    	               e.printStackTrace();
    	           } 
    	       } 


      @Override
      /**
       *存储窗口状态
       */
      public void onSaveInstanceState(Bundle outState) {
    	  Log.v("!!!!!!!!!!", "@@@@@@@@@@@@@@@@"); 
          super.onSaveInstanceState(outState);
          outState.putInt("curChoice", mCurCheckPosition);//初始为0
          outState.putInt("shownChoice", mShownCheckPosition);//初始为-1
      }

      @Override
      public void onListItemClick(ListView l, View v, int position, long id) {
          showDetails(position);
      }

      /**
       * Helper function to show the details of a selected item, either by
       * displaying a fragment in-place in the current UI, or starting a
       * whole new activity in which it is displayed.
       */
      void showDetails(int index) {
          mCurCheckPosition = index;

          if (mDualPane) {
              // We can display everything in-place with fragments, so update
              // the list to highlight the selected item and show the data.
              getListView().setItemChecked(index, true);

            //  if (mShownCheckPosition != mCurCheckPosition) {
                  // If we are not currently showing a fragment for the new
                  // position, we need to create and install a new one.
                  ArticleReaderFragment df = ArticleReaderFragment.newInstance(index);

                  // Execute a transaction, replacing any existing fragment
                  // with this one inside the frame.
                  FragmentTransaction ft = getFragmentManager().beginTransaction();
                  ft.replace(R.id.viewer, df);
                  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                  ft.commit();
                  mShownCheckPosition = index;
            //  }

          } else {
              // Otherwise we need to launch a new activity to display
              // the dialog fragment with selected text.
              Intent intent = new Intent();
              intent.setClass(getActivity(), ArticleReaderActivity.class);
              intent.putExtra("index", index);
              startActivity(intent);
          }
      }
	
     

}
