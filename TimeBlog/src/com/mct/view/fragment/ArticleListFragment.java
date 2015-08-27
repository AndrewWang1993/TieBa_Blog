package com.mct.view.fragment;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.Toast;

import com.mct.activity.PublishArticleActivity;
import com.mct.blog.R;
import com.mct.control.ArticleAdapter;
import com.mct.model.ArticleItem;
import com.mct.task.LoadArticleListTask;
import com.mct.utils.LoadIconManager;

/**
 * 帖子列表碎片
 * 
 * @author Whunf
 * 
 */
public class ArticleListFragment extends ListFragment implements
		OnScrollListener, OnClickListener {
	private OnArticleItemClickListener onArticleItemClickListener;
	private ArticleAdapter adapter;
	private List<ArticleItem> articleList;
	// 加载控制变量，并不是每次都要加载
	private boolean isLoad = false;
	// 当前屏幕显示的第一个、当前屏幕显示的总个数、用户是否在滚动列表的状态1：正在滚动 0：已经滚动完毕
	private int firstPos, currentTotal, state = 0;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View layout = inflater.inflate(R.layout.fragment_article, null);
		layout.findViewById(R.id.tv_write_article).setOnClickListener(this);
		return layout;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (null == adapter) {
			adapter = new ArticleAdapter(getActivity());
			adapter.setOnLoadIconListener(onLoadIconListener);
		}
		setListAdapter(adapter);
		getListView().setOnScrollListener(this);
	}

	// 加载数据方法
	public void loadData() {
		if (isLoad) {
			return;
		}
		isLoad = true;
		LoadArticleListTask task = new LoadArticleListTask(loadListListener);
		task.execute();
	}

	// 重新加载（刷新帖子列表）
	private void reLoad() {
		isLoad = false;
		articleList = null;
		loadData();
	}

	private LoadIconManager.OnDownloadListener onLoadIconListener = new LoadIconManager.OnDownloadListener() {

		@Override
		public void onDownloadOK(String name) {
			// TODO Auto-generated method stub
			// 用户没有滚动列表的状态下，下载头像成功时检测一下目前要显示头像的用户是否在屏幕上，在则刷新，否则不刷新
			if (state == 0) {
				int last = firstPos + currentTotal;
				for (int i = firstPos; i < last; i++) {
					if (((ArticleItem) adapter.getItem(i)).getU_name().equals(
							name)) {
						adapter.notifyDataSetChanged();
						break;
					}
				}
			}
		}

		@Override
		public void onDownloadFail(String name) {
			// TODO Auto-generated method stub
		}

	};

	// 加载帖子列表监听
	private LoadArticleListTask.OnLoadArticleListListener loadListListener = new LoadArticleListTask.OnLoadArticleListListener() {

		@Override
		public void onLoadSuccess(List<ArticleItem> list) {
			// TODO Auto-generated method stub
			if (articleList == null) {
				articleList = list;
				adapter.setArticleList(articleList);
				adapter.notifyDataSetChanged();
			} else {

			}
		}

		@Override
		public void onLoadFail() {
			// TODO Auto-generated method stub
			Toast.makeText(getActivity(), "加载失败", 2000).show();
		}
	};

	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		firstPos = firstVisibleItem;
		currentTotal = visibleItemCount;
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		state = scrollState;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_write_article:
			// 进入发布帖子页面
			Intent intent = new Intent(getActivity(),
					PublishArticleActivity.class);
			startActivityForResult(intent, 1);
			break;
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {
			reLoad();
		}

	}

	public interface OnArticleItemClickListener {
		void onClick(ArticleItem item);
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		if (null != onArticleItemClickListener) {
			onArticleItemClickListener.onClick((ArticleItem) adapter
					.getItem(position));
		}
	}

	public OnArticleItemClickListener getOnArticleItemClickListener() {
		return onArticleItemClickListener;
	}

	public void setOnArticleItemClickListener(
			OnArticleItemClickListener onArticleItemClickListener) {
		this.onArticleItemClickListener = onArticleItemClickListener;
	}

}
