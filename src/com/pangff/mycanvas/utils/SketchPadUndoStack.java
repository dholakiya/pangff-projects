package com.pangff.mycanvas.utils;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.pangff.mycanvas.interfaces.ISketchPadTool;
import com.pangff.mycanvas.view.CanvasView;

public class SketchPadUndoStack {
	private int m_stackSize = 0;
	private CanvasView m_sketchPad = null;
	private ArrayList<ISketchPadTool> m_undoStack = new ArrayList<ISketchPadTool>();
	private ArrayList<ISketchPadTool> m_redoStack = new ArrayList<ISketchPadTool>();
	private ArrayList<ISketchPadTool> m_removedStack = new ArrayList<ISketchPadTool>();

	public SketchPadUndoStack(CanvasView sketchPad, int stackSize) {
		m_sketchPad = sketchPad;
		m_stackSize = stackSize;
	}

	public void push(ISketchPadTool sketchPadTool) {
		if (null != sketchPadTool) {
			if (m_undoStack.size() == m_stackSize && m_stackSize > 0) {
				ISketchPadTool removedTool = m_undoStack.get(0);
				m_removedStack.add(removedTool);
				m_undoStack.remove(0);
			}
			//m_undoStack.add(0, sketchPadTool);
			m_undoStack.add(sketchPadTool);
		}
	}

	public void clearAll() {
		m_redoStack.clear();
		m_undoStack.clear();
		m_removedStack.clear();
	}

	public void undo() {
		if (canUndo() && null != m_sketchPad) {
			ISketchPadTool removedTool = m_undoStack.get(m_undoStack.size() - 1);
			m_redoStack.add(removedTool);
			m_undoStack.remove(m_undoStack.size() - 1);

				// Create a new bitmap and set to canvas.
			m_sketchPad.createStrokeBitmap(m_sketchPad.m_canvasWidth,m_sketchPad.m_canvasHeight);

			Canvas canvas = m_sketchPad.m_canvas;

			// First draw the removed tools from undo stack.
			for (ISketchPadTool sketchPadTool : m_removedStack) {
				sketchPadTool.draw(canvas);
			}

			for (ISketchPadTool sketchPadTool : m_undoStack) {
				sketchPadTool.draw(canvas);
			}

			m_sketchPad.invalidate();
		}
	}

	public void redo() {
		if (canRedo() && null != m_sketchPad) {
			ISketchPadTool removedTool = m_redoStack
					.get(m_redoStack.size() - 1);
			m_undoStack.add(removedTool);
			m_redoStack.remove(m_redoStack.size() - 1);

				// Create a new bitmap and set to canvas.
			m_sketchPad.createStrokeBitmap(m_sketchPad.m_canvasWidth,m_sketchPad.m_canvasHeight);

			Canvas canvas = m_sketchPad.m_canvas;

			// First draw the removed tools from undo stack.
			for (ISketchPadTool sketchPadTool : m_removedStack) {
				sketchPadTool.draw(canvas);
			}

			for (ISketchPadTool sketchPadTool : m_undoStack) {
				sketchPadTool.draw(canvas);
			}

			m_sketchPad.invalidate();
		}
	}

	public boolean canUndo() {
		return (m_undoStack.size() > 0);
	}

	public boolean canRedo() {
		return (m_redoStack.size() > 0);
	}

}
