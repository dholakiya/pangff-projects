package com.pangff;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;




public class GameView extends SurfaceView implements Callback {
	
	Ball ball;
	
	GameThread gt;
	
	DrawThread dt;
	
	Paint paint;
	public GameView(BlanceBallActivity father) {
		super(father);
		paint = new Paint();
		ball = new Ball(father);
		dt = new DrawThread(getHolder(),this);
		gt = new GameThread(this);
		if(!dt.isAlive()){				
			dt.start();
		}		
		if(!gt.isAlive()){				//启动GameThread
			gt.start();
		}		
	}
	
	//屏幕绘制方法
	public void doDraw(Canvas canvas){
		canvas.drawColor(Color.BLUE);
		ball.drawSelf(canvas);
	}
	
	@Override
	public void surfaceCreated(SurfaceHolder holder) {//重写surfaceCreated方法

	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
	}
}
