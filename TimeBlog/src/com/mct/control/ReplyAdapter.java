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
 * 回复列表适配器
 * 
 * @author huajian.zhang
 * 
 */
public class ReplyAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private Context mContext;

	// 评论列表
	private ArrayList<ArrayList<Reply>> mList;
	
	//加载回复者头像监听
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

	// 获取每一楼层的布局
	private View getReplyView(ArrayList<Reply> list) {
		// 加载盖楼布局
		LinearLayout layout = (LinearLayout) mInflater.inflate(
				R.layout.item_reply_base, null);
		// 设置第一楼的文本信息
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
		// 如果该楼只有一条内容
		if (list.size() == 1) {
			// 隐藏需要盖住的楼层空间（这里是个Linearlayout）
			towerLayout.setVisibility(View.GONE);
		} else {
			for (int i = 1; i < list.size(); i++) {
				// 创建一层新楼
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
	
	//回复被点击监听（盖楼回复时需要）
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
