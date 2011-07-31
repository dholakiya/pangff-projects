package com.pangff;

import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ImportCsvDemoActivity extends Activity {
    /** Called when the activity is first created. */
	private SQLiteDatabase db; 
	private TextView textView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        db = DBUtil.createDateBase(this, "userdata");
        Button importButton = (Button) this.findViewById(R.id.importButton);
        textView = (TextView) this.findViewById(R.id.importText);
        importButton.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Dialog searchFileDialog = new SearchFileDialog(ImportCsvDemoActivity.this);
		    	searchFileDialog.show();
			}
        	
        });
        
    }
    /**
	 * @param reader
	 * @throws IOException
	 */
	public void doImport(FileReader reader) throws IOException {
		ImportCsv ic = new ImportCsv(db,this);
		ic.importCsv(reader);
	}
	
	public void showImportData(Map<String, String> select) {
		// TODO Auto-generated method stub
		String text ="";
		Iterator it =select.values().iterator();
		while(it.hasNext()){
			text+=it.next()+",";
		}
		textView.setText(text);
	}

}