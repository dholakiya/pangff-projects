package com.pangff.mycanvas.tools;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.pangff.mycanvas.interfaces.ISketchPadTool;

public class Rect implements ISketchPadTool{

	private float startX = 0.0f;
    private float startY = 0.0f;
	private float endX = 0.0f;
    private float endY = 0.0f;
    private boolean m_hasDrawn = false;
    private Paint m_penPaint = new Paint();
    
    public Rect(int penSize, int penColor){
        m_penPaint.setAntiAlias(true);
        m_penPaint.setDither(true);
        m_penPaint.setColor(penColor);
        m_penPaint.setStrokeWidth(penSize);
        m_penPaint.setStyle(Paint.Style.STROKE);
        m_penPaint.setStrokeJoin(Paint.Join.ROUND);
        m_penPaint.setStrokeCap(Paint.Cap.ROUND);
    }
	@Override
	public void cleanAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (null != canvas){
            canvas.drawRect(startX, startY, endX, endY, m_penPaint);
        }
	}

	@Override
	public boolean hasDraw() {
		// TODO Auto-generated method stub
		return m_hasDrawn;
	}

	@Override
	public void touchDown(float x, float y) {
		// TODO Auto-generated method stub
		startX = x;
		startY = y;
	}

	@Override
	public void touchMove(float x, float y) {
		// TODO Auto-generated method stub
		endX = x;
		endY = y;
	}

	@Override
	public void touchUp(float x, float y) {
		// TODO Auto-generated method stub
		endX = x;
		endY = y;
	}
	@Override
	public void setStrokeWidth(int size) {
		// TODO Auto-generated method stub
		m_penPaint.setStrokeWidth(size);
	}
	@Override
	public void changeColor(int color) {
		// TODO Auto-generated method stub
		m_penPaint.setColor(color);
	}

}
