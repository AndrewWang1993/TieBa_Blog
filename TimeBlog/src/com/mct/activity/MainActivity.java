package com.mct.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.mct.blog.R;
import com.mct.control.MainPagerAdater;
import com.mct.model.ArticleItem;
import com.mct.utils.UserUtil;
import com.mct.view.fragment.ArticleDetailFragment;
import com.mct.view.fragment.ArticleListFragment;
import com.mct.view.fragment.SettingFragment;
import com.mct.view.fragment.SquareFragment;
import com.mct.view.fragment.UserInfoFragment;

public class MainActivity extends FragmentActivity implements OnClickListener {
	private enum ShowStatus { // ����������ʾ�����ص�״̬
		HIDE, SHOW
	}

	private ShowStatus status;
	private ViewPager mPager;
	private RadioGroup tabGroup;
	private Fragment[] fragments;
	// ����������Ƭ
	private ArticleDetailFragment mArticleDetailFragment;
	// ��Ļ���
	private int screenWidth;
	// ��ʾ��Ƭ�Ĳ���
	private FrameLayout fragmentLayout;
	private LinearLayout contentLayout;
	// ��ʾ��Ƭ�Ĳ��ֲ�������
	private LinearLayout.LayoutParams layoutParam;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		screenWidth = getWindowManager().getDefaultDisplay().getWidth();

		// ����ViewPager���������Ŀ��
		contentLayout = (LinearLayout) findViewById(R.id.m_content);
		LinearLayout.LayoutParams lp2 = (LinearLayout.LayoutParams) contentLayout
				.getLayoutParams();
		lp2.width = screenWidth;
		contentLayout.setLayoutParams(lp2);

		// ��������������Ƭ���������Ŀ��
		fragmentLayout = (FrameLayout) findViewById(R.id.fragment_detail);
		layoutParam = (LinearLayout.LayoutParams) fragmentLayout
				.getLayoutParams();
		layoutParam.width = screenWidth;
		layoutParam.leftMargin = -screenWidth;
		fragmentLayout.setLayoutParams(layoutParam);
		status = ShowStatus.HIDE;

		// ������Ƭ
		mArticleDetailFragment = new ArticleDetailFragment();
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.fragment_detail, mArticleDetailFragment);
		ft.commit();

		mPager = (ViewPager) findViewById(R.id.m_pager);
		tabGroup = (RadioGroup) findViewById(R.id.tab_group);
		findViewById(R.id.tab_article).setOnClickListener(this);
		findViewById(R.id.tab_square).setOnClickListener(this);
		findViewById(R.id.tab_me).setOnClickListener(this);
		initPager();
	}

	private void initPager() {
		fragments = new Fragment[4];
		ArticleListFragment alf = new ArticleListFragment();
		alf.setOnArticleItemClickListener(onArticleItemClickListener);
		fragments[0] = alf;
		fragments[1] = new SquareFragment();
		fragments[2] = new UserInfoFragment();
		fragments[3] = new SettingFragment();
		MainPagerAdater adapter = new MainPagerAdater(
				getSupportFragmentManager(), fragments);
		mPager.setAdapter(adapter);
		mPager.setCurrentItem(2);
		mPager.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				if (arg0 < 3) {
					((RadioButton) tabGroup.getChildAt(arg0)).setChecked(true);
				} else {
					tabGroup.clearCheck();
				}
				if (arg0 == 0) {
					((ArticleListFragment) fragments[0]).loadData();
				}
			}

			@Override
			public void onPageScrolled(int position, float offset,
					int offsetInPixels) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub

			}
		});
	}

	private ArticleListFragment.OnArticleItemClickListener onArticleItemClickListener = new ArticleListFragment.OnArticleItemClickListener() {

		@Override
		public void onClick(ArticleItem item) {
			// TODO Auto-generated method stub
			showArticleDetail();
			mArticleDetailFragment.loadData(item);
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tab_article:
			mPager.setCurrentItem(0);
			break;
		case R.id.tab_square:
			mPager.setCurrentItem(1);
			break;
		case R.id.tab_me:
			mPager.setCurrentItem(2);
			break;
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		// ע���û�
		UserUtil.getInstance().logout();
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if (status == ShowStatus.SHOW) {
				backToArticleList();
			} else {
				FragmentTransaction ft = getSupportFragmentManager()
						.beginTransaction();
				ft.remove(mArticleDetailFragment);
				ft.commit();
				finish();
			}
			return true;
		}
		return super.onKeyUp(keyCode, event);
	}

	public void backToArticleList() {
		new ScrollTask().execute(-30);
	}

	/**
	 * ��ʾ��������
	 */
	public void showArticleDetail() {
		new ScrollTask().execute(30);
	}

	class ScrollTask extends AsyncTask<Integer, Integer, Integer> {

		@Override
		protected Integer doInBackground(Integer... params) {
			// TODO Auto-generated method stub
			int offset = params[0]; // ÿ���ƶ���ƫ����
			int currentMarginLeft = layoutParam.leftMargin;
			while (true) {
				currentMarginLeft += offset;
				if (currentMarginLeft >= 0) { // ��ȫ��ʾ����ҳ��
					currentMarginLeft = 0;
					break;
				}
				if (currentMarginLeft <= -screenWidth) { // ��ȫ��������ҳ��
					currentMarginLeft = -screenWidth;
					break;
				}
				publishProgress(currentMarginLeft);
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			return currentMarginLeft;
		}

		@Override
		protected void onPostExecute(Integer result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			layoutParam.leftMargin = result;
			fragmentLayout.setLayoutParams(layoutParam);
			if (result == 0) {
				// ������������Ѿ���ʾ����
				status = ShowStatus.SHOW;
			} else {
				status = ShowStatus.HIDE;
			}
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			// TODO Auto-generated method stub
			super.onProgressUpdate(values);
			layoutParam.leftMargin = values[0];
			fragmentLayout.setLayoutParams(layoutParam);
		}

	}

}
