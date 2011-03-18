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
	
	protected MenuDialog(Context context,CanvasView view) {
		super(context);
		mClickListener = view;
		// TODO Auto-generated constructor stub
	}
	
	@Override 
    protected void onCreate(Bundle savedInstanceState) { 
        super.onCreate(savedInstanceState); 
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
				break;
			case ToolsTypeConstants.CX2:
				size = 10;
				break;
			case ToolsTypeConstants.CX3:
				size = 15;
				break;
		}
		mClickListener.setStrokeWidth(size);
		this.dismiss();
	}

	

}
