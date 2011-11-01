package com.pangff;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

/**
 * ==================================================
 *@作者 庞飞飞
 *@日期 2011-10-31
 *@说明 AppShow更换皮肤的demo 主activity页面
 *===================================================
 */
public class AppshowByXmlActivity extends Activity {
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //获取登陆信息
        Intent intent = getIntent(); 
        int login_flag = intent.getIntExtra("login_flag", 0);
        //初始化页面元素
        this.initPage(login_flag);
    }
    
    /**
     * 初始化页面元素
     */
    private void initPage(int login_flag){
    	/**
    	 * 根据登录标识确定皮肤
    	 */
    	switch(login_flag){
    		case 0://女性
    			 setContentView(R.layout.main_pink);
    			break;
    		case 1://男性
    			 setContentView(R.layout.main_blue);
    			break;
    	}
    	
    }
}