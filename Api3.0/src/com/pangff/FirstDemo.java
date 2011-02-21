package com.pangff;

import java.util.ArrayList;
import java.util.HashMap;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class FirstDemo extends Activity {
	Button searchButton = null;
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
    
    private void startSearchFileDialog(){
    	Dialog searchFileDialog = new SearchFileDialog(FirstDemo.this);
    	
    	searchFileDialog.show();
    	
    	
  	 
  	
    }
}