package com.pangff.mycanvas.tools;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;

import com.pangff.mycanvas.interfaces.ISketchPadTool;

public class Beeline implements ISketchPadTool{
	
	private float m_curX = 0.0f;
    private float m_curY = 0.0f;
    private boolean m_hasDrawn = false;
    private Path m_linePath = new Path();
    private Paint m_linePaint = new Paint();
    
	public Beeline(int lineSize,int color){
		m_linePaint.setAntiAlias(true);
		m_linePaint.setDither(true);
		m_linePaint.setColor(color);
		m_linePaint.setStrokeWidth(lineSize);
		m_linePaint.setStyle(Paint.Style.STROKE);
		m_linePaint.setStrokeJoin(Paint.Join.ROUND);
		m_linePaint.setStrokeCap(Paint.Cap.ROUND);
	}
	
	@Override
	public void cleanAll() {
		// TODO Auto-generated method stub
		m_linePath.reset();
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (null != canvas){
            canvas.drawPath(m_linePath, m_linePaint);
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
		m_linePath.reset();
		m_linePath.moveTo(x, y);
        m_curX = x;
        m_curY = y;
	}

	@Override
	public void touchMove(float x, float y) {
		// TODO Auto-generated method stub
		m_linePath.reset();
		m_linePath.moveTo(m_curX, m_curY);
		m_linePath.lineTo(x, y);		
	}

	@Override
	public void touchUp(float x, float y) {
		// TODO Auto-generated method stub
		m_linePath.lineTo(x, y);
	}

	@Override
	public void setStrokeWidth(int size) {
		// TODO Auto-generated method stub
		m_linePaint.setStrokeWidth(size);
	}

	@Override
	public void changeColor(int color) {
		// TODO Auto-generated method stub
		m_linePaint.setColor(color);
	}

}
