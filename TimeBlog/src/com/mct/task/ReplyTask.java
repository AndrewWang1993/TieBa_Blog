package com.mct.task;

import com.mct.utils.ReplyUtil;

import android.os.AsyncTask;

/**
 * 回复相关任务：发表回复
 * 
 * @author Whunf
 * 
 */
public class ReplyTask extends AsyncTask<Object, Integer, Boolean> {

	private OnPublishReplyListener onPublishReplyListener;

	public ReplyTask(OnPublishReplyListener onPublishReplyListener) {
		this.onPublishReplyListener = onPublishReplyListener;
	}

	@Override
	protected Boolean doInBackground(Object... params) {
		// TODO Auto-generated method stub
		int article_id = (Integer) params[0];
		String content = (String) params[1];
		int parent = (Integer) params[2];
		return new ReplyUtil().publishReply(article_id, content, parent);
	}

	@Override
	protected void onPostExecute(Boolean result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		if (null != onPublishReplyListener) {
			if (result) {
				onPublishReplyListener.publishSuccess();
			} else {
				onPublishReplyListener.publishFail();
			}
		}
	}

	public interface OnPublishReplyListener {
		void publishSuccess();

		void publishFail();
	}

	public OnPublishReplyListener getOnPublishReplyListener() {
		return onPublishReplyListener;
	}

	public void setOnPublishReplyListener(
			OnPublishReplyListener onPublishReplyListener) {
		this.onPublishReplyListener = onPublishReplyListener;
	}

}
