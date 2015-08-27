package com.mct.view.fragment;

import java.text.SimpleDateFormat;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mct.activity.MainActivity;
import com.mct.activity.ReplyActivity;
import com.mct.blog.R;
import com.mct.model.Article;
import com.mct.model.ArticleItem;
import com.mct.task.ReplyTask;
import com.mct.utils.ArticleUtil;
import com.mct.utils.CommonUtil;
import com.mct.utils.LoadIconManager;

public class ArticleDetailFragment extends Fragment implements OnClickListener {
	private View mainLayout;
	private TextView tvTitle, tvUserName, tvTime, tvContent, tvReplyCount;
	private EditText etReply;
	private ImageView icon;
	private int articleId;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		tvTitle = (TextView) mainLayout.findViewById(R.id.tv_title);
		tvUserName = (TextView) mainLayout.findViewById(R.id.tv_user);
		tvTime = (TextView) mainLayout.findViewById(R.id.tv_time);
		tvContent = (TextView) mainLayout.findViewById(R.id.tv_content);
		tvReplyCount = (TextView) mainLayout.findViewById(R.id.tv_reply_count);
		tvReplyCount.setOnClickListener(this);
		etReply = (EditText) mainLayout.findViewById(R.id.et_reply);
		icon = (ImageView) mainLayout.findViewById(R.id.image_detail_icon);
		mainLayout.findViewById(R.id.tv_back).setOnClickListener(this);
		mainLayout.findViewById(R.id.tv_publish_reply).setOnClickListener(this);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		mainLayout = inflater.inflate(R.layout.fragment_article_detail, null);
		return mainLayout;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_back:
			((MainActivity) getActivity()).backToArticleList();
			break;
		case R.id.tv_publish_reply:
			String content = etReply.getText().toString();
			if (null != content && !"".equals(content)) {
				new ReplyTask(onPublishListener).execute(articleId, content, 0);
			} else {
				// 没有内容则直接跳转到评论页面
				toReplyActivity();
			}
			break;
		case R.id.tv_reply_count:
			toReplyActivity();
			break;
		}

	}

	// 跳转到回复界面
	private void toReplyActivity() {
		Intent intent = new Intent(getActivity(), ReplyActivity.class);
		intent.putExtra("article_id", articleId);
		startActivity(intent);
	}

	// 发表评论监听
	private ReplyTask.OnPublishReplyListener onPublishListener = new ReplyTask.OnPublishReplyListener() {

		@Override
		public void publishSuccess() {
			// TODO Auto-generated method stub
			etReply.setText("");
			CommonUtil.showMsg(getActivity(), "发表成功");
		}

		@Override
		public void publishFail() {
			// TODO Auto-generated method stub
			CommonUtil.showMsg(getActivity(), "发表失败");
		}
	};

	// 开始加载帖子详情
	public void loadData(ArticleItem item) {
		articleId = item.getA_id();
		tvTitle.setText(item.getTitle());
		tvUserName.setText(item.getU_name());
		long t = Long.parseLong(item.getTime());
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		tvTime.setText(sf.format(t));
		tvReplyCount.setText("跟帖:" + String.valueOf(item.getReply_count()));
		tvContent.setText("内容加载中...");
		LoadIconManager.getInstance().showHeadIcon(getActivity(), icon,
				item.getU_name(), item.getU_photo(), null);
		new LoadArticleDetailTask().execute(item.getA_id());
	}

	// 加载帖子详情任务
	class LoadArticleDetailTask extends AsyncTask<Object, Void, Article> {

		@Override
		protected Article doInBackground(Object... params) {
			// TODO Auto-generated method stub
			int id = (Integer) params[0];
			return new ArticleUtil().getArticle(id);
		}

		@Override
		protected void onPostExecute(Article result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (null != result) {
				tvContent.setText(result.getContent());
			} else {
				tvContent.setText("加载错误");
			}
		}

	}

}
