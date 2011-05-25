package com.pangff;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class AntiterroristActivity extends Activity {
	WelcomeView welcomeView = null;//欢迎界面
	WelcomeViewGoThread welcomeViewGoThread = null;//欢迎界面绘图线程
	MenuViewGoThread menuViewGoThread = null;//菜单界面中的移动线程
	
	GameView gameView = null;
	
	boolean isSound = true;//是否播放声音
	MediaPlayer pushBoxSound;//推箱子声音
	MediaPlayer backSound;//背景音乐
	MediaPlayer winSound;//胜利的音乐
	MediaPlayer startSound;//开始和菜单时的音乐
	
	MenuView menuView = null;
	
	Handler myHandler = new Handler(){//用来更新UI线程中的控件
        public void handleMessage(Message msg) {
        	if(msg.what == 1){//收到WelcomeViewGoThread/Welcome发来的消息
        		if(welcomeView != null){
        			welcomeView = null;  
        		}
        		
        		initAndToMenuView();
        	}
        	else if(msg.what == 2){//收到MenuView发来的消息
        		initGameView();
        	}   
        	else if(msg.what == 3){
        	}   
        	else if(msg.what == 4){//收到GameView来的消息，进入下一关
        	}
        }
	}; 
	
	public void initGameView(){
		gameView = new GameView(this);
		this.setContentView(gameView);
	}
	
    public void initAndToMenuView(){
    	//startSound.stop();
    	//backSound = MediaPlayer.create(this, R.raw.startsound);//背景音乐
    	//backSound.setLooping(true);
    	//backSound.start();
    	menuView = new MenuView(this);
    	this.setContentView(menuView);
    	menuViewGoThread = new MenuViewGoThread(this);
    	menuViewGoThread.start();
    }
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        /**设置全屏模式**/
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN ,  
	              WindowManager.LayoutParams.FLAG_FULLSCREEN);
        
        startSound  = MediaPlayer.create(this, R.raw.startsound);//背景音乐
        startSound.setLooping(true); //设置循环
        initWelcomeView();
    }


	private void initWelcomeView() {
		// TODO Auto-generated method stub
		welcomeView = new WelcomeView(this);
		if(isSound){
			startSound.start();
		}
		this.setContentView(welcomeView);
		welcomeViewGoThread = new WelcomeViewGoThread(this);
    	welcomeViewGoThread.start();
	}
}