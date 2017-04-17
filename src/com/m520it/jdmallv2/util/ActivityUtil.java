package com.m520it.jdmallv2.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

public class ActivityUtil {

	public static void start(Context c,Class clazz){
		Intent intent=new Intent(c,clazz);
		c.startActivity(intent);
		((Activity)c).finish();
	}
	
	public static void start(Context c,Class clazz,boolean finish){
		Intent intent=new Intent(c,clazz);
		c.startActivity(intent);
		if (finish) {
			((Activity)c).finish();
		}
	}
}
