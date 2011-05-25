package com.pangff;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GameView extends SurfaceView implements SurfaceHolder.Callback{
	AntiterroristActivity antiterroristActivity;
	GameViewDrawThread gameViewDrawThread;
	Paint paint;
	
	WeatherThread weatherThread;
	Bitmap yun;//云
	float yunX = 600 ;
	float yunY = 30;
	double windSpeed = 2;
	double windDeriction = 1;
	
	DdThread ddThread ;
	Bitmap dd;//导弹
	float initddX = 123;//绘制时第一个的坐标,即开始绘制的位置
	float initddY = 145;
	float ddx = 123;
	float ddy = 145;
	double ddTimeX = 0 ;
	double ddTimeY = 0 ;
	double ddXv = 0;//水平初速度
	double g = 8;//重力加速度
	double ddXa = 1;//水平加速度
	double ddYv = 0;//竖直初速度
	
	Bitmap dd1;//导弹
	float dd1x = 383;
	float dd1y = 605;
	
	Bitmap dd2;//导弹
	float dd2x = 452;
	float dd2y = 605;
	
	Bitmap dd3;//导弹
	float dd3x = 522;
	float dd3y = 605;
	
	Bitmap dd4;//导弹
	float dd4x = 592;
	float dd4y = 605;
	float ddNum = 4;
	
	
	StrengthThread strengthThread;
	//Bitmap strengtha;//劲力条
	//Bitmap strengthb;//劲力条背景
	float ddstrengthSX = 449;
	float ddstrengthSY = 689;//689;
	float ddstrengthEX = 449;//1090
	float ddstrengthEY = 708;//708;
	
	
	float boomX = -500;
	float boomY = -500;
	
	float totalStrength = 1000;//总劲力
	float moveLength = 0;//移动距离
	float currentStrength = 0;//移动距离
	double initAngle = Math.PI/6;//初始角度
	
	double plainAngle = Math.PI/36;
	double angleCirclX = 148;
	double angleCirclY= 655;
	double angleR = 73;
	double angler = 15.56; 
	Bitmap exit2;//退出按钮图片
	Bitmap plain;//飞机
	Bitmap gameback;//飞机
	Bitmap ctrl;
	Bitmap winde;
	Bitmap windw;
	
	Bitmap building;//建筑物
	Bitmap goon;//恭喜过关的图片
	Bitmap boom;
	
	boolean begin = false;
	int status = 0 ;//0正常游戏中,1胜利
	
	public GameView(AntiterroristActivity antiterroristActivity) {//构造器
		super(antiterroristActivity);
		this.antiterroristActivity = antiterroristActivity;
		gameViewDrawThread = new GameViewDrawThread(this,getHolder());
		getHolder().addCallback(this);
		initBitmap();
		weatherThread = new WeatherThread(this);
		weatherThread.start();
	}
	public void initBitmap(){ 
		plain = BitmapFactory.decodeResource(getResources(), R.drawable.plain);
		building =  BitmapFactory.decodeResource(getResources(),R.drawable.building);
		dd = BitmapFactory.decodeResource(getResources(),R.drawable.dd4);
		yun = BitmapFactory.decodeResource(getResources(),R.drawable.clound);
		winde = BitmapFactory.decodeResource(getResources(),R.drawable.winde);
		windw = BitmapFactory.decodeResource(getResources(),R.drawable.windw);
		ctrl = BitmapFactory.decodeResource(getResources(),R.drawable.contrl);
		boom = BitmapFactory.decodeResource(getResources(),R.drawable.boom);
		dd1 = BitmapFactory.decodeResource(getResources(),R.drawable.dd4);
		dd2 = BitmapFactory.decodeResource(getResources(),R.drawable.dd4);
		dd3 = BitmapFactory.decodeResource(getResources(),R.drawable.dd4);
		dd4 = BitmapFactory.decodeResource(getResources(),R.drawable.dd4);
		paint = new Paint();
	}
	protected void onDraw(Canvas canvas) {
		if(status == 0){//正常游戏中
			paint.setAntiAlias(true);//抗锯齿
			canvas.drawColor(Color.BLUE);//绘制黑背景
			//paint.setColor(Color.BLUE);
			//canvas.drawRect(0, 0, 1280, 600, paint);
			
			canvas.drawBitmap(yun, yunX,yunY, paint);//云
			if(begin){
				canvas.drawBitmap(dd, ddx, ddy, paint);
			}
			canvas.drawBitmap(plain, 10, 100, paint);//飞机
			canvas.drawRect(0, 540, 1280, 450+building.getHeight(), paint);
			canvas.drawBitmap(building, 1000, 450, paint);//建筑
			canvas.drawBitmap(ctrl, 0,760-ctrl.getHeight(), paint);//云
			canvas.drawRect(ddstrengthSX, ddstrengthSY, ddstrengthEX, ddstrengthEY, paint);
			canvas.drawBitmap(boom, boomX,boomY, paint);//云
			
			
			paint.setColor(Color.YELLOW);
			if(windDeriction>0){
				canvas.drawBitmap(windw, 1177,656, paint);//
			}else{
				canvas.drawBitmap(winde, 1177,656, paint);//
			}
			float sx = (float) (angleCirclX+angler*Math.cos(plainAngle));
			float sy = (float) (angleCirclY-angler*Math.sin(plainAngle));
			
			float ex = (float) (angleCirclX+angleR*Math.cos(plainAngle));
			float ey = (float) (angleCirclY-angleR*Math.sin(plainAngle));
			
			canvas.drawLine(sx, sy, ex, ey, paint);
			
			if(ddNum==4){
				canvas.drawBitmap(dd1, dd1x, dd1y, paint);
				canvas.drawBitmap(dd2, dd2x, dd2y, paint);
				canvas.drawBitmap(dd3, dd3x, dd3y, paint);
				canvas.drawBitmap(dd4, dd4x, dd4y, paint);
			}else if(ddNum==3){
				canvas.drawBitmap(dd1, dd1x, dd1y, paint);
				canvas.drawBitmap(dd2, dd2x, dd2y, paint);
				canvas.drawBitmap(dd3, dd3x, dd3y, paint);
			}else if(ddNum==2){
				canvas.drawBitmap(dd1, dd1x, dd1y, paint);
				canvas.drawBitmap(dd2, dd2x, dd2y, paint);
			}else if(ddNum==1){
				canvas.drawBitmap(dd1, dd1x, dd1y, paint);
			}

		}
		else if(status == 1){//恭喜过关
			paint.setAntiAlias(true);//抗锯齿
			canvas.drawColor(Color.WHITE);//绘制黑背景
			canvas.drawBitmap(goon, -3, 70, new Paint());//绘制恭喜图片
		}
	}	
	public void myDrawRect(Canvas canvas, int x ,int y){//绘制多边形
    	Path path = new Path();
    	path.moveTo(x+14, y);
    	path.lineTo(x+53, y+10);
    	path.lineTo(x+37, y+37);
    	path.lineTo(x-2, y+26);
    	path.lineTo(x+14, y);
    	canvas.drawPath(path, paint);
	}
	public boolean onTouchEvent(MotionEvent event) {
		Log.v("XXX", event.getX()+"");
		Log.v("XXX", event.getY()+"");
		//ddThread = new DdThread(this);
		//ddThread.start();
		
		if(status == 0){
				switch(event.getAction()){
					case MotionEvent.ACTION_DOWN:
						if(event.getX()>449 && event.getX()<1090&&event.getY()>689&&event.getY()<708){
							if(strengthThread==null||!strengthThread.isAlive()){
								strengthThread  = new StrengthThread(this);
								initPlayInfo();
								strengthThread.start();
							}else{
								strengthThread.setFlag(false);
								strengthThread.stop();
								ddXv = currentStrength*Math.cos(initAngle);
								ddYv = currentStrength*Math.sin(initAngle);
								Log.v("ddXv", ddXv+"");
								ddThread = new DdThread(this);
								ddThread.start();
							}
						}
						break;
				}			
		}
//		else if(status == 1){
//			antiterroristActivity.myHandler.sendEmptyMessage(4);//向Activity发生消息
//		}

		return super.onTouchEvent(event);
	}	
	/**
	 * 初始导弹、劲力
	 */
	public void initPlayInfo(){
		ddNum --;
		begin = true;
		moveLength = 0;
		ddstrengthSX = 449;
		ddstrengthSY = 689;//689;
		ddstrengthEX = 449;//1090
		ddstrengthEY = 708;//708;
		ddx = 123;
		ddy = 145;
	}
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {		
	}
	public void surfaceCreated(SurfaceHolder holder) {
		gameViewDrawThread.setFlag(true);
		gameViewDrawThread.start();
	}
	public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;
        gameViewDrawThread.setFlag(false);
        while (retry) {
            try {
            	gameViewDrawThread.join();
                retry = false;
            } 
            catch (InterruptedException e) {//不断地循环，直到刷帧线程结束
            }
        }		
	}
}