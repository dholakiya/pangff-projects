package com.pangff.mycanvas.tools;

import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;

import com.pangff.mycanvas.constants.ToolsTypeConstants;
import com.pangff.mycanvas.interfaces.ISketchPadTool;

public class Brush implements ISketchPadTool{
	
	private static final float TOUCH_TOLERANCE =4.0f;
	private float m_curX = 0.0f;
    private float m_curY = 0.0f;
    private boolean m_hasDrawn = false;
    private Path m_penPath = new Path();
    private Paint m_penPaint = new Paint();
    MaskFilter mBlur = new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL);
    MaskFilter mEmboss = new EmbossMaskFilter(new float[] { 1, 1, 1 },
            0.4f, 6, 3.5f);
	public Brush(int penSize, int penColor ,int burshType){
        m_penPaint.setAntiAlias(true);
        m_penPaint.setDither(true);
        m_penPaint.setColor(penColor);
        m_penPaint.setStrokeWidth(penSize);
        m_penPaint.setStyle(Paint.Style.STROKE);
        m_penPaint.setStrokeJoin(Paint.Join.ROUND);
        m_penPaint.setStrokeCap(Paint.Cap.ROUND);
        switch(burshType){
        	case ToolsTypeConstants.BRUSH1:
        		m_penPaint.setMaskFilter(mBlur);
        		break;
        	case ToolsTypeConstants.BRUSH2:
        		m_penPaint.setMaskFilter(mEmboss);
        		break;
        }
        
	}

	@Override
	public void cleanAll() {
		// TODO Auto-generated method stub
		 m_penPath.reset();
	}

	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if (null != canvas){
            canvas.drawPath(m_penPath, m_penPaint);
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
        m_penPath.reset();
        m_penPath.moveTo(x, y);
        m_curX = x;
        m_curY = y;
	}

	@Override
	public void touchMove(float x, float y) {
		// TODO Auto-generated method stub
	       float dx = Math.abs(x - m_curX);
	       float dy = Math.abs(y - m_curY);
	        
	        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE){
	            m_penPath.quadTo(m_curX, m_curY, (x + m_curX) / 2, (y + m_curY) / 2);
	            
	            m_hasDrawn = true;
	            m_curX = x;
	            m_curY = y;
	        }

	}

	@Override
	public void touchUp(float x, float y) {
		// TODO Auto-generated method stub
		m_penPath.lineTo(x, y);
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
