package com.pangff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * ==================================================
 *@作者 庞飞飞
 *@日期 2011-10-31
 *@说明 AppShow更换皮肤的demo 登录页面
 *===================================================
 */
public class LoginActivity extends Activity{
	
	private int login_flag = 0 ;		//登录标识 0女性 1男性
	
	 /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
  
    }
    
    /**
     * 女性登陆按钮点击事件
     */
    public void femaleLogin(View view){
    	login_flag = 0;
    	login();
    }
    
    /**
     * 男性登陆按钮点击事件
     */
    public void maleLogin(View view){
    	login_flag = 1;
    	login();
    }
    
    /**
     * 登陆方法
     */
    private void login(){
    	 Intent intent = new Intent();  
         intent.putExtra("login_flag", login_flag);  
         intent.setClass(LoginActivity.this, AppshowActivity.class);  
         startActivity(intent);  
    }
}
