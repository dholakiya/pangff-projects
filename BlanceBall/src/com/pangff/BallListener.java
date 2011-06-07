package com.pangff;

import android.hardware.SensorListener;
import android.hardware.SensorManager;

public class BallListener implements SensorListener{
	
	BlanceBallActivity father;		
	int timeSpan = 500;		
	long startTime;	
	
	public BallListener(BlanceBallActivity father){
		this.father = father;
		startTime = System.currentTimeMillis();	
	}
	public void analysisData(float[] values) {		
		double[] valuesTemp=new double[]{values[0],-values[1],values[2]};
		double ginfo[] = RotateUtil.getDirectionCase(valuesTemp);
		
		father.gv.ball.direction = (int) ginfo[0];
		//向上
		if(father.gv.ball.direction == 0 || father.gv.ball.direction==6 || father.gv.ball.direction==7){
			father.gv.ball.ay = -ginfo[2];
		}
		//向下
		if(father.gv.ball.direction == 3 || father.gv.ball.direction==4 || father.gv.ball.direction==5){
			father.gv.ball.ay = ginfo[2];
		}
		//向左
		if(father.gv.ball.direction == 5 || father.gv.ball.direction==6 || father.gv.ball.direction==7){
			father.gv.ball.ax = -ginfo[1];
		}
		//向右
		if(father.gv.ball.direction == 1 || father.gv.ball.direction==2 || father.gv.ball.direction==3){
			father.gv.ball.ax = ginfo[1];
		}
		
	}

	@Override
	public void onAccuracyChanged(int sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(int sensor, float[] values) {
		// TODO Auto-generated method stub
		long now = System.currentTimeMillis();		
		if(now - startTime >= timeSpan){			
			if(sensor ==SensorManager.SENSOR_ORIENTATION){
				analysisData(values);		
			}	
			startTime = now;				
		}
	}
}
