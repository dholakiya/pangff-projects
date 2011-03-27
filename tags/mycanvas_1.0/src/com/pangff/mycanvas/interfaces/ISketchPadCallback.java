package com.pangff.mycanvas.interfaces;

import android.view.MotionEvent;

import com.pangff.mycanvas.view.CanvasView;

public interface ISketchPadCallback
{
    public void onTouchDown(CanvasView obj, MotionEvent event);
    public void onTouchUp(CanvasView obj, MotionEvent event);
    public void onDestroy(CanvasView obj);
}
