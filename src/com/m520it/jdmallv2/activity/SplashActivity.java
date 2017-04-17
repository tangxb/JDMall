package com.m520it.jdmallv2.activity;

import java.util.Timer;
import java.util.TimerTask;

import android.os.Bundle;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.m520it.jdmallv2.R;
import com.m520it.jdmallv2.util.ActivityUtil;

//一个应用 一般每次启动都会展示启动页  
//欢迎页面 第一次安装APP的时候才有
public class SplashActivity extends BaseActivity {
	
	private ImageView mIv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		mIv=(ImageView)findViewById(R.id.app_icon_iv);
//		需求： 做一个渐变的动画2秒  
		alphaAnimation();
//		2秒之后  启动登录的Activity 通过定时器
		new Timer().schedule(new TimerTask() {
			
			@Override
			public void run() {
//		Thread.currentThread().getName() 通过获取线程的名称来区别
//				里面的方法都是在另一条线程里面调用的
				runOnUiThread(new Runnable() {
					public void run() {
						ActivityUtil.start(SplashActivity.this, LoginActivity.class);
					}
				});
			}
		}, 2000);
		
	}

	private void alphaAnimation() {
		AlphaAnimation alpha=new AlphaAnimation(0.2f, 1.0f);
		alpha.setDuration(2000);
		alpha.setFillAfter(true);
		mIv.startAnimation(alpha);
	}
	
}
