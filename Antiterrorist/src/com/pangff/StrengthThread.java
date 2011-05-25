package com.pangff;

public class StrengthThread  extends Thread{

	double current;	//记录当前时间
	boolean flag = true;//循环标记位
	int state = 0 ;
	int sleepSpan = 300;//睡眠的毫秒数
	GameView gameView;
	public StrengthThread(GameView gameView){
		this.gameView = gameView;
		gameView.ddTimeX = System.nanoTime();
		gameView.ddTimeY = System.nanoTime();
	}
	
	public void run(){
		while(flag){
			if(gameView != null){
				gameView.ddstrengthSX += 10;
				gameView.moveLength += 10;
				gameView.currentStrength = gameView.totalStrength*(gameView.moveLength/(1090-449));
				try{
					Thread.sleep(sleepSpan);//睡眠
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
		}
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}	
	
	

}
