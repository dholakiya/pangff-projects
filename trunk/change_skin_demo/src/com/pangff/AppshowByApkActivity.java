package com.pangff;


import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
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
public class AppshowByApkActivity extends Activity {
	
	private Context skinContext; //皮肤context
	
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
        this.setContentView(R.layout.main);
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
    	
    	
    	ArrayList<PackageInfo> skinList = getSkin(login_flag);
    	try {
			skinContext = createPackageContext(skinList.get(0).packageName,Context.CONTEXT_IGNORE_SECURITY);
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			skinContext = this;
		}
		if(skinContext != null){
			this.mainLayout.setBackgroundColor(skinContext.getResources().getColor(R.drawable.skin));
			this.titleBar.setBackgroundColor(skinContext.getResources().getColor(R.drawable.skin_bar));
			this.rangButton.setBackgroundColor(skinContext.getResources().getColor(R.drawable.skin_bar));
			this.activityButton.setBackgroundColor(skinContext.getResources().getColor(R.drawable.skin_bar));
			this.shareButton.setBackgroundColor(skinContext.getResources().getColor(R.drawable.skin_bar));
			this.circleButton.setBackgroundColor(skinContext.getResources().getColor(R.drawable.skin_bar));
			this.image.setImageDrawable(skinContext.getResources().getDrawable(R.drawable.image));

		}
    }
    
    /**
     * 查询皮肤包
     * @param login_flag
     * @return
     */
    private ArrayList<PackageInfo> getSkin(int login_flag) {
		ArrayList<PackageInfo> skinList = new ArrayList<PackageInfo>();
		List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
		for (PackageInfo p : packs) {
			if (isSkinPackage(p.packageName,login_flag)) {
				skinList.add(p);
			}
		}
		return skinList;
	}

    /**
     * 过滤皮肤包
     * @param packageName
     * @param login_flag
     * @return
     */
	private boolean isSkinPackage(String packageName,int login_flag) {
		String rex = "com.appshow.skin.pink";
		switch(login_flag){
			case 0://女性
				rex="com.appshow.skin.pink";
				break;
			case 1://男性
				rex="com.appshow.skin.blue";
				break;
		}
		Pattern pattern = Pattern.compile(rex);
		Matcher matcher = pattern.matcher(packageName);
		return matcher.find();
	}
}