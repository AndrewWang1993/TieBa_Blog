package com.mct.control;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mct.blog.R;
import com.mct.model.ArticleItem;
import com.mct.utils.BitmapUtil;
import com.mct.utils.LoadIconManager;

public class ArticleAdapter extends BaseAdapter {
	private LayoutInflater mInflater;
	private List<ArticleItem> list;
	private SimpleDateFormat dateFormat;
	private Context mContext;
	private LoadIconManager.OnDownloadListener onLoadIconListener;

	public ArticleAdapter(Context context) {
		this.mContext = context;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		dateFormat = new SimpleDateFormat("yy/MM/dd HH:mm");
	}

	public void setArticleList(List<ArticleItem> list) {
		this.list = list;
	}

	// 添加一个条目
	public void addArticle(ArticleItem item) {
		if (null == list) {
			list = new ArrayList<ArticleItem>();
		}
		list.add(item);
	}

	// 添加一个集合
	public void addArticle(List<ArticleItem> slist) {
		if (null == list) {
			list = new ArrayList<ArticleItem>();
		}
		list.addAll(slist);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return null == list ? 0 : list.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if (null == convertView) {
			convertView = mInflater.inflate(R.layout.item_article_layout, null);
			holder = new ViewHolder();
			holder.icon = (ImageView) convertView.findViewById(R.id.item_icon);
			holder.title = (TextView) convertView.findViewById(R.id.item_title);
			holder.name = (TextView) convertView
					.findViewById(R.id.item_user_name);
			holder.time = (TextView) convertView.findViewById(R.id.item_time);
			holder.count = (TextView) convertView.findViewById(R.id.item_count);
			holder.line = (TextView) convertView.findViewById(R.id.item_line);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ArticleItem item = list.get(position);
		holder.title.setText(item.getTitle());
		String name = item.getU_name();
		holder.name.setText(name);
		long time = Long.parseLong(item.getTime());
		holder.time.setText(getTimeFormat(time));
		holder.count.setText(String.valueOf(item.getReply_count()));
		if (isNewPublish(time)) {
			holder.line.setBackgroundResource(R.drawable.article_divider_new);
		} else {
			holder.line.setBackgroundResource(R.drawable.article_divider_1);
		}
		//使用头像加载器显示头像
		LoadIconManager.getInstance().showHeadIcon(mContext, holder.icon, name,
				item.getU_photo(), onLoadIconListener);
		return convertView;
	}

	// 检测是否是最新发布的
	private boolean isNewPublish(long time) {
		// 是否是在这3个小时之内发的
		return System.currentTimeMillis() - time <= 10800000;
	}

	// 获取时间格式
	private String getTimeFormat(long time) {
		// 时间小于一天时
		if (System.currentTimeMillis() - time <= 86400000) {
			dateFormat.applyPattern("HH:mm");
		} else {
			dateFormat.applyPattern("yy/MM/dd HH:mm");
		}
		return dateFormat.format(time);
	}

	class ViewHolder {
		ImageView icon;
		TextView title;
		TextView name;
		TextView time;
		TextView count;
		TextView line;
	}

	public LoadIconManager.OnDownloadListener getOnLoadIconListener() {
		return onLoadIconListener;
	}

	public void setOnLoadIconListener(
			LoadIconManager.OnDownloadListener onLoadIconListener) {
		this.onLoadIconListener = onLoadIconListener;
	}

}
