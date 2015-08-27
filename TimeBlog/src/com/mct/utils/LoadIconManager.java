package com.mct.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * ����ͷ�������
 * 
 * @author huajian.zhang
 * 
 */
public class LoadIconManager {
	// ����ͷ������������سɹ���Ӽ�¼���Ƴ����������ʧ�����Ƴ����Ա��ȡĬ��ͷ����ʾ
	private Map<String, DownloadTask> taskMap;

	private static LoadIconManager instance;

	private LoadIconManager() {
		taskMap = new HashMap<String, DownloadTask>();
	}

	public static LoadIconManager getInstance() {
		if (null == instance) {
			instance = new LoadIconManager();
		}
		return instance;
	}

	// ��ʾ�û�ͷ��
	public void showHeadIcon(Context context, ImageView iv, String name,
			String url, OnDownloadListener listener) {
		boolean isHasIconInRemote = false;
		//������û�ͷ��δ�����������������ж��Ƿ������ص�ַ
		if (!taskMap.containsKey(name)) {
			isHasIconInRemote = null != url && !"".equals(url);
		}
		int result = BitmapUtil.getBitmap(context, name, isHasIconInRemote, iv);
		if (result == 0) {
			startLoad(name, url, listener); // �����ص�ַ������Ҫ����ͷ��
		}
	}

	// ���������ִ����ϵ����������¼
	public void reset() {
		Iterator it = taskMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String, DownloadTask> entry = (Entry<String, DownloadTask>) it
					.next();
			if (entry.getValue().isEnd) {
				taskMap.remove(entry.getKey());
			}
		}
	}

	// ��ʼ����
	private void startLoad(String userName, String url,
			OnDownloadListener listener) {
		// �ж��Ƿ��Ѿ��������ڼ��ظ�ͷ������������������������
		if (taskMap.containsKey(userName)) {
			return;
		}
		// �����������񣬲���ӵ�Map�м�¼���Է�ֹ�ж�������ͬʱ����ͬһ��ͷ������
		DownloadTask task = new DownloadTask(userName, listener);
		taskMap.put(userName, task);
		task.execute(url);
	}

	class DownloadTask extends AsyncTask<Object, Integer, Object> {
		private OnDownloadListener listener;

		private String userName;

		private boolean isEnd; // ��¼�����Ƿ�ִ�����

		public DownloadTask(String userName, OnDownloadListener listener) {
			this.userName = userName;
			this.listener = listener;
			isEnd = false;
		}

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			String url = (String) params[0];// ���ص�ַ
			return HttpUtils.download(url, CommonUtil.PHOTO_CACHE_PATH
					+ userName);
		}

		@Override
		protected void onPostExecute(Object result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (null != listener) {
				boolean r = (Boolean) result;
				if (r) {
					listener.onDownloadOK(userName);
					// ���سɹ����Ƴ����ؼ�¼
					taskMap.remove(userName);
				} else {
					listener.onDownloadFail(userName);
				}
			}
			isEnd = true;
		}

		public OnDownloadListener getListener() {
			return listener;
		}

		public void setListener(OnDownloadListener listener) {
			this.listener = listener;
		}

	}

	public interface OnDownloadListener {
		void onDownloadOK(String name);

		void onDownloadFail(String name);
	}

}
