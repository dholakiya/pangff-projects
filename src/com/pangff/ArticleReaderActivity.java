package com.pangff;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;

public class ArticleReaderActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if(this.getRequestedOrientation()==Configuration.ORIENTATION_LANDSCAPE){
			this.finish();
			return;
		}
		/**
		 * 该activity没有自己的xml布局，是将ArticleReaderFragment的实例加入
		 */
		if(savedInstanceState==null){
			ArticleReaderFragment reader = new ArticleReaderFragment();
			reader.setArguments(this.getIntent().getExtras());
			this.getFragmentManager().beginTransaction().add(android.R.id.content, reader).commit();
		}
	}
		
	
	

}
