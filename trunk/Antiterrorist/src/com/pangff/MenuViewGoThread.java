package com.pangff;

public class MenuViewGoThread extends Thread{
	boolean flag = true;//循环标记位
	int sleepSpan = 300;//睡眠的毫秒数
	AntiterroristActivity antiterroristActivity;
	public MenuViewGoThread(AntiterroristActivity antiterroristActivity){
		this.antiterroristActivity = antiterroristActivity;
	}
	public void run(){
		while(flag){
			if(antiterroristActivity.menuView != null){
				if(antiterroristActivity.menuView.menubackgroudX < -320){//当移动过远时将坐标重置到0
					antiterroristActivity.menuView.menubackgroudX = 0;
				}
				antiterroristActivity.menuView.menubackgroudX -= 2;//每次循环向左移两个单位
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