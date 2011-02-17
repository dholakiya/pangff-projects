package com.pangff;

import android.app.FragmentTransaction;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class ArticleListFragment  extends ListFragment{
	
	  boolean mDualPane;//标示是否是横向布局，右侧reader视图是否可见
      int mCurCheckPosition = 0;//鼠标所选的标题索引
      int mShownCheckPosition = -1;//当前reader视图显示的内容索引
      
      @Override
      //当该fragment所属activity创建时
      public void onActivityCreated(Bundle savedInstanceState) {
          super.onActivityCreated(savedInstanceState);

          // Populate list with our static array of titles.
          setListAdapter(new ArrayAdapter<String>(getActivity(),
                  android.R.layout.simple_list_item_activated_1, Shakespeare.TITLES));

          // Check to see if we have a frame in which to embed the details
          // fragment directly in the containing UI.
          View detailsFrame = getActivity().findViewById(R.id.viewer);
          mDualPane = detailsFrame != null && detailsFrame.getVisibility() == View.VISIBLE;

          if (savedInstanceState != null) {
              // Restore last state for checked position.
              mCurCheckPosition = savedInstanceState.getInt("curChoice", 0);
              mShownCheckPosition = savedInstanceState.getInt("shownChoice", -1);
          }

          if (mDualPane) {
              // In dual-pane mode, the list view highlights the selected item.
              getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
              // Make sure our UI is in the correct state.
              showDetails(mCurCheckPosition);
          }
      }

      @Override
      /**
       * 先存储数据，在onActivityCreated里接收
       */
      public void onSaveInstanceState(Bundle outState) {
          super.onSaveInstanceState(outState);
          outState.putInt("curChoice", mCurCheckPosition);//初始为0
          outState.putInt("shownChoice", mShownCheckPosition);//初始为-1
      }

      @Override
      public void onListItemClick(ListView l, View v, int position, long id) {
          showDetails(position);
      }

      /**
       * Helper function to show the details of a selected item, either by
       * displaying a fragment in-place in the current UI, or starting a
       * whole new activity in which it is displayed.
       */
      void showDetails(int index) {
          mCurCheckPosition = index;

          if (mDualPane) {
              // We can display everything in-place with fragments, so update
              // the list to highlight the selected item and show the data.
              getListView().setItemChecked(index, true);

              if (mShownCheckPosition != mCurCheckPosition) {
                  // If we are not currently showing a fragment for the new
                  // position, we need to create and install a new one.
                  ArticleReaderFragment df = ArticleReaderFragment.newInstance(index);

                  // Execute a transaction, replacing any existing fragment
                  // with this one inside the frame.
                  FragmentTransaction ft = getFragmentManager().beginTransaction();
                  ft.replace(R.id.viewer, df);
                  ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                  ft.commit();
                  mShownCheckPosition = index;
              }

          } else {
              // Otherwise we need to launch a new activity to display
              // the dialog fragment with selected text.
              Intent intent = new Intent();
              intent.setClass(getActivity(), ArticleReaderActivity.class);
              intent.putExtra("index", index);
              startActivity(intent);
          }
      }
	
	

}
