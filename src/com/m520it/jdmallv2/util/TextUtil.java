package com.m520it.jdmallv2.util;

import android.graphics.Paint;
import android.widget.TextView;

public class TextUtil {
	
	public static void setStrike(TextView tv){
		tv.getPaint().setFlags(Paint. STRIKE_THRU_TEXT_FLAG); 
	}
	
}
