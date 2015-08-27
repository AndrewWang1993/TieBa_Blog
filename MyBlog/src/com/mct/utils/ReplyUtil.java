package com.mct.utils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Stack;

import com.mct.model.Reply;

public class ReplyUtil {
	private static ReplyUtil instance;

	private ReplyUtil() {

	}

	public static ReplyUtil getInstance() {
		if (null == instance) {
			instance = new ReplyUtil();
		}
		return instance;
	}

	/**
	 * 添加一个普通跟帖
	 * 
	 * @param conn
	 *            数据库连接对象
	 * @param u_id
	 *            用户id
	 * @param a_id
	 *            帖子id
	 * @param content
	 *            回复内容
	 * @param time
	 *            回复时间
	 * @return
	 */
	public boolean insertReply(Connection conn, int u_id, int a_id,
			String content, String time) {
		return insertReply(conn, u_id, a_id, content, time, 0);
	}

	/**
	 * 添加一个盖楼跟帖
	 * 
	 * @param conn
	 *            数据库连接对象
	 * @param u_id
	 *            用户id
	 * @param a_id
	 *            帖子id
	 * @param content
	 *            回复内容
	 * @param time
	 *            回复时间
	 * @param parent
	 *            所盖的楼的id
	 * @return
	 */
	public boolean insertReply(Connection conn, int u_id, int a_id,
			String content, String time, int parent) {
		String sql = "insert into reply (user_id,article_id,content,time,parent) values ("
				+ u_id
				+ ","
				+ a_id
				+ ",'"
				+ content
				+ "','"
				+ time
				+ "',"
				+ parent + ");";
		try {
			Statement statement = conn.createStatement();
			int result = statement.executeUpdate(sql);
			if (result > 0) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * 可分页加载某个帖子的评论
	 * 
	 * @param conn
	 * @param article_id
	 *            帖子id
	 * @param limit
	 *            每次加载个数
	 * @param current
	 *            当前以加载个数
	 * @return
	 */
	public ArrayList<Reply> getReply(Connection conn, int article_id,
			int limit, int current) {
		ArrayList<Reply> list = null;
		StringBuffer sb = new StringBuffer(
				"select r._id,r.content,r.time,r.parent,u._id,u.name,u.photo from reply r,users u where article_id=");
		sb.append(article_id);
		sb.append(" and r.user_id = u._id");
		sb.append(" order by r._id desc"); //倒序列出，最后回复在最前面
		if (limit > 0) {
			sb.append(" limit ").append(limit).append(" offset ").append(
					current);
		}
		String sql = sb.toString();
		try {
			Statement statement = conn.createStatement();
			System.out.println(sql);
			ResultSet rs = statement.executeQuery(sql);
			list = new ArrayList<Reply>();
			while (rs.next()) {
				int rId = rs.getInt("r._id");
				String content = rs.getString("r.content");
				String time = rs.getString("r.time");
				int parent = rs.getInt("r.parent");
				int uId = rs.getInt("u._id");
				String userName = rs.getString("u.name");
				String photo = rs.getString("u.photo");
				list.add(new Reply(rId, uId, article_id, parent, content, time,
						userName, photo));
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
	 * 根据评论id单独加载某个评论楼(从当前楼到最底楼的加载)
	 * 
	 * @param conn
	 * @param article_id 帖子id
	 * @param reply_id 起始回复的id
	 * @return
	 */
	public ArrayList<Reply> getTowerReply(Connection conn, int article_id,
			int reply_id) {
		// 记录获取当前评论的id
		Stack<Integer> stack = new Stack<Integer>();
		ArrayList<Reply> list = new ArrayList<Reply>();
		stack.push(reply_id);
		try {
			Statement statement = conn.createStatement();
			while (!stack.isEmpty()) {
				String sql = "select r._id,r.content,r.time,r.parent,u._id,u.name,u.photo from reply r,users u where article_id="
						+ article_id + " and r._id = " + stack.pop();
				ResultSet rs = statement.executeQuery(sql);
				if (rs.next()) {
					int rId = rs.getInt("r._id");
					String content = rs.getString("r.content");
					String time = rs.getString("r.time");
					int parent = rs.getInt("r.parent");
					int uId = rs.getInt("u._id");
					String userName = rs.getString("u.name");
					String photo = rs.getString("u.photo");
					list.add(new Reply(rId, uId, article_id, parent, content,
							time, userName, photo));
					// 如果所盖的回复id非0则压入栈中继续查找所盖楼层
					if (parent != 0) {
						stack.push(parent);
					}
				}
				rs.close();
			}
			statement.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
}
