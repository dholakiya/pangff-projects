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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
	    while ((nextLine = csvreader.readNext()) != null) {
	        // nextLine[] is an array of values from the line
	    	
	    	// We use the first column as note
	    	String applicant = nextLine[0];
	    	String applicant_enu = nextLine[1];
	    	String ipr_title = nextLine[2];
	    	String arp_name = nextLine[3];
	    	String pc = nextLine[4];
	    	String ipr_no = nextLine[5];
	    	String record_no = nextLine[6];
	    	String name = nextLine[7];
	    	String company = nextLine[8];
	    	String tel = nextLine[9];
	    	String email = nextLine[10];
	    	Log.v("Data", applicant+","+applicant_enu+","+ipr_title+","+arp_name+","+pc+","+ipr_no+","+record_no+","+name+","+company+","+tel+","+email);
	    	// And ignore the other columns
	    	
	    	// Third column would be category.
	    	importUtil.insert(nextLine);
	    	//NotepadUtils.addNote(mContext, note);
	    }
	    mContext.showImportData(importUtil.select());
	}

}
