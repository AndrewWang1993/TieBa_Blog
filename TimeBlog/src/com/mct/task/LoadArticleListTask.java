package com.mct.task;

import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

import com.mct.model.ArticleItem;
import com.mct.utils.ArticleUtil;
import com.mct.utils.HttpUtils;

/**
 * 加载帖子列表
 * 
 * @author Whunf
 * 
 */
public class LoadArticleListTask extends AsyncTask<Object, Integer, Object> {

	private OnLoadArticleListListener loadListListener;

	private ArticleUtil util;

	public LoadArticleListTask(OnLoadArticleListListener loadListListener) {
		this.loadListListener = loadListListener;
		util = new ArticleUtil();
	}

	@Override
	protected Object doInBackground(Object... params) {
		// TODO Auto-generated method stub
		if (params.length == 2) {
			int current = (Integer) params[0];
			int limit = (Integer) params[1];
			return util.loadArticleItems(current, limit);
		} else {
			return util.loadArticleItems(0, 0);
		}
	}

	@Override
	protected void onPostExecute(Object result) {
		// TODO Auto-generated method stub
		if (null != loadListListener) {
			if (result instanceof ArrayList) {
				loadListListener.onLoadSuccess((List<ArticleItem>) result);
			} else {
				loadListListener.onLoadFail();
			}
		}
		super.onPostExecute(result);
	}

	public interface OnLoadArticleListListener {
		void onLoadSuccess(List<ArticleItem> list);

		void onLoadFail();
	}

	public OnLoadArticleListListener getLoadListListener() {
		return loadListListener;
	}

	public void setLoadListListener(OnLoadArticleListListener loadListListener) {
		this.loadListListener = loadListListener;
	}

}
