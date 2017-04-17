package com.m520it.jdmallv2.cons;

import android.provider.BaseColumns;

public class DbConst implements BaseColumns{

	public static final String DB_NAME = "jdmall.db";
	
	public static final int DB_VERSION = 1;

	public static final String TABLE_USER = "userinfo";
	public static final String _USERNAME = "username";
	public static final String _PWD = "pwd";
}
