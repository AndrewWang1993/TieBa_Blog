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
 * �����б���Ƭ
 * 
 * @author Whunf
 * 
 */
public class ArticleListFragment extends ListFragment implements
		OnScrollListener, OnClickListener {
	private OnArticleItemClickListener onArticleItemClickListener;
	private ArticleAdapter adapter;
	private List<ArticleItem> articleList;
	// ���ؿ��Ʊ�����������ÿ�ζ�Ҫ����
	private boolean isLoad = false;
	// ��ǰ��Ļ��ʾ�ĵ�һ������ǰ��Ļ��ʾ���ܸ������û��Ƿ��ڹ����б��״̬1�����ڹ��� 0���Ѿ��������
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

	// �������ݷ���
	public void loadData() {
		if (isLoad) {
			return;
		}
		isLoad = true;
		LoadArticleListTask task = new LoadArticleListTask(loadListListener);
		task.execute();
	}

	// ���¼��أ�ˢ�������б�
	private void reLoad() {
		isLoad = false;
		articleList = null;
		loadData();
	}

	private LoadIconManager.OnDownloadListener onLoadIconListener = new LoadIconManager.OnDownloadListener() {

		@Override
		public void onDownloadOK(String name) {
			// TODO Auto-generated method stub
			// �û�û�й����б��״̬�£�����ͷ��ɹ�ʱ���һ��ĿǰҪ��ʾͷ����û��Ƿ�����Ļ�ϣ�����ˢ�£�����ˢ��
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

	// ���������б����
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
			Toast.makeText(getActivity(), "����ʧ��", 2000).show();
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
			// ���뷢������ҳ��
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
