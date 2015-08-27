package com.mct.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import com.mct.model.Article;
import com.mct.model.ArticleItem;

/**
 * ���Ӳ�������
 * 
 * @author huajian.zhang
 * 
 */
public class ArticleUtil {
	private static ArticleUtil instance;

	private ArticleUtil() {

	}

	public static ArticleUtil getInstance() {
		if (null == instance) {
			instance = new ArticleUtil();
		}
		return instance;
	}

	/**
	 * ����
	 * 
	 * @param conn
	 *            ���ݿ����Ӷ���
	 * @param title
	 *            ����
	 * @param content
	 *            ����
	 * @param user_id
	 *            �û�id
	 * @param time
	 *            ʱ��
	 * @param type
	 *            ����
	 * @return
	 */
	public boolean postingArticle(Connection conn, String title,
			String content, int user_id, String time, int type) {
		String sql = "insert into article (title,content,user_id,time,type) values ('"
				+ title
				+ "','"
				+ content
				+ "',"
				+ user_id
				+ ",'"
				+ time
				+ "',"
				+ type + ")";
		try {
			Statement statement = conn.createStatement();
			int row = statement.executeUpdate(sql);
			if (row > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * ��ȡ������Ŀ
	 * 
	 * @param conn
	 *            ���ݿ����Ӷ���
	 * @param limit
	 *            ��ȡ������
	 * @param currentCount
	 *            ��ǰ�Ѿ��õ���������
	 * @return
	 */
	public ArrayList<ArticleItem> getArticle(Connection conn, int limit,
			int currentCount) {
		StringBuffer sb = new StringBuffer(
				"select a._id,a.title,a.time,u.name,u.photo from article a , users u where a.user_id = u._id");
		sb.append(" order by a._id desc");
		if (limit > 0) {
			sb.append(" limit ").append(limit).append(" offset ").append(
					currentCount);
		}
		String sql = sb.toString();
		ArrayList<ArticleItem> list = null;
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			list = new ArrayList<ArticleItem>();
			while (rs.next()) {
				int a_id = rs.getInt("a._id");
				String title = rs.getString("a.title");
				String time = rs.getString("a.time");
				String user_name = rs.getString("u.name");
				String photo = rs.getString("u.photo");
				int reply_count = getReplyCount(conn, a_id);
				list.add(new ArticleItem(a_id, reply_count, user_name, title,
						time, photo));
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * ��������id��ȡ���ӵĻظ�����
	 * 
	 * @param conn
	 *            ���ݿ����Ӷ���
	 * @param article_id
	 *            ����id
	 * @return
	 */
	private int getReplyCount(Connection conn, int article_id) {
		String sql = "select count(*) from reply where article_id = "
				+ article_id;
		int count = 0;
		try {
			Statement statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				count = rs.getInt("count(*)");
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return count;
	}

	/**
	 * ��������id��ȡ��������
	 * 
	 * @param id
	 * @return
	 */
	public Article getArticle(Connection conn, int id) {
		String sql = "select * from article where _id=" + id;
		Statement statement;
		Article article = null;
		try {
			statement = conn.createStatement();
			ResultSet rs = statement.executeQuery(sql);
			if (rs.next()) {
				String content = rs.getString("content");
				int u_id = rs.getInt("user_id");
				int type = rs.getInt("type");
				article = new Article(content, u_id, type);
			}
			rs.close();
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return article;
	}

}