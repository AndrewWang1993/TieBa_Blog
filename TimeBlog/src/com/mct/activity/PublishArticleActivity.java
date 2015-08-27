package com.mct.activity;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;

import com.mct.blog.R;
import com.mct.utils.ArticleUtil;
import com.mct.utils.CommonUtil;

/**
 * 发布帖子界面
 * 
 * @author huajian.zhang
 * 
 */
public class PublishArticleActivity extends Activity implements OnClickListener {
	private EditText etTitle, etContent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_publish_article);
		etTitle = (EditText) findViewById(R.id.et_publish_title);
		etContent = (EditText) findViewById(R.id.et_publish_content);
		findViewById(R.id.btn_publish_article).setOnClickListener(this);
		findViewById(R.id.btn_cancel).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.btn_publish_article:
			String title = etTitle.getText().toString();
			if (null != title && !"".equals(title.trim())) {
				String content = etContent.getText().toString();
				if (null != content && !"".equals(content.trim())) {
					new PublishTask().execute(title, content);
				} else {
					CommonUtil.showMsg(this, "帖子内容不能为空");
				}
			} else {
				CommonUtil.showMsg(this, "未输入帖子标题");
			}
			break;
		case R.id.btn_cancel:
			setResult(RESULT_CANCELED);
			finish();
			break;
		}
	}

	class PublishTask extends AsyncTask<Object, Void, Boolean> {

		@Override
		protected Boolean doInBackground(Object... params) {
			// TODO Auto-generated method stub
			String title = (String) params[0];
			String content = (String) params[1];
			return new ArticleUtil().publishArticle(title, content);
		}

		@Override
		protected void onPostExecute(Boolean result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (result) {
				setResult(RESULT_OK);
				finish();
			} else {
				CommonUtil.showMsg(PublishArticleActivity.this, "发布失败");
			}
		}

	}

}
