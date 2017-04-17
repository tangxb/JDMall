package com.m520it.jdmallv2.controller;

import com.m520it.jdmallv2.cons.IDiyMessage;
import com.m520it.jdmallv2.dao.UserDao;

import android.content.Context;

public class MineController extends BaseConttoller {

	public MineController(Context c) {
		super(c);
	}

	@Override
	protected void handleMessage(int action, Object... values) {
		switch (action) {
		case IDiyMessage.ACTION_DELETE_USER:
			deleteUser();
			onModelChanged(IDiyMessage.ACTION_DELETE_USER_RESULT,0);
			break;
		}
	}

	private void deleteUser() {
		UserDao dao=new UserDao(mContext);
		dao.deleteAllUser();
	}

}
