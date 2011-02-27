package com.pangff;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


public class DBUtil{
	/**
	 * 创建数据库
	 * @param context
	 * @param name
	 * @return
	 */
	public static SQLiteDatabase createDateBase(Context context,String name){
		SQLiteDatabase dataBase= null;
		if(!name.endsWith(".db")){
			name +=".db"; 
		}
		try{
		 dataBase=context.openOrCreateDatabase(name,context.MODE_PRIVATE,null);
		// dataBase.close();
		}catch(Exception e){
			e.printStackTrace();
		}
		return dataBase;
	}
	/**
	 * 删除数据库
	 * @param context
	 * @param name
	 * @return
	 */
	public static boolean deleteDateBase(Context context,String name){
		boolean isOk = false;
		if(!name.endsWith(".db")){
			name +=".db"; 
		}
		try{
			isOk = context.deleteDatabase(name);
		}catch(Exception e){
			e.printStackTrace();
		}
		return isOk;
	}
	/**
	 * 执行SQL,可是使用占位符“？”的方法
	 */
	public static void exSQL(SQLiteDatabase dataBase,String sql,Object[] bindArgs){
		try{
			dataBase.execSQL(sql,bindArgs);
			//dataBase.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 执行SQL
	 */
	public static void exSQL(SQLiteDatabase dataBase,String sql){
		try{
			dataBase.execSQL(sql);
			//dataBase.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	/**
	 * 查询
	 */
	public static Cursor exSelectSQL(SQLiteDatabase dataBase,String sql){
		Cursor cursor = dataBase.rawQuery(sql, null);  
		return cursor;
	}
}
