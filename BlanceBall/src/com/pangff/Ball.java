package com.pangff;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Ball {
	int direction = -1;	//小球方向
	Bitmap bmpBall; //小球贴图	
	int ballX;
	int ballY;
	int velocity = 4;			//小球运动速度
	public Ball(BlanceBallActivity father){
		Resources r = father.getResources();
		this.bmpBall = BitmapFactory.decodeResource(r, R.drawable.ball);
		this.ballX = 0;
		this.ballY = 0;
	}
	
	public void drawSelf(Canvas canvas){
		canvas.drawBitmap(bmpBall, ballX,ballY, null);
	}
	
}
