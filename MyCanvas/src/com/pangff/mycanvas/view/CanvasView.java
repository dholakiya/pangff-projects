package com.pangff.mycanvas.view;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;

import com.pangff.mycanvas.constants.PenSizeConstants;
import com.pangff.mycanvas.constants.ToolsTypeConstants;
import com.pangff.mycanvas.interfaces.ISketchPadTool;
import com.pangff.mycanvas.utils.ToolkitFactory;

public class CanvasView extends View implements  ColorPickerFragment.OnColorChangedListener, OnClickListener{
	
	float currentPathX;
	float currentPathY;
	Paint paint;
	Path path;
	Canvas mycanvas;
	private Bitmap  mBitmap;
	private Paint mBitmapPaint;
	
	
	private ISketchPadTool m_curTool;
	private int m_penSize = PenSizeConstants.MIDDLE_PEN_WIDTH;
    private int m_bkColor = Color.WHITE;
    private int m_strokeType = ToolsTypeConstants.PEN;
    private int m_strokeColor = Color.BLACK;
    private boolean m_isTouchUp;
	private Dialog dialog;
	public CanvasView(Context context) {
		super(context);
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	}
	
	  @Override
      protected void onSizeChanged(int w, int h, int oldw, int oldh) {
          super.onSizeChanged(w, h, oldw, oldh);
          mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
          mycanvas = new Canvas(mBitmap);
      }
	

	public CanvasView(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);
		m_curTool = ToolkitFactory.getTools(m_strokeType,m_penSize,m_strokeColor);
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	}



	@Override
	protected void onDraw(Canvas canvas) {
		  canvas.drawColor(m_bkColor);
		  canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
          if (null != m_curTool){
             if (ToolsTypeConstants.ERASER != m_strokeType){
        		 if (!m_isTouchUp){
            		 m_curTool.draw(canvas);
            	 }
             }
          }

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		float x = event.getX();
		float y = event.getY();
		 m_isTouchUp = false;
		switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:{
				m_curTool.touchDown(x, y);
				invalidate();
				break;
			}
			case MotionEvent.ACTION_MOVE:{
				m_curTool.touchMove(x, y);
				if (ToolsTypeConstants.ERASER == m_strokeType){
					m_curTool.draw(mycanvas);
				}
				invalidate();
				break;
			}
			case MotionEvent.ACTION_UP:{
				m_isTouchUp = true;
				m_curTool.touchUp(x, y);
				m_curTool.draw(mycanvas);
				invalidate();
				break;
			}
		}
		// TODO Auto-generated method stub
		 return true;
	}


	@Override
	public void colorChanged(int color) {
		// TODO Auto-generated method stub
		m_strokeColor = color;
		m_curTool.changeColor(m_strokeColor);
		//= ToolkitFactory.getTools(m_strokeType,m_penSize,m_strokeColor);
	}
	
	

	@Override
	public void onClick(View v) {
		m_strokeType = v.getId();
		Log.v("curve",(m_strokeType==ToolsTypeConstants.CX1)+"");
		if(m_strokeType==ToolsTypeConstants.CX){
			dialog = new MenuDialog(v.getContext(),this);
			dialog.show();
		}
		if(m_strokeType!=ToolsTypeConstants.CX){
			m_curTool = ToolkitFactory.getTools(m_strokeType,m_penSize,m_strokeColor);
		}
		
	}
	/**
	 * 设置画笔粗细
	 * @param size
	 */
	public void setStrokeWidth(int size){
		m_penSize = size;
		m_curTool.setStrokeWidth(m_penSize);
	}

}
