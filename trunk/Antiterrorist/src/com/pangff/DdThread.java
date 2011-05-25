package com.pangff;

public class DdThread  extends Thread{

	double current;	//记录当前时间
	double boomTime;	//爆炸时间
	boolean flag = true;//循环标记位
	int state = 0 ;
	int sleepSpan = 300;//睡眠的毫秒数
	boolean unBoomFlag = true;
	GameView gameView;
	
	public DdThread(GameView gameView){
		this.gameView = gameView;
		gameView.ddTimeX = System.nanoTime();
		gameView.ddTimeY = System.nanoTime();
	}
	
	public void run(){
		while(flag){
			if(gameView != null){
					/**
					 * 水平方向
					 */
					current = System.nanoTime();//获取当前时间，单位为纳秒
					double timeSpanX = (double)((current-gameView.ddTimeX)/1000/1000/1000);//获取从玩家开始到现在水平方向走过的时间
					gameView.ddx = (float)(gameView.initddX + gameView.ddXv*timeSpanX+0.5*gameView.ddXa*timeSpanX*timeSpanX);
					
					/**
					 * 竖直方向
					 */
					current = System.nanoTime();//获取当前时间，单位为纳秒
					double timeSpanY = (double)((current-gameView.ddTimeY)/1000/1000/1000);//获取从玩家开始到现在竖直方向走过的时间
					gameView.ddy = (float)(gameView.initddY + gameView.ddYv*timeSpanY+0.5*gameView.g*timeSpanY*timeSpanY);
	
					/**
					 * 检测是否落地
					 */
					if(gameView.ddy>=450+gameView.building.getHeight()){
						gameView.begin = false;
						if(unBoomFlag){
							boomTime = System.nanoTime();//获取当前时间，单位为纳秒
							gameView.boomX = gameView.ddx+gameView.dd.getWidth()-gameView.boom.getWidth();
							gameView.boomY = 450+gameView.building.getHeight()-gameView.boom.getHeight();
							unBoomFlag = false;
						}
						current = System.nanoTime();//获取当前时间，单位为纳秒
						double t = (current-boomTime)/1000/1000/1000;
						if(t>=1){
							gameView.boomX = -500;
							gameView.boomY = -500;
							flag = false;
							this.stop();
						}
					}
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
