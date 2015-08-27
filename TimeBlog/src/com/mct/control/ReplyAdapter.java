package com.mct.control;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mct.blog.R;
import com.mct.model.Reply;
import com.mct.utils.LoadIconManager;

/**
 * �ظ��б�������
 * 
 * @author huajian.zhang
 * 
 */
public class ReplyAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context mContext;

	// �����б�
	private ArrayList<ArrayList<Reply>> mList;
	
	//���ػظ���ͷ�����
	private LoadIconManager.OnDownloadListener loadIconListener;
	private OnReplyClickListener onReplyClickListener;

	public ReplyAdapter(Context context) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
	}

	public void setList(ArrayList<ArrayList<Reply>> mList) {
		this.mList = mList;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mList == null ? 0 : mList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return getReplyView(mList.get(position));
	}

	// ��ȡÿһ¥��Ĳ���
	private View getReplyView(ArrayList<Reply> list) {
		// ���ظ�¥����
		LinearLayout layout = (LinearLayout) mInflater.inflate(
				R.layout.item_reply_base, null);
		// ���õ�һ¥���ı���Ϣ
		TextView authorView = (TextView) layout
				.findViewById(R.id.item_reply_user);
		TextView replyContents = (TextView) layout
				.findViewById(R.id.item_reply_content);
		ImageView icon = (ImageView) layout.findViewById(R.id.item_reply_icon);
		Reply r0 = list.get(0);
		LoadIconManager.getInstance().showHeadIcon(mContext, icon,
				r0.getUser_name(), r0.getPhoto(), loadIconListener);
		authorView.setText(r0.getUser_name());
		replyContents.setText(r0.getContent());
		layout.findViewById(R.id.item_reply_view).setOnClickListener(
				new TowerReplyOnClickListener(r0));
		FrameLayout towerLayout = (FrameLayout) layout
				.findViewById(R.id.item_reply_tower_layout);
		// �����¥ֻ��һ������
		if (list.size() == 1) {
			// ������Ҫ��ס��¥��ռ䣨�����Ǹ�Linearlayout��
			towerLayout.setVisibility(View.GONE);
		} else {
			for (int i = 1; i < list.size(); i++) {
				// ����һ����¥
				LinearLayout layout2 = (LinearLayout) mInflater.inflate(
						R.layout.item_reply_tower, null);
				Reply r = list.get(i);
				((TextView) layout2.findViewById(R.id.item_reply_user))
						.setText(r.getUser_name());
				((TextView) layout2.findViewById(R.id.item_reply_content))
						.setText(r.getContent());
				layout2.findViewById(R.id.item_reply_view).setOnClickListener(
						new TowerReplyOnClickListener(r));
				towerLayout.addView(layout2);
				towerLayout = (FrameLayout) layout2
						.findViewById(R.id.item_reply_tower_layout);
				if (i == list.size() - 1) {
					towerLayout.setVisibility(View.GONE);
				}
			}
		}
		return layout;
	}

	private class TowerReplyOnClickListener implements OnClickListener {
		private Reply reply;

		public TowerReplyOnClickListener(Reply reply) {
			this.reply = reply;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(null != onReplyClickListener){
				onReplyClickListener.onClick(v,reply);
			}
		}

	}

	public LoadIconManager.OnDownloadListener getLoadIconListener() {
		return loadIconListener;
	}

	public void setLoadIconListener(
			LoadIconManager.OnDownloadListener loadIconListener) {
		this.loadIconListener = loadIconListener;
	}
	
	//�ظ��������������¥�ظ�ʱ��Ҫ��
	public interface OnReplyClickListener{
		void onClick(View view,Reply reply);
	}

	public OnReplyClickListener getOnReplyClickListener() {
		return onReplyClickListener;
	}

	public void setOnReplyClickListener(OnReplyClickListener onReplyClickListener) {
		this.onReplyClickListener = onReplyClickListener;
	}

}
