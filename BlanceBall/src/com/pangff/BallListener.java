package com.pangff;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
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
		father.gv.ball.direction = RotateUtil.getDirectionCase(valuesTemp);	
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
