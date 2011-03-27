package com.pangff.mycanvas.utils;

import com.pangff.mycanvas.constants.ToolsTypeConstants;
import com.pangff.mycanvas.interfaces.ISketchPadTool;
import com.pangff.mycanvas.tools.Beeline;
import com.pangff.mycanvas.tools.Brush;
import com.pangff.mycanvas.tools.Circle;
import com.pangff.mycanvas.tools.Eraser;
import com.pangff.mycanvas.tools.Pen;
import com.pangff.mycanvas.tools.Rect;

public class ToolkitFactory {
	/**
	 * 根据不同类型的type
	 * 生成不同类型的画图工具
	 * @param type
	 * @param size
	 * @param color
	 */
	public static ISketchPadTool getTools(int type,int size, int color){
		ISketchPadTool tool = null;
		switch(type){
			case ToolsTypeConstants.PEN:
				tool = new Pen(size,color);
				break;
			case ToolsTypeConstants.BEELINE:
				tool = new Beeline(size,color);
				break;
			case ToolsTypeConstants.ERASER:
				tool = new Eraser(size);
				break;
			case ToolsTypeConstants.CIRCLE:
				tool = new Circle(size,color);
				break;
			case ToolsTypeConstants.Rect:
				tool = new Rect(size,color);
				break;
			case ToolsTypeConstants.BRUSH1:
				tool = new Brush(size,color,ToolsTypeConstants.BRUSH1);
				break;
			case ToolsTypeConstants.BRUSH2:
				tool = new Brush(size,color,ToolsTypeConstants.BRUSH2);
				break;
		}
		return tool;
	}

}
