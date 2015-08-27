package com.mct.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

/**
 * 加载头像管理器
 * 
 * @author huajian.zhang
 * 
 */
public class LoadIconManager {
	// 下载头像任务，如果下载成功则从记录中移除，如果下载失败则不移除，以便获取默认头像显示
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

	// 显示用户头像
	public void showHeadIcon(Context context, ImageView iv, String name,
			String url, OnDownloadListener listener) {
		boolean isHasIconInRemote = false;
		//如果该用户头像未有下载任务下载则判断是否有下载地址
		if (!taskMap.containsKey(name)) {
			isHasIconInRemote = null != url && !"".equals(url);
		}
		int result = BitmapUtil.getBitmap(context, name, isHasIconInRemote, iv);
		if (result == 0) {
			startLoad(name, url, listener); // 有下载地址并且需要下载头像
		}
	}

	// 清除所有已执行完毕的下载任务记录
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

	// 开始加载
	private void startLoad(String userName, String url,
			OnDownloadListener listener) {
		// 判断是否已经有任务在加载该头像，如果已有则不再启动任务加载
		if (taskMap.containsKey(userName)) {
			return;
		}
		// 创建加载任务，并添加到Map中记录，以防止有多条任务同时加载同一个头像的情况
		DownloadTask task = new DownloadTask(userName, listener);
		taskMap.put(userName, task);
		task.execute(url);
	}

	class DownloadTask extends AsyncTask<Object, Integer, Object> {
		private OnDownloadListener listener;

		private String userName;

		private boolean isEnd; // 记录任务是否执行完成

		public DownloadTask(String userName, OnDownloadListener listener) {
			this.userName = userName;
			this.listener = listener;
			isEnd = false;
		}

		@Override
		protected Object doInBackground(Object... params) {
			// TODO Auto-generated method stub
			String url = (String) params[0];// 下载地址
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
					// 加载成功则移除下载记录
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
