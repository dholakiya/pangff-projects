package com.pangff.mycanvas.utils;

import com.pangff.mycanvas.activity.R;
import com.pangff.mycanvas.view.MainFragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.app.ActionBar.Tab;

public class TabListener implements ActionBar.TabListener {
    private MainFragment mainFragment;

    public TabListener(MainFragment fragment) {
    	mainFragment = fragment;
    }

    public void onTabSelected(Tab tab, FragmentTransaction ft) {
        ft.add(R.id.fragment_content, mainFragment, mainFragment.getText());
    }

    public void onTabUnselected(Tab tab, FragmentTransaction ft) {
        ft.remove(mainFragment);
    }

    public void onTabReselected(Tab tab, FragmentTransaction ft) {
        //Toast.makeText(MianActivity.this, "Reselected!", Toast.LENGTH_SHORT).show();
    }


}