package com.mct.activity;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.mct.blog.R;
import com.mct.control.ReplyAdapter;
import com.mct.model.Reply;
import com.mct.task.ReplyTask;
import com.mct.utils.CommonUtil;
import com.mct.utils.LoadIconManager;
import com.mct.utils.ReplyUtil;

/**
 * �ظ�ҳ��
 * 
 * @author Whunf
 * 
 */
public class ReplyActivity extends Activity implements OnClickListener {
	private ListView mListView;
	private EditText etReply;
	private TextView tvEmpty;
	private ReplyAdapter adapter;
	private ArrayList<ArrayList<Reply>> list;
	private int article_id;
	// ������ʾ������
	private InputMethodManager inputManager;
	private boolean isPublishTower;// �Ƿ��Ƿ����¥����
	private View rootView; // �ܲ��ֵ���ͼ����

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_reply);
		article_id = getIntent().getIntExtra("article_id", 0);
		rootView = findViewById(R.id.root_layout_reply);
		etReply = (EditText) findViewById(R.id.et_reply);
		tvEmpty = (TextView) findViewById(R.id.empty_tips);
		findViewById(R.id.tv_publish_reply).setOnClickListener(this);
		mListView = (ListView) findViewById(R.id.reply_list);
		adapter = new ReplyAdapter(this);
		adapter.setLoadIconListener(onIconLoadListener);
		adapter.setOnReplyClickListener(onReplyClick);
		mListView.setAdapter(adapter);
		inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		etReply.requestFocus();
		ViewTreeObserver vo = rootView.getViewTreeObserver();
		vo.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				if (rootView.getRootView().getHeight() - rootView.getHeight() < 100) {
					isPublishTower = false;
				}
			}
		});
	}

	// �û�ͷ�������ϼ���
	private LoadIconManager.OnDownloadListener onIconLoadListener = new LoadIconManager.OnDownloadListener() {

		@Override
		public void onDownloadOK(String name) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onDownloadFail(String name) {
			// TODO Auto-generated method stub

		}
	};

	// ��¥�ظ��ļ���
	private ReplyAdapter.OnReplyClickListener onReplyClick = new ReplyAdapter.OnReplyClickListener() {

		@Override
		public void onClick(View view, Reply reply) {
			// TODO Auto-generated method stub
			towerView = view;
			towerReply = reply;
			showReplyTowerMenu();
		}
	};

	private View towerView; // ��ǰҪ�ظ�������������ͼ
	private Reply towerReply;// ��ǰҪ�ظ������۶���
	private PopupWindow menuWindow;

	// ��ʾ�˵�
	private void showReplyTowerMenu() {
		if (null == menuWindow) {
			View contentView = getLayoutInflater().inflate(
					R.layout.popu_menu_layout, null);
			contentView.findViewById(R.id.tv_reply_tower).setOnClickListener(
					this);
			contentView.findViewById(R.id.tv_reply_zan)
					.setOnClickListener(this);
			menuWindow = new PopupWindow(contentView, 150, 50);
			menuWindow.setBackgroundDrawable(new BitmapDrawable());
			menuWindow.setFocusable(true);
			menuWindow.setOutsideTouchable(true);
		}
		menuWindow.showAsDropDown(towerView, 50, 0);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.tv_publish_reply:
			String content = etReply.getText().toString();
			if (null != content && !"".equals(content)) {
				if (isPublishTower) {
					new ReplyTask(onPublishListener).execute(article_id,
							content, towerReply.getId());
				} else {
					new ReplyTask(onPublishListener).execute(article_id,
							content, 0);
				}
			} else {
				CommonUtil.showMsg(ReplyActivity.this, "�������ݲ���Ϊ��");
			}
			break;
		case R.id.tv_reply_tower:
			if (menuWindow.isShowing()) {
				menuWindow.dismiss();
			}
			// д����(��¥����)
			isPublishTower = true;
			inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			break;
		case R.id.tv_reply_zan:
			// �ޣ�������չ��...��ʵ��̨ľ�нӿ�^_^
			if (menuWindow.isShowing()) {
				menuWindow.dismiss();
			}
			CommonUtil.showMsg(ReplyActivity.this, "��  +1");
			break;
		}
	}

	// �������ۼ���
	private ReplyTask.OnPublishReplyListener onPublishListener = new ReplyTask.OnPublishReplyListener() {

		@Override
		public void publishSuccess() {
			// TODO Auto-generated method stub
			etReply.setText("");
			inputManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
			CommonUtil.showMsg(ReplyActivity.this, "����ɹ�");
			loadReply();
		}

		@Override
		public void publishFail() {
			// TODO Auto-generated method stub
			CommonUtil.showMsg(ReplyActivity.this, "����ʧ��");
		}
	};

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		loadReply();
	}

	// ��������
	private void loadReply() {
		if (article_id != 0) {
			new LoadReplyTask().execute(article_id);
		}
	}

	private class LoadReplyTask extends AsyncTask<Object, Void, List<Reply>> {

		@Override
		protected List<Reply> doInBackground(Object... params) {
			// TODO Auto-generated method stub
			int article_id = (Integer) params[0];
			return new ReplyUtil().getReplyList(article_id);
		}

		@Override
		protected void onPostExecute(List<Reply> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (null != result) {
				tvEmpty.setVisibility(View.GONE);
				showData(result);
			} else {
				tvEmpty.setVisibility(View.VISIBLE);
			}
		}

	}

	/**
	 * ��ʾ����
	 * 
	 * @param rList
	 */
	private void showData(List<Reply> rList) {
		if (null == list) {
			list = new ArrayList<ArrayList<Reply>>();
		} else {
			// ����Ƿ�ҳ������ôҪ������������ʾ�ģ��������Ȱ�ԭ�����ص�ȫ�����
			list.clear();
		}
		// ��װ��¥��ʾ�Ľṹ
		int size = rList.size();
		for (int i = 0; i < size; i++) {
			Reply r = rList.get(i);
			if (!r.isShow()) {
				ArrayList<Reply> tower = new ArrayList<Reply>();
				tower.add(r);
				r.setShow(true);
				if (r.getParent() != 0) {
					tempStack.push(r);
					addReplyToTower(tower, rList);
				}
				list.add(tower);
			}
		}
		adapter.setList(list);
		adapter.notifyDataSetChanged();
	}

	// ��ϸ�¥����ʱջ
	private Stack<Reply> tempStack = new Stack<Reply>();

	/**
	 * �ӻظ������л�ȡ��¥����صĻظ�����ӵ�¥����
	 * 
	 * @param tower
	 *            ��ǰ¥�ṹ(��װ��Ŀ��)
	 * @param fromList
	 *            �ܻظ���������Դ��
	 */
	private void addReplyToTower(ArrayList<Reply> tower, List<Reply> fromList) {
		while (!tempStack.isEmpty()) {
			Reply current = tempStack.peek();
			int index = fromList.indexOf(current);
			int size = fromList.size();
			for (int i = index + 1; i < size; i++) {
				Reply r = fromList.get(i);
				if (r.getId() == current.getParent()) {
					tempStack.pop();
					tower.add(r);
					r.setShow(true); // ����Ϊ�ѱ�����
					if (r.getParent() != 0) {
						tempStack.push(r);
					}
					break;
				}
			}
		}

	}
}
