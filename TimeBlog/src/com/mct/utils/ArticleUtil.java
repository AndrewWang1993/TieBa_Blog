package com.mct.utils;

import java.util.List;
import java.util.TreeMap;

import com.alibaba.fastjson.JSON;
import com.mct.model.Article;
import com.mct.model.ArticleItem;

/**
 * ���Ӳ������ߣ��ӷ���˻�ȡ���ύ��Ϣ������˵ȵȣ�
 * 
 * @author Whunf
 * 
 */
public class ArticleUtil {

	/**
	 * ��������
	 * 
	 * @param currentCount
	 * @param limit
	 * @return
	 */
	public List<ArticleItem> loadArticleItems(int currentCount, int limit) {
		String url = Common.URL_LOAD_ARTICLES + "?limit=" + limit + "&current="
				+ currentCount;
		String jsonStr = HttpUtils.getMsg(url);
		if (null != jsonStr && !jsonStr.startsWith("{result:")) {
			List<ArticleItem> list = JSON
					.parseArray(jsonStr, ArticleItem.class);
			return list;
		}
		return null;
	}

	/**
	 * ����
	 * 
	 * @param title
	 * @param content
	 * @param userId
	 * @return
	 */
	public boolean publishArticle(String title, String content) {
		TreeMap<String, String> par = new TreeMap<String, String>();
		par.put("title", title);
		par.put("content", content);
		par.put("user_id", String.valueOf(UserUtil.loginUser.getId()));
		par.put("time", String.valueOf(System.currentTimeMillis()));
		String jsonStr = HttpUtils.postMsg(Common.URL_PUBLISH_ARTICLE, par);
		if (jsonStr.startsWith("{result:")) {
			int r = Integer
					.parseInt(jsonStr.substring(8, jsonStr.length() - 1));
			if (r == 1) {
				return true;
			}
		}
		return false;
	}

	/**
	 * ��ȡ��������
	 * @param id
	 * @return
	 */
	public Article getArticle(int id) {
		String url = Common.URL_ARTICLE_DETAIL + "?id=" + id;
		String jsonStr = HttpUtils.getMsg(url);
		if (!jsonStr.startsWith("{re")) {
			Article a = JSON.parseObject(jsonStr, Article.class);
			return a;
		}
		return null;
	}

}
