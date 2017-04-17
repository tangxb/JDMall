package com.m520it.jdmallv2.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.m520it.jdmallv2.bean.RResult;
import com.m520it.jdmallv2.bean.User;
import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.cons.NetworkConst;
import com.m520it.jdmallv2.dao.UserDao;
import com.m520it.jdmallv2.util.AESUtils;
import com.m520it.jdmallv2.util.NetWorkUtil;

//这个类 做有关注册 登录  重置密码 有关的网络操作
public class LoginController extends BaseConttoller {
	
	private UserDao mUserDao;
	
	public LoginController(Context c) {
		super(c);
		mUserDao = new UserDao(mContext);
	}

	@Override
	protected void handleMessage(int action, Object... values) {
		switch (action) {
		case IDiyMessage.ACTION_LOGIN:
			// 3. 获取网络结果成功 告诉Activity 修改UI
			onModelChanged(IDiyMessage.ACTION_LOGIN_RESULT,
					login((String) values[0], (String) values[1]));
			break;
		case IDiyMessage.ACTION_REGIST:
			onModelChanged(IDiyMessage.ACTION_REGIST_RESULT,
					regist((String) values[0], (String) values[1]));
			break;
		case IDiyMessage.ACTION_RESET:
			onModelChanged(IDiyMessage.ACTION_RESET_RESULT,
					reset((String) values[0]));
			break;
		case IDiyMessage.ACTION_SAVE_USER:
			saveUserInfo((String)values[0],(String)values[1]);
			break;
		case IDiyMessage.ACTION_GET_USER:
			onModelChanged(IDiyMessage.ACTION_GET_USER_RESULT,
					getUser());
			break;
		}

	}

	private User getUser() {
		return mUserDao.getRecentUser();
	}

	private void saveUserInfo(String name,String pwd) {
//		保存数据之前删除掉之前保存的所有数据
		try {
			mUserDao.deleteAllUser();
			mUserDao.saveUser(name, AESUtils.encrypt(pwd));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private RResult reset(String name) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", name));
		String result = NetWorkUtil.doPost(NetworkConst.RESET_URL, params);
		return JSON.parseObject(result, RResult.class);
	}

	private RResult regist(String name, String pwd) {
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(new BasicNameValuePair("username", name));
		params.add(new BasicNameValuePair("pwd", pwd));
		String result = NetWorkUtil.doPost(NetworkConst.REGIST_URL, params);
		return JSON.parseObject(result, RResult.class);
	}

	private RResult login(String name, String pwd) {
		// 不需要写子线程 因为父类已经实现
		// 直接写登录请求的业务方法
		// 下面是发送网络请求
		Map<String, String> map=new HashMap<String, String>();
		map.put("username", name);
		map.put("pwd", pwd);
		String result = NetWorkUtil.doPost(NetworkConst.LOGIN_URL, map);
		
		// 返回的是JSON语句 需要转换成对象
		return JSON.parseObject(result, RResult.class);
	}

}
