package com.pangff;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class WelcomeView extends SurfaceView implements SurfaceHolder.Callback{
	AntiterroristActivity antiterroristActivity;
	WelcomeViewDrawThread welcomeViewDrawThread = null;
	
	Bitmap background_sky;//欢迎页面背景
	Bitmap background_yun1;//欢迎页面背景
	Bitmap background_yun2;//欢迎页面背景
	Bitmap fly;//欢迎页面背景
	Bitmap dd1;//欢迎页面背景
	Bitmap dd2;//欢迎页面背景
	Bitmap dd3;//欢迎页面背景
	Bitmap dd4;//欢迎页面背景
	Bitmap bb;//欢迎页面背景
	float background_yun_X1 = 0;//需要移动的背景的坐标
	float background_yun_X2 = 400;//需要移动的背景的坐标
	float background_yun_Y1 = 20;//需要移动的背景的坐标
	float background_yun_Y2 = 20;//需要移动的背景的坐标
	float flyX = -600;//需要移动的背景的坐标
	float flyY = 250;//需要移动的背景的坐标
	float dd1X = -1000;//没-4,25
	float dd1Y = 430;//没-2,25
	float bbX = -1000;//700;
	float bbY = -1000;//300;
	Paint paint = null;
	
	public WelcomeView(AntiterroristActivity antiterroristActivity) {
		super(antiterroristActivity);
		// TODO Auto-generated constructor stub
		paint = new Paint();
		this.antiterroristActivity = antiterroristActivity;
		getHolder().addCallback(this);
		welcomeViewDrawThread = new WelcomeViewDrawThread(this,this.getHolder());
		background_sky = BitmapFactory.decodeResource(getResources(), R.drawable.wel_back2);
		//background_yun1 = BitmapFactory.decodeResource(getResources(), R.drawable.yun3);
		//background_yun2 = BitmapFactory.decodeResource(getResources(), R.drawable.yun4);
		fly = BitmapFactory.decodeResource(getResources(), R.drawable.fly1);
		dd1 =  BitmapFactory.decodeResource(getResources(), R.drawable.dd3);
		bb =  BitmapFactory.decodeResource(getResources(), R.drawable.bb);
	}
	
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(Color.BLUE);//背景白色
		canvas.drawBitmap(background_sky, 0, 0, paint);//绘制背景
		//canvas.drawBitmap(background_yun1, background_yun_X1, background_yun_Y1,paint);//绘制云
		//canvas.drawBitmap(background_yun2, background_yun_X2, background_yun_Y2,paint);//绘制云
		canvas.drawBitmap(bb, bbX, bbY,paint);//爆炸
		canvas.drawBitmap(dd1, dd1X, dd1Y,paint);//导弹
		canvas.drawBitmap(fly, flyX, flyY,paint);//飞机
	}	

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		welcomeViewDrawThread.setFlag(true);
		welcomeViewDrawThread.start();//启动刷帧线程
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		  boolean retry = true;
	        welcomeViewDrawThread.setFlag(false);//停止刷帧线程
	        while (retry) {
	            try {
	            	welcomeViewDrawThread.join();//等待刷帧线程结束
	                retry = false;
	            } 
	            catch (InterruptedException e) {//不断地循环，直到等待的线程结束
	            }
	        }
	}

}
