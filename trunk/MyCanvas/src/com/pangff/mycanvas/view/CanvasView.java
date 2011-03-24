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
import com.pangff.mycanvas.interfaces.ISketchPadCallback;
import com.pangff.mycanvas.interfaces.ISketchPadTool;
import com.pangff.mycanvas.interfaces.IUndoCommand;
import com.pangff.mycanvas.utils.BitmapUtil;
import com.pangff.mycanvas.utils.SketchPadUndoStack;
import com.pangff.mycanvas.utils.ToolkitFactory;

public class CanvasView extends View implements OnClickListener, IUndoCommand {

	float currentPathX;
	float currentPathY;
	Paint paint;
	Path path;
	private Paint mBitmapPaint;

	private ISketchPadTool m_curTool;
	private int m_penSize = PenSizeConstants.MIDDLE_PEN_WIDTH;
	private int m_bkColor = Color.WHITE;
	private int m_strokeType = ToolsTypeConstants.PEN;
	private int m_strokeColor = Color.BLACK;
	private boolean m_isTouchUp;
	private Dialog dialog;

	public Paint m_bitmapPaint = null;
	public Bitmap m_foreBitmap = null;
	public SketchPadUndoStack m_undoStack = null;
	public Bitmap m_tempForeBitmap = null;
	public Bitmap m_bkBitmap = null;
	public Canvas m_canvas = null;
	private boolean m_isSetForeBmp = false;
	private boolean m_canClear = true;
	public int m_canvasWidth = 100;
	public int m_canvasHeight = 100;
	private ISketchPadCallback m_callback = null;
	public static final int UNDO_SIZE = 20;

	public ISketchPadCallback getCallback() {
		return m_callback;
	}

	public void setCallback(ISketchPadCallback mCallback) {
		m_callback = mCallback;
	}

	public CanvasView(Context context) {
		super(context);
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
		if (!m_isSetForeBmp) {
			setCanvasSize(w, h);
		}
		m_canvasWidth = w;
		m_canvasHeight = h;
		m_isSetForeBmp = false;
	}

	protected void setCanvasSize(int width, int height) {
		if (width > 0 && height > 0) {
			if (m_canvasWidth != width || m_canvasHeight != height) {
				m_canvasWidth = width;
				m_canvasHeight = height;

				createStrokeBitmap(m_canvasWidth, m_canvasHeight);
			}
		}
	}

	public Bitmap getCanvasSnapshot() {
		setDrawingCacheEnabled(true);
		buildDrawingCache(true);
		Bitmap bmp = getDrawingCache(true);
		if (null == bmp) {
			android.util.Log.d("leehong2",
					"getCanvasSnapshot getDrawingCache == null");
		}
		return BitmapUtil.duplicateBitmap(bmp);
	}

	public CanvasView(Context context, AttributeSet attrs) {
		// TODO Auto-generated constructor stub
		super(context, attrs);

		m_canvas = new Canvas();
		m_curTool = ToolkitFactory.getTools(m_strokeType, m_penSize,
				m_strokeColor);
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
		m_undoStack = new SketchPadUndoStack(this, UNDO_SIZE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		canvas.drawColor(m_bkColor);
		canvas.drawBitmap(m_foreBitmap, 0, 0, mBitmapPaint);
		if (null != m_curTool) {
			if (ToolsTypeConstants.ERASER != m_strokeType) {
				if (!m_isTouchUp) {
					m_curTool.draw(canvas);
				}
			}
		}

	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (null != m_callback) {
			int action = event.getAction();
			if (MotionEvent.ACTION_DOWN == action) {
				m_callback.onTouchDown(this, event);
			} else if (MotionEvent.ACTION_UP == action) {
				m_callback.onTouchUp(this, event);
			}
		}
		float x = event.getX();
		float y = event.getY();
		m_isTouchUp = false;
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN: {
			m_curTool = ToolkitFactory.getTools(m_strokeType, m_penSize,m_strokeColor);
			m_curTool.touchDown(x, y);
			invalidate();
			break;
		}
		case MotionEvent.ACTION_MOVE: {
			m_curTool.touchMove(x, y);
			if (ToolsTypeConstants.ERASER == m_strokeType) {
				m_curTool.draw(m_canvas);
			}
			invalidate();
			break;
		}
		case MotionEvent.ACTION_UP: {
			m_isTouchUp = true;
			if (m_curTool.hasDraw()) {
				// Add to undo stack.
				m_undoStack.push(m_curTool);
			}
			m_curTool.touchUp(x, y);
			m_curTool.draw(m_canvas);
			invalidate();
			break;
		}
		}
		// TODO Auto-generated method stub
		return true;
	}

	public void changedPenColor(int color) {
		m_strokeColor = color;
		m_curTool.changeColor(m_strokeColor);
	}

	public void changedBackColor(int color) {
		m_bkColor = color;
		invalidate();
	}

	@Override
	public void onClick(View v) {
		m_strokeType = v.getId();
		Log.v("curve", (m_strokeType == ToolsTypeConstants.CX1) + "");
		if (m_strokeType == ToolsTypeConstants.CX) {
			dialog = new MenuDialog(v.getContext(), this);
			dialog.show();
		}
	}

	/**
	 * 设置画笔粗细
	 * 
	 * @param size
	 */
	public void setStrokeWidth(int size) {
		m_penSize = size;
		m_curTool.setStrokeWidth(m_penSize);
	}

	public void setForeBitmap(Bitmap foreBitmap) {
		if (foreBitmap != m_foreBitmap && null != foreBitmap) {
			// Recycle the bitmap.
			if (null != m_foreBitmap) {
				m_foreBitmap.recycle();
			}

			m_isSetForeBmp = true;

			// Remember the temporary fore bitmap.
			m_tempForeBitmap = BitmapUtil.duplicateBitmap(foreBitmap);

			// Here create a new fore bitmap to avoid crashing when set bitmap
			// to canvas.
			m_foreBitmap = BitmapUtil.duplicateBitmap(foreBitmap);
			if (null != m_foreBitmap && null != m_canvas) {
				m_canvas.setBitmap(m_foreBitmap);
			}

			m_canClear = true;

			invalidate();
		}
	}

	public Bitmap getForeBitmap() {
		return m_foreBitmap;
	}

	public void setTempForeBitmap(Bitmap tempForeBitmap) {
		if (null != tempForeBitmap) {
			if (null != m_foreBitmap) {
				m_foreBitmap.recycle();
			}

			m_foreBitmap = BitmapUtil.duplicateBitmap(tempForeBitmap);

			if (null != m_foreBitmap && null != m_canvas) {
				m_canvas.setBitmap(m_foreBitmap);
				invalidate();
			}
		}
	}

	public void createStrokeBitmap(int w, int h) {
		m_canvasWidth = w;
		m_canvasHeight = h;

		Bitmap bitmap = Bitmap.createBitmap(m_canvasWidth, m_canvasHeight,
				Bitmap.Config.ARGB_8888);
		if (null != bitmap) {
			m_foreBitmap = bitmap;
			// Set the fore bitmap to m_canvas to be as canvas of strokes.
			m_canvas.setBitmap(m_foreBitmap);
		}
	}

	@Override
	public void redo() {
		if (null != m_undoStack) {
			m_undoStack.redo();
		}
	}

	@Override
	public void undo() {
		if (null != m_undoStack) {
			m_undoStack.undo();
		}
	}

	@Override
	public boolean canRedo() {
		// TODO Auto-generated method stub
		if (null != m_undoStack) {
			return m_undoStack.canRedo();
		}
		return false;
	}

	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		if (null != m_undoStack) {
			return m_undoStack.canUndo();
		}
		return false;
	}

	@Override
	public void onDeleteFromRedoStack() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onDeleteFromUndoStack() {
		// TODO Auto-generated method stub

	}

}
