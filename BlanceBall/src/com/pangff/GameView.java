package com.pangff;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	
	double ballTime = 0 ;
	
	/**
	 * 地图测试部分
	 */
	int initX = 0;
	int initY = 0;
	Bitmap p56;
	Bitmap p12;
	Bitmap p26;
	Bitmap p58;
	Bitmap p25;
	Bitmap p14;
	Bitmap p57;
	Bitmap p4;
	Bitmap p59;
	int partWidth;
	int partHeight;
	
	public GameView(BlanceBallActivity father) {
		super(father);
		Resources r = father.getResources();
		initMap(r);
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
	
	private void initMap(Resources r){
		p56 =  BitmapFactory.decodeResource(r, R.drawable.p56);
		p12 =  BitmapFactory.decodeResource(r, R.drawable.p12);
		p26 =  BitmapFactory.decodeResource(r, R.drawable.p26);
		p58 =  BitmapFactory.decodeResource(r, R.drawable.p58);
		p25 =  BitmapFactory.decodeResource(r, R.drawable.p25);
		p14 =  BitmapFactory.decodeResource(r, R.drawable.p14);
		p57 =  BitmapFactory.decodeResource(r, R.drawable.p57);
		p4 =  BitmapFactory.decodeResource(r, R.drawable.p4);	
		p59 =  BitmapFactory.decodeResource(r, R.drawable.p59);
		partWidth = p56.getWidth()-10;
		partHeight = p56.getHeight()-7;
	}
	
	//屏幕绘制方法
	public void doDraw(Canvas canvas){
		canvas.drawColor(Color.BLUE);
		for(int i=0;i<MapList.map.length;i++){
			for(int j=0;j<MapList.map[i].length;j++){
				int X = initX+partWidth*j;
                int Y = initY+partHeight*i;	
				switch(MapList.map[i][j]){
				case 56:
					canvas.drawBitmap(p56, X, Y, paint);
					break;
				case 12:
					canvas.drawBitmap(p12, X, Y, paint);
					break;
				case 26:
					canvas.drawBitmap(p26, X, Y, paint);
					break;
				case 58:
					canvas.drawBitmap(p58, X, Y, paint);
					break;
				case 25:
					canvas.drawBitmap(p25, X, Y, paint);
					break;
				case 14:
					canvas.drawBitmap(p14, X, Y, paint);
					break;
				case 57:
					canvas.drawBitmap(p57, X, Y, paint);
					break;
				case 4:
					canvas.drawBitmap(p4, X, Y, paint);
					break;
				case 59:
					canvas.drawBitmap(p59, X, Y, paint);
					break;
				}
				
			}
		}
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
