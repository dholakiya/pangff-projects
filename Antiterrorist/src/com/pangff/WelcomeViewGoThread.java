package com.pangff;

public class WelcomeViewGoThread  extends Thread{
	boolean flag = true;//循环标记位
	int state = 0 ;
	int sleepSpan = 300;//睡眠的毫秒数
	AntiterroristActivity antiterroristActivity;
	public WelcomeViewGoThread(AntiterroristActivity antiterroristActivity){
		this.antiterroristActivity = antiterroristActivity;
	}
	
	public void run(){
		while(flag){
			
			if(antiterroristActivity.welcomeView != null){
				switch(state){
					case 0:
						if(antiterroristActivity.welcomeView.flyX >= 1280){//当移动过远时将坐标重置到0
							antiterroristActivity.welcomeView.flyX = -600;
						}
						if(antiterroristActivity.welcomeView.flyX >= 0){//当移动过远时将坐标重置到0
							antiterroristActivity.welcomeView.dd1X = 530;
							state = 1;
						}
						antiterroristActivity.welcomeView.flyX += 16;//每次循环向右移两个单位
						break;
					case 1:
						if(antiterroristActivity.welcomeView.dd1X >= 650){//当移动过远时将坐标重置到0
							antiterroristActivity.welcomeView.bbX = antiterroristActivity.welcomeView.dd1X;
							antiterroristActivity.welcomeView.bbY = antiterroristActivity.welcomeView.dd1Y+antiterroristActivity.welcomeView.dd1.getHeight()-antiterroristActivity.welcomeView.bb.getHeight();
							antiterroristActivity.welcomeView.dd1X = -1000;
							flag  = false;
							antiterroristActivity.myHandler.sendEmptyMessage(1);//向主activity发送Handler消息
						}else{
							antiterroristActivity.welcomeView.dd1X += 18;//每次循环向右移两个单位
							antiterroristActivity.welcomeView.dd1Y += 12;
							antiterroristActivity.welcomeView.flyX += 16;//每次循环向右移两个单位
						}
						break;
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
