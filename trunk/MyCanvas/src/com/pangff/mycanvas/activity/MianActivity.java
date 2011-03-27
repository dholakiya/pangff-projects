package com.pangff.mycanvas.activity;






import java.io.File;
import java.util.Calendar;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ActionBar.Tab;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;

import com.pangff.mycanvas.interfaces.ISketchPadCallback;
import com.pangff.mycanvas.utils.BitmapUtil;
import com.pangff.mycanvas.utils.TabListener;
import com.pangff.mycanvas.view.CanvasView;
import com.pangff.mycanvas.view.MainFragment;
import com.pangff.mycanvas.view.SearchFileDialog;

public class MianActivity extends Activity implements ISketchPadCallback{

	 
	private CanvasView canvasView = null;
	
	private MenuItem undoItem;
	private MenuItem redoItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        canvasView = (CanvasView)this.findViewById(R.id.curveMap);
       
    }
    

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	 
        getMenuInflater().inflate(R.layout.menu_actions, menu);
        ActionBar bar = this.getActionBar();
         
        Tab mainPageTab = bar.newTab();
        Tab lookPageTab = bar.newTab();
        mainPageTab.setCustomView(R.layout.mainpagetab);
        lookPageTab.setCustomView(R.layout.lookpagetab);
        bar.addTab(mainPageTab.setTabListener(new TabListener(new MainFragment("内容"))));
        bar.addTab(lookPageTab.setTabListener(new TabListener(new MainFragment("查看"))));

        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        bar.setDisplayShowCustomEnabled(false);
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
        
        undoItem = menu.findItem(R.id.action_undo);
        redoItem = menu.findItem(R.id.action_redo);
        
        canvasView.setCallback(this);
        
        undoItem.setEnabled(false);
        redoItem.setEnabled(false);
        return true;
    }

    public void onSaveClick(MenuItem item) {
    	SearchFileDialog dialog = new SearchFileDialog(MianActivity.this);
    	dialog.show();
    	//String strFilePath = getStrokeFilePath();
    }
    
    public void saveDo(String strFilePath){
    	//String strFilePath = dialog.getCurrentPath().getPath();
    	Log.v("PATHSSSS", strFilePath);
 	    Bitmap bmp = canvasView.getCanvasSnapshot();
 	    String strFileName = getStrokeFileName();
 	    if (null != bmp){
 	      BitmapUtil.saveBitmapToSDCard(bmp, strFilePath,strFileName);
 	    }
    }
    
    public void onUndoClick(MenuItem item) {
    	canvasView.undo();
    	redoItem.setEnabled(canvasView.canRedo());
    	undoItem.setEnabled(canvasView.canUndo());
    }
    
    
    public void onRedoClick(MenuItem item) {
    	canvasView.redo();
    	redoItem.setEnabled(canvasView.canRedo());
    	undoItem.setEnabled(canvasView.canUndo());
    }

	public int getInitColor(){
		return Color.BLUE;
	}
	
    public String getStrokeFilePath(){
        File sdcarddir = android.os.Environment.getExternalStorageDirectory();
        String strDir = sdcarddir.getPath() + "/DCIM/mycanvas/";
      
        String strFilePath = strDir;
        
        return strFilePath;
    }
    
    public String getStrokeFileName(){
        String strFileName = "";
        
        Calendar rightNow = Calendar.getInstance();
        int year = rightNow.get(Calendar.YEAR);
        int month = rightNow.get(Calendar.MONDAY);
        int date = rightNow.get(Calendar.DATE);
        int hour = rightNow.get(Calendar.HOUR);
        int minute = rightNow.get(Calendar.MINUTE);
        int second = rightNow.get(Calendar.SECOND);
        
        strFileName = String.format("%02d%02d%02d%02d%02d%02d.png", year, month, date, hour, minute, second);
        return strFileName;
    }


	@Override
	public void onDestroy(CanvasView obj) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void onTouchDown(CanvasView obj, MotionEvent event) {
		// TODO Auto-generated method stub
		undoItem.setEnabled(true);
	}


	@Override
	public void onTouchUp(CanvasView obj, MotionEvent event) {
		// TODO Auto-generated method stub
		
	}
  
}