package com.pangff;

import java.util.HashMap;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class ImportUtil {
	private SQLiteDatabase db;
	public ImportUtil(SQLiteDatabase db){
		this.db = db;
	}
	/**
     * 建表
     * @param db
     */
    public void createTable(){
    	 String sql = "CREATE TABLE IF NOT EXISTS readinfo (applicant varchar(60),applicant_enu varchar(60),ipr_title varchar(60),arp_name varchar(60),pc varchar(60),ipr_no varchar(60),record_no varchar(60) primary key,name varchar(60),company varchar(60),tel varchar(60),email varchar(60))";
         DBUtil.exSQL(db, sql);
         Log.v("", "!!!!!!!!!!createTable"); 
    }
    /**
     * 删表
     * @param db
     */
    public void deleteTable(SQLiteDatabase db){
	   	 String sql = "DROP TABLE IF EXISTS firstdemo";
	     DBUtil.exSQL(db, sql);
	     db.close();
    }
	 /**
     * 插入当前阅读的文件
     * @param db
     */
    public void insert(String values[]){
    	String sql = "insert into readinfo(applicant,applicant_enu,ipr_title,arp_name,pc,ipr_no,record_no,name,company,tel,email) values(?,?,?,?,?,?,?,?,?,?,?)";
    	DBUtil.exSQL(db,sql,values);
    	Log.v("", "!!!!!!!!!!insert"); 
    	//db.close();
    }
    
    public Map<String,String> selectAll(){
    	Map<String,String> data = new HashMap<String,String>();
    	String sql = "select * from readinfo";
    	Cursor cursor= DBUtil.exSelectSQL(db, sql ,null);
    	while (cursor.moveToNext()) {  
		    data.put("applicant", cursor.getString(0));
		    data.put("applicant_enu", cursor.getString(1));
		    data.put("ipr_title", cursor.getString(2));
		    data.put("arp_name", cursor.getString(3));
		    data.put("pc", cursor.getString(4));
		    data.put("ipr_no", cursor.getString(5));
		    data.put("record_no", cursor.getString(6));
		    data.put("name", cursor.getString(7));
		    data.put("company", cursor.getString(8));
		    data.put("tel", cursor.getString(9));
		    data.put("email", cursor.getString(10));
		}  
    	cursor.close();  
    	Log.v("!!!!!!!!!!", "!!!!!!!!!!select"); 
		//db.close();
		return data;
    }
    
    public int selectCountById(String record_no){
    	String id[] = {record_no};
    	int count = 0;
    	String sql = "select count(record_no) from readinfo where record_no=?";
    	Cursor cursor= DBUtil.exSelectSQL(db, sql ,id);
    	while (cursor.moveToNext()) {  
    		count = cursor.getInt(0);
		}  
    	cursor.close();  
		return count;
    }
}
