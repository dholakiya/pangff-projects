package com.pangff;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class DrawThread extends Thread{
	SurfaceHolder surfaceHolder;			//SurfaceHolder����
	GameView gv;
	boolean flag;							
	boolean isGameOn;						
	int sleepSpan=25;						
	public DrawThread(SurfaceHolder holder, GameView gameView) {
		// TODO Auto-generated constructor stub
		this.surfaceHolder = holder;
		this.gv = gameView;
		this.flag = true;
	}
	public void run(){
		
		while(flag){
			Canvas c;
				c = null;
				try {
					// 锁定整个画布，在内存要求比较高的情况下，建议参数不要为null
				    c = surfaceHolder.lockCanvas(null);
				    synchronized (this.surfaceHolder) {
				    		gv.doDraw(c);
				    }
				}catch(Exception e){
					
				} finally {
				    if (c != null) {
				    	//更新屏幕显示内容
				        surfaceHolder.unlockCanvasAndPost(c);
				    }
				}
				try{
					Thread.sleep(sleepSpan);//睡眠sleepSpan毫秒
				}
				catch(Exception e){
					e.printStackTrace();
				}
			}
	}
}
