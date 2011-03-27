package com.pangff.mycanvas.view;

import com.pangff.mycanvas.activity.R;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

public class MainFragment extends Fragment{
	  private CanvasView mClickListener;
	  
	  private OnClickListener menuClickListener;
	  private String text;
	  
	  
	  public String getText() {
		return text;
	  }

	  public MainFragment(String text){
		super();
	  }
	
	  @Override
	  public View onCreateView(LayoutInflater inflater, ViewGroup container,
	          Bundle savedInstanceState) {
		  mClickListener = (CanvasView)this.getActivity().findViewById(R.id.curveMap);
	      View mainFragment = inflater.inflate(R.layout.menu_first_fragment, container, false);
	      ImageButton pen = (ImageButton)mainFragment.findViewById(R.id.pen);
	      ImageButton zx = (ImageButton)mainFragment.findViewById(R.id.zx);
	      ImageButton eraser = (ImageButton)mainFragment.findViewById(R.id.xp);
	      ImageButton circle = (ImageButton)mainFragment.findViewById(R.id.ty);
	      ImageButton rect = (ImageButton)mainFragment.findViewById(R.id.zfx);
	      ImageButton cx = (ImageButton)mainFragment.findViewById(R.id.cx);
	      ImageButton sz = (ImageButton)mainFragment.findViewById(R.id.sz);
	      pen.setOnClickListener(mClickListener);
	      zx.setOnClickListener(mClickListener);
	      eraser.setOnClickListener(mClickListener);
	      circle.setOnClickListener(mClickListener);
	      rect.setOnClickListener(mClickListener);
	      cx.setOnClickListener(mClickListener);
	      sz.setOnClickListener(mClickListener);
	      return mainFragment;
	  }


}
