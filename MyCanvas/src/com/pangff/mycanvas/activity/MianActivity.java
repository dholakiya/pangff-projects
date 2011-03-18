package com.pangff.mycanvas.activity;






import com.pangff.mycanvas.activity.R;
import com.pangff.mycanvas.utils.TabListener;
import com.pangff.mycanvas.view.MainFragment;

import android.app.ActionBar;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.View.OnClickListener;

public class MianActivity extends Activity
        implements View.OnClickListener, ActionBar.TabListener {

	 private OnClickListener mClickListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    //	this.requestWindowFeature(Window.FEATURE_NO_TITLE); 
    	requestWindowFeature(Window.FEATURE_ACTION_BAR);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        getWindow().setTitleColor(Color.RED);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	 
        getMenuInflater().inflate(R.layout.menu_actions, menu);
        ActionBar bar = this.getActionBar();
         

        Tab mainPageTab = bar.newTab().setText("主页");
        Tab lookPageTab = bar.newTab().setText("查看");
        bar.addTab(mainPageTab.setTabListener(new TabListener(new MainFragment("内容"))));
        bar.addTab(lookPageTab.setTabListener(new TabListener(new MainFragment("查看"))));

      // bar.setDisplayOptions(options)
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
       // bar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
       // bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_TITLE);
       // bar.setDisplayOptions(0, ActionBar.DISPLAY_HIDE_HOME);
        bar.setDisplayOptions(0, ActionBar.DISPLAY_SHOW_CUSTOM);
        bar.setDisplayShowCustomEnabled(false);
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayShowTitleEnabled(false);
       
        return true;
    }

    public void onClick(View v) {
    	
    	mClickListener.onClick(v);
    }
    
   // new ColorPickerDialog(this, this, mPaint.getColor()).show();

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
    }

    
	public int getInitColor(){
		return Color.BLUE;
	}
  
}