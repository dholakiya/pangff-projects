package com.pangff;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class BlanceBallActivity extends Activity {
    /** Called when the activity is first created. */
	SensorManager mySensorManager; 
	GameView gv;
	BallListener bl;
	Sensor sensor; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	   super.onCreate(savedInstanceState);
    	  // requestWindowFeature(Window.FEATURE_NO_TITLE);
           getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
           gv = new GameView(this);
           setContentView(gv);
           bl = new BallListener(this); //创建SensorManager
           mySensorManager = (SensorManager) this.getSystemService(SENSOR_SERVICE);
           sensor = mySensorManager.getDefaultSensor(SensorManager.SENSOR_ORIENTATION);  
           mySensorManager.registerListener( bl,sensor,SensorManager.SENSOR_DELAY_GAME);   
    }
}