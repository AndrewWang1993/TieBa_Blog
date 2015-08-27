package com.mct.task;

import com.mct.utils.UserUtil;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

/**
 * �û�������񣺵�¼��ע�ᡢ��ȡ�û���Ϣ���޸��û���Ϣ
 * 
 * @author Whunf
 * 
 */
public class UserTask extends AsyncTask<Object, Void, Integer> {
	// ��¼
	public static final int OPT_LOGIN = 1;
	// ע��
	public static final int OPT_REGIST = 2;
	// ��ȡ�û���Ϣ
	public static final int OPT_GETINFO = 3;

	private int opt;

	// �������ķ���
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
			// �����¼
			if (params.length == 2) {
				String userName = (String) params[0];
				String psw = (String) params[1];
				return UserUtil.getInstance().login(userName, psw);
			}
			break;
		case OPT_REGIST:
			// ����ע��
			String userName = (String) params[0];
			String psw = (String) params[1];
			String iconPath = null;
			if (params.length == 3) {
				iconPath = (String) params[2];
			}
			return UserUtil.getInstance().regist(userName, psw, iconPath);
		case OPT_GETINFO:
			// �����ȡ�û���Ϣ

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
