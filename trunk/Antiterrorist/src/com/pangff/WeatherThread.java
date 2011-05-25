package com.pangff;

import android.util.Log;

public class WeatherThread  extends Thread{

	double current;	//记录当前时间
	double initTime;	//记录当前时间
	boolean flag = true;//循环标记位
	int state = 0 ;
	int sleepSpan = 300;//睡眠的毫秒数
	GameView gameView;
	public WeatherThread(GameView gameView){
		this.gameView = gameView;
		initTime = System.nanoTime();
	}
	
	public void run(){
		while(flag){
			if(gameView != null){
				current = System.nanoTime();//获取当前时间，单位为纳秒
				double timeSpan = (double)((current-initTime)/1000/1000/1000);//获取从玩家开始到现在水平方向走过的时间
				if(timeSpan>2*60){
					//每5分钟改变一次风速
					/**
					 * 随即风的方向
					 */
					if(Math.random()>0.5d){
						gameView.windDeriction = 1;
					}else{
						gameView.windDeriction = -1;
					}
					
					/**
					 * 随机风的速度 改变云的坐标
					 */
					gameView.windSpeed = Math.random()*6*gameView.windDeriction;
					
					initTime = System.nanoTime();//改变初始时间
				}
				if(gameView.yunX+gameView.yun.getWidth()<=0 && gameView.windDeriction==-1){
					gameView.yunX = 1280;
				}
				if(gameView.yunX >= 1280 && gameView.windDeriction== 1){
					gameView.yunX = 0-gameView.yun.getWidth();
				}
				gameView.yunX += gameView.windSpeed;
				gameView.ddXa = gameView.windSpeed;
				//Log.v("ddXa",gameView.ddXa+"");
				try{
					Thread.sleep(sleepSpan);//睡眠
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}	

}
