package com.mct.utils;

import java.util.List;
import java.util.TreeMap;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.mct.model.Reply;

public class ReplyUtil {

	/**
	 * ��������
	 * 
	 * @param article_id
	 *            ����id
	 * @param content��������
	 * @param parent�������۵�id
	 *            (��ͨ���۴�0����¥����������id)
	 * @return
	 */
	public boolean publishReply(int article_id, String content, int parent) {
		TreeMap<String, String> params = new TreeMap<String, String>();
		params.put("article_id", String.valueOf(article_id));
		// ���۵��û�Ϊ��ǰ�ѵ�¼�û�
		params.put("user_id", String.valueOf(UserUtil.loginUser.getId()));
		params.put("content", content);
		params.put("time", String.valueOf(System.currentTimeMillis()));
		params.put("parent", String.valueOf(parent));
		String jsonStr = HttpUtils.postMsg(Common.URL_PUBLISH_REPLY, params);
		if (jsonStr.startsWith("{result")) {
			int r = Integer
					.parseInt(jsonStr.substring(8, jsonStr.length() - 1));
			if (r == 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * �������Իظ�
	 * 
	 * @param article_id
	 * @return
	 */
	public List<Reply> getReplyList(int article_id) {
		return getReplyList(article_id, 0, 0);
	}

	/**
	 * �ɷ�ҳ���ػظ�
	 * 
	 * @param article_id
	 * @param limit
	 * @param current
	 * @return
	 */
	public List<Reply> getReplyList(int article_id, int limit, int current) {
		String url = Common.URL_REPLY_LIST + "?article_id=" + article_id
				+ "&limit=" + limit + "&current=" + current;
		String jsonStr = HttpUtils.getMsg(url);
		if (null != jsonStr && !"".equals(jsonStr)
				&& !jsonStr.startsWith("{result")) {
			List<Reply> list = JSON.parseArray(jsonStr, Reply.class);
			return list;
		}
		return null;
	}
}
