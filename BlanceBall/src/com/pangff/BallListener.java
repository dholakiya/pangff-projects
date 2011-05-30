package com.pangff;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorListener;
import android.hardware.SensorManager;

public class BallListener implements SensorEventListener{
	
	BlanceBallActivity father;		
	int timeSpan = 500;		
	long startTime;	
	
	public BallListener(BlanceBallActivity father){
		this.father = father;
		startTime = System.currentTimeMillis();	
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		long now = System.currentTimeMillis();		
		if(now - startTime >= timeSpan){			
			if(event.sensor.getType() == SensorManager.SENSOR_ORIENTATION){
				analysisData(event.values);		
			}	
			startTime = now;				
		}
	}	
	
	public void analysisData(float[] values) {		
		double[] valuesTemp=new double[]{values[0],-values[1],values[2]};	
		father.gv.ball.direction = RotateUtil.getDirectionCase(valuesTemp);	
	}
}
