/* 
 * Copyright (C) 2008 OpenIntents.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pangff;

import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class ImportCsv {
	SQLiteDatabase db;
	ImportCsvDemoActivity mContext;
	
	public ImportCsv(SQLiteDatabase db,ImportCsvDemoActivity context) {
		this.db = db;
		mContext = context;
	}
	
	/**
	 * @param dis
	 * @throws IOException
	 */
	public void importCsv(Reader reader) throws IOException {
		
		
		ImportUtil importUtil = new ImportUtil(db);
		importUtil.createTable();//如果不存在创建
		
		CSVReader csvreader = new CSVReader(reader);
	    String [] nextLine;
	    int index = 0;
	    while ((nextLine = csvreader.readNext()) != null) {
	    	index++;
	    	String record_no = nextLine[6];
	    	if(record_no!=null && !record_no.equals("")){
	    		int count = importUtil.selectCountById(record_no);
	    		if(count==0){
	    	    	importUtil.insert(nextLine);
	    		}else{
		    		Toast.makeText(mContext, "记录编号:"+record_no+"的记录已存在!", Toast.LENGTH_SHORT).show();
		    	}
	    	}else{
	    		Toast.makeText(mContext, "第"+index+"行记录编号为空!", Toast.LENGTH_SHORT).show();
	    	}
	    }
	    mContext.showImportData(importUtil.selectAll());
	}

}
