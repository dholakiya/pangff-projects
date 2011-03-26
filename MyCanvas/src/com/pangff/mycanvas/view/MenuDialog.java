package com.pangff.mycanvas.view;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.pangff.mycanvas.activity.R;
import com.pangff.mycanvas.constants.PenSizeConstants;
import com.pangff.mycanvas.constants.ToolsTypeConstants;

public class MenuDialog extends Dialog implements android.view.View.OnClickListener{
	private CanvasView mClickListener;
	
	private int layout;
	
	private int menuType ;
	protected MenuDialog(Context context,CanvasView view,int type) {
		super(context);
		mClickListener = view;
		menuType = type;
		// TODO Auto-generated constructor stub
	}
	
	@Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
        switch(menuType){
			case ToolsTypeConstants.CX:
				initCx();
				break;
			case ToolsTypeConstants.BRUSH:
				initBrush();
				break;
        }
       
    }
	
	private void initBrush() {
		// TODO Auto-generated method stub
		setContentView(R.layout.menu_brush); 
		this.setTitle("刷子");
        ImageButton brush1 = (ImageButton)this.findViewById(R.id.brush1);
        ImageButton brush2 = (ImageButton)this.findViewById(R.id.brush2);
        brush1.setOnClickListener(this);
        brush2.setOnClickListener(this);
	}

	private void initCx(){
		setContentView(R.layout.menu_thick_thin); 
		this.setTitle("粗细");
        ImageButton cx1 = (ImageButton)this.findViewById(R.id.cx1);
        ImageButton cx2 = (ImageButton)this.findViewById(R.id.cx2);
        ImageButton cx3 = (ImageButton)this.findViewById(R.id.cx3);
        cx1.setOnClickListener(this);
        cx2.setOnClickListener(this);
        cx3.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		int size =  PenSizeConstants.MIDDLE_PEN_WIDTH;
		switch (v.getId()){
			case ToolsTypeConstants.CX1:
				size = 3;
				mClickListener.setStrokeWidth(size);
				break;
			case ToolsTypeConstants.CX2:
				size = 10;
				mClickListener.setStrokeWidth(size);
				break;
			case ToolsTypeConstants.CX3:
				size = 15;
				mClickListener.setStrokeWidth(size);
				break;
			case ToolsTypeConstants.BRUSH1:
				mClickListener.setBrush(v.getId());
				break;
			case ToolsTypeConstants.BRUSH2:
				mClickListener.setBrush(v.getId());
				break;
		}
		this.dismiss();
	}

	

}
