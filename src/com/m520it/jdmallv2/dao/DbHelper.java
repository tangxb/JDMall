package com.m520it.jdmallv2.dao;

import com.m520it.jdmallv2.cons.DbConst;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DbHelper extends SQLiteOpenHelper {

//	1.创建数据库文件  创建数据库表
//	2.创建Dao--> CURD  错     业务驱动代码
	public DbHelper(Context context) {
		super(context, DbConst.DB_NAME, null, DbConst.DB_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
//		写创建数据库表的方法  
//		只有在第一次getReadableDatabase/getWrittableDatabase
//		才会调用onCreate 所有创建数据库表只有一次
		db.execSQL("create table "+DbConst.TABLE_USER
				+"("+DbConst._ID+" integer primary key autoincrement,"
				+DbConst._USERNAME+" text,"
				+DbConst._PWD+" text);");
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
