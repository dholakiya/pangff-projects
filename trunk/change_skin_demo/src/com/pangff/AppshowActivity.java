package com.pangff;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
/**
 * ==================================================
 *@作者 庞飞飞
 *@日期 2011-10-31
 *@说明 AppShow更换皮肤的demo 主activity页面
 *===================================================
 */
public class AppshowActivity extends Activity {
	
	private LinearLayout  mainLayout ;  //最外层主布局
	private LinearLayout titleBar ; 	//标题栏
	
	private Button rangButton ;     	//排名按钮
	private Button activityButton ;     //动态按钮
	private Button shareButton ;        //分享按钮
	private Button circleButton ;       //圈子按钮
	
	private ImageView image;			//相片
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
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
    	 * 初始化元素
    	 */
    	this.mainLayout =  (LinearLayout) this.findViewById(R.id.mainLayout);
    	this.titleBar =  (LinearLayout) this.findViewById(R.id.titleBar);
    	this.rangButton = (Button) this.findViewById(R.id.rangButton);
    	this.activityButton = (Button) this.findViewById(R.id.activityButton);
    	this.shareButton = (Button) this.findViewById(R.id.shareButton);
    	this.circleButton = (Button) this.findViewById(R.id.circleButton);
    	this.image = (ImageView) this.findViewById(R.id.image);
    	
    	/**
    	 * 根据登录标识确定皮肤
    	 */
    	switch(login_flag){
    		case 0://女性
    			this.mainLayout.setBackgroundColor(this.getResources().getColor(R.drawable.skin_pink));
    			this.titleBar.setBackgroundColor(this.getResources().getColor(R.drawable.skin_pink_bar));
    			this.rangButton.setBackgroundColor(this.getResources().getColor(R.drawable.skin_pink_bar));
    			this.activityButton.setBackgroundColor(this.getResources().getColor(R.drawable.skin_pink_bar));
    			this.shareButton.setBackgroundColor(this.getResources().getColor(R.drawable.skin_pink_bar));
    			this.circleButton.setBackgroundColor(this.getResources().getColor(R.drawable.skin_pink_bar));
    			this.image.setImageDrawable(this.getResources().getDrawable(R.drawable.female));
    			break;
    		case 1://男性
    			this.mainLayout.setBackgroundColor(this.getResources().getColor(R.drawable.skin_blue));
    			this.titleBar.setBackgroundColor(this.getResources().getColor(R.drawable.skin_blue_bar));
    			this.rangButton.setBackgroundColor(this.getResources().getColor(R.drawable.skin_blue_bar));
    			this.activityButton.setBackgroundColor(this.getResources().getColor(R.drawable.skin_blue_bar));
    			this.shareButton.setBackgroundColor(this.getResources().getColor(R.drawable.skin_blue_bar));
    			this.circleButton.setBackgroundColor(this.getResources().getColor(R.drawable.skin_blue_bar));
    			this.image.setImageDrawable(this.getResources().getDrawable(R.drawable.male));
    			break;
    		default :{
    			
    		}
    	}
    	
    }
}