package com.mct.task;

import com.mct.utils.UserUtil;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

/**
 * 用户相关任务：登录、注册、获取用户信息、修改用户信息
 * 
 * @author Whunf
 * 
 */
public class UserTask extends AsyncTask<Object, Void, Integer> {
	// 登录
	public static final int OPT_LOGIN = 1;
	// 注册
	public static final int OPT_REGIST = 2;
	// 获取用户信息
	public static final int OPT_GETINFO = 3;

	private int opt;

	// 处理结果的返回
	private Handler handler;

	public UserTask(int opt, Handler handler) {
		this.opt = opt;
		this.handler = handler;
	}

	@Override
	protected Integer doInBackground(Object... params) {
		// TODO Auto-generated method stub
		switch (opt) {
		case OPT_LOGIN:
			// 处理登录
			if (params.length == 2) {
				String userName = (String) params[0];
				String psw = (String) params[1];
				return UserUtil.getInstance().login(userName, psw);
			}
			break;
		case OPT_REGIST:
			// 处理注册
			String userName = (String) params[0];
			String psw = (String) params[1];
			String iconPath = null;
			if (params.length == 3) {
				iconPath = (String) params[2];
			}
			return UserUtil.getInstance().regist(userName, psw, iconPath);
		case OPT_GETINFO:
			// 处理获取用户信息

			break;

		}
		return null;
	}

	@Override
	protected void onPostExecute(Integer result) {
		// TODO Auto-generated method stub
		if (null != handler) {
			Message msg = null;
			if (result == 1) {
				msg = handler.obtainMessage(1);
			} else {
				msg = handler.obtainMessage(0);
			}
			msg.sendToTarget();
		}
	}

}
