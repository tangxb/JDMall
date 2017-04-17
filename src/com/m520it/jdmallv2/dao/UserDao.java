package com.m520it.jdmallv2.dao;

import com.m520it.jdmallv2.bean.User;
import com.m520it.jdmallv2.cons.DbConst;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * 用户登录的Dao层
 */
public class UserDao {

	private DbHelper mHelper;

	public UserDao(Context c) {
		mHelper = new DbHelper(c);
	}

	/**
	 * 保存用户信息
	 */
	public void saveUser(String name, String pwd) {
		SQLiteDatabase db = mHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DbConst._USERNAME, name);
		values.put(DbConst._PWD, pwd);
		db.insert(DbConst.TABLE_USER, null, values);
	}

	/**
	 * 获取最近登录的用户
	 */
	public User getRecentUser() {
		SQLiteDatabase db = mHelper.getReadableDatabase();
		Cursor cursor = db
				.query(DbConst.TABLE_USER, new String[] { DbConst._USERNAME,
						DbConst._PWD }, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			String name = cursor.getString(0);
			String pwd = cursor.getString(1);
			return new User(name, pwd);
		}
		return null;
	}

	/**
	 * 删除所有用户数据
	 */
	public void deleteAllUser(){
//		如果删除所有用户？？
//		删除的过程可能出现删除掉某一条数据出错  事务
		SQLiteDatabase db = mHelper.getWritableDatabase();
		db.delete(DbConst.TABLE_USER, null, null);
//		db.beginTransaction();
//	   try {
////			  删除每一条数据
//		   
//	     db.setTransactionSuccessful();
//	   } finally {
//	     db.endTransaction();
//	   }

	}
}
